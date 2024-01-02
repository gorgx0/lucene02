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

    @Test
    fun name() {
        val analyzer = StandardAnalyzer()
        val dir = ByteBuffersDirectory()
        val indexWriterConfig = IndexWriterConfig(analyzer)
        val indexWriter = IndexWriter(dir, indexWriterConfig)

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
}