import com.andrew.search.SearchApplication
import com.andrew.search.domain.Document
import com.andrew.search.domain.KakaoBlogResponse
import com.andrew.search.domain.Meta
import com.andrew.search.service.BlogSearchApi
import com.andrew.search.service.PopularKeywordService
import com.andrew.search.service.SearchServiceImpl
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity

@SpringBootTest(classes = [SearchApplication::class])
class SearchServiceImplTest {

    @Mock
    private lateinit var blogSearchApi: BlogSearchApi

    @Autowired
    private lateinit var popularKeywordService: PopularKeywordService

    @Autowired
    private lateinit var searchService: SearchServiceImpl

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun searchTest() {
        // Given
        val keyword = "example"
        val sort = "accuracy"
        val page = 1
        val size = 1

        // When
        val result = searchService.searchBlogs(keyword, sort, page, size)

        // Then
        assertEquals(result?.size, 1)
    }


    @Test
    fun getTop10Test() {
        // Given
        for (i in 1..15)
            for (j in 1 .. i)
                popularKeywordService.incrementCount(i.toString())
        // When
        val result = popularKeywordService.getPopularKeywords()

        // Then
        assertEquals(result?.size, 10)
        assertEquals(result?.get(0)?.keyword, "15")
    }
}
