package homelab

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.StringField
import org.apache.lucene.document.TextField
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.ByteBuffersDirectory
import kotlin.test.Test
import kotlin.test.assertEquals

class SecondTest {

    private val offers = TestDataReader().offers("test_data.json")
    private val analyzer = StandardAnalyzer()
    private val dir = ByteBuffersDirectory()
    private val indexWriterConfig = IndexWriterConfig(analyzer)
    private val indexWriter = IndexWriter(dir, indexWriterConfig)

    @Test
    fun indexingOffers() {
        offers.forEach {
            val document = Document()
            with(document) {
                add(Field("property_id", it.propertyId, StringField.TYPE_STORED))
                add(Field("type", it.type, TextField.TYPE_STORED))
                add(Field("location", it.location, TextField.TYPE_STORED))
                add(Field("price", it.price, TextField.TYPE_STORED))
                add(Field("bedrooms", it.bedrooms.toString(), TextField.TYPE_STORED))
                add(Field("bathrooms", it.bathrooms.toString(), TextField.TYPE_STORED))
                add(Field("area_sqft", it.areaSqft.toString(), TextField.TYPE_STORED))
                add(Field("description", it.description, TextField.TYPE_NOT_STORED))
                add(Field("image_url", it.imageUrl, StringField.TYPE_STORED))
            }
            indexWriter.addDocument(document)
        }
        indexWriter.close()

        val reader = DirectoryReader.open(dir)
        val searcher = IndexSearcher(reader)
        val queryParser = QueryParser("description", analyzer)
        val query = queryParser.parse("description:renovated")
        val topDocs = searcher.search(query, 10)

        assertEquals(2L, topDocs.totalHits.value)

        val propertyIds = topDocs.scoreDocs.map {
            searcher.storedFields().document(it.doc).get("property_id")
        }

        topDocs.scoreDocs.forEach {
            println("------------------")
            val fields = searcher.indexReader.document(it.doc).fields
            fields.forEach(::println)
        }

        assertEquals(listOf("REO005", "REO002"), propertyIds)
    }
}