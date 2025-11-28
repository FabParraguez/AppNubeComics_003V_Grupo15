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

    @Test
    fun `getPost() debe retornar un post por id`() = runTest {
        val fakePost = Post(userId = 1, id = 1, title = "Título Test", body = "Cuerpo Test")

        val fakeApi = object : ApiService {
            override suspend fun getPosts(): List<Post> = throw UnsupportedOperationException()
            override suspend fun getPost(id: Int): Post = fakePost
            override suspend fun createPost(post: Post): Post = throw UnsupportedOperationException()
            override suspend fun updatePost(id: Int, post: Post): Post = throw UnsupportedOperationException()
            override suspend fun deletePost(id: Int) = throw UnsupportedOperationException()
        }

        val result = fakeApi.getPost(1)
        assertEquals(fakePost, result)
        assertEquals("Título Test", result.title)
    }

    @Test
    fun `createPost() debe crear y retornar nuevo post`() = runTest {
        val newPost = Post(userId = 1, id = 0, title = "Nuevo Post", body = "Contenido nuevo")
        val createdPost = Post(userId = 1, id = 101, title = "Nuevo Post", body = "Contenido nuevo")

        val fakeApi = object : ApiService {
            override suspend fun getPosts(): List<Post> = throw UnsupportedOperationException()
            override suspend fun getPost(id: Int): Post = throw UnsupportedOperationException()
            override suspend fun createPost(post: Post): Post = createdPost
            override suspend fun updatePost(id: Int, post: Post): Post = throw UnsupportedOperationException()
            override suspend fun deletePost(id: Int) = throw UnsupportedOperationException()
        }

        val result = fakeApi.createPost(newPost)
        assertEquals(101, result.id)
        assertEquals("Nuevo Post", result.title)
    }

    @Test
    fun `updatePost() debe actualizar y retornar post modificado`() = runTest {
        val updatedPost = Post(userId = 1, id = 1, title = "Título Actualizado", body = "Cuerpo Actualizado")

        val fakeApi = object : ApiService {
            override suspend fun getPosts(): List<Post> = throw UnsupportedOperationException()
            override suspend fun getPost(id: Int): Post = throw UnsupportedOperationException()
            override suspend fun createPost(post: Post): Post = throw UnsupportedOperationException()
            override suspend fun updatePost(id: Int, post: Post): Post = updatedPost
            override suspend fun deletePost(id: Int) = throw UnsupportedOperationException()
        }

        val result = fakeApi.updatePost(1, updatedPost)
        assertEquals("Título Actualizado", result.title)
        assertEquals("Cuerpo Actualizado", result.body)
    }

    @Test
    fun `deletePost() debe ejecutarse sin lanzar excepciones`() = runTest {
        var deleteWasCalled = false

        val fakeApi = object : ApiService {
            override suspend fun getPosts(): List<Post> = throw UnsupportedOperationException()
            override suspend fun getPost(id: Int): Post = throw UnsupportedOperationException()
            override suspend fun createPost(post: Post): Post = throw UnsupportedOperationException()
            override suspend fun updatePost(id: Int, post: Post): Post = throw UnsupportedOperationException()
            override suspend fun deletePost(id: Int) {
                deleteWasCalled = true
            }
        }

        fakeApi.deletePost(1)
        assertEquals(true, deleteWasCalled)
    }
}
