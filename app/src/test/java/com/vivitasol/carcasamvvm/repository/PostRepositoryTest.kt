package com.vivitasol.carcasamvvm.repository

import com.vivitasol.carcasamvvm.model.Post
import com.vivitasol.carcasamvvm.remote.ApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

// Subclass of PostRepository that allows injecting a test ApiService (like the screenshot)
class TestablePostRepository(private val testApi: ApiService) : PostRepository() {
    override suspend fun getPosts(): List<Post> {
        return testApi.getPosts()
    }
}

class PostRepositoryTest {
    @Test
    fun `getPosts() debe retornar una lista de posts simulada`() = runTest {
        val fakePosts = listOf(
            Post(userId = 1, id = 1, title = "Título 1", body = "Cuerpo 1"),
            Post(userId = 2, id = 2, title = "Título 2", body = "Cuerpo 2")
        )

        val fakeApi = object : ApiService {
            override suspend fun getPosts(): List<Post> = fakePosts
            override suspend fun getPost(id: Int): Post = throw UnsupportedOperationException()
            override suspend fun createPost(post: Post): Post = throw UnsupportedOperationException()
            override suspend fun updatePost(id: Int, post: Post): Post = throw UnsupportedOperationException()
            override suspend fun deletePost(id: Int) = throw UnsupportedOperationException()
        }

        val repo = TestablePostRepository(testApi = fakeApi)

        val result = repo.getPosts()
        assertEquals(fakePosts, result)
    }
}
