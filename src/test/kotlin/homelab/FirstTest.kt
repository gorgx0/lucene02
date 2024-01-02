package homelab

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.TextField
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.ByteBuffersDirectory
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FirstTest {

    val analyzer = StandardAnalyzer()
    val dir = ByteBuffersDirectory()
    val indexWriterConfig = IndexWriterConfig(analyzer)
    val indexWriter = IndexWriter(dir, indexWriterConfig)


    @Test
    fun name() {
        val document = Document()
        document.add(Field("field01", "value of field01", TextField.TYPE_STORED))
        indexWriter.addDocument(document)
        indexWriter.close()

        val directoryReader = DirectoryReader.open(dir)
        val indexSearcher = IndexSearcher(directoryReader)

        val query = "field01:value"

        val queryParser = QueryParser("field01", analyzer)

        val parsedQuery = queryParser.parse(query)

        val topDocs = indexSearcher.search(parsedQuery, 10)

        println(topDocs.totalHits)

        assertEquals(1L, topDocs.totalHits.value)
    }

    @Test
    fun second() {
        FakeTestDataGenerator().generateOffers(10).forEach {
            val document = Document()
            with(document) {
                add(Field("id", it.id.toString(), TextField.TYPE_STORED))
                add(Field("owner", it.owner, TextField.TYPE_STORED))
                add(Field("address", it.address, TextField.TYPE_STORED))
                add(Field("city", it.city, TextField.TYPE_STORED))
                add(Field("numberOfRooms", it.numberOfRooms.toString(), TextField.TYPE_STORED))
                add(Field("price", it.price.toString(), TextField.TYPE_STORED))
            }
            indexWriter.addDocument(document)
        }
        indexWriter.close()
    }
}