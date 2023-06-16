import com.andrew.search.SearchApplication
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest(classes = [SearchApplication::class])
class SearchControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun searchBlogTest() {
        val keyword = "example"
        val sort = "accuracy"
        val page = 1
        val size = 10

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/search")
                .param("query", keyword)
                .param("sort", sort)
                .param("page", page.toString())
                .param("size", size.toString())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun searchBlogFailTest() {
        val sort = "accuracy"
        val page = 1
        val size = 10

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/search")
                .param("sort", sort)
                .param("page", page.toString())
                .param("size", size.toString())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.code").value(400))
    }

    @Test
    fun searchBlogSortFailTest() {
        val keyword = "example"
        val sort = "test"
        val page = 1
        val size = 10

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/search")
                .param("query", keyword)
                .param("sort", sort)
                .param("page", page.toString())
                .param("size", size.toString())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.code").value(400))
    }

    @Test
    fun searchBlogPageFailTest() {
        val keyword = "example"
        val sort = "accuracy"
        val page = 100
        val size = 10

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/search")
                .param("query", keyword)
                .param("sort", sort)
                .param("page", page.toString())
                .param("size", size.toString())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.code").value(400))
    }

    @Test
    fun searchBlogSizeFailTest() {
        val keyword = "example"
        val sort = "accuracy"
        val page = 1
        val size = 100

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/search")
                .param("query", keyword)
                .param("sort", sort)
                .param("page", page.toString())
                .param("size", size.toString())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.code").value(400))
    }

    @Test
    fun getPopularKeywordsTest() {

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/popular")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }
}
