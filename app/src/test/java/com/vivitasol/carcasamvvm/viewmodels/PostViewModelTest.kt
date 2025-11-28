package com.vivitasol.carcasamvvm.viewmodels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import com.vivitasol.carcasamvvm.model.Post
import com.vivitasol.carcasamvvm.repository.PostRepository
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Fake repository for tests
    class FakeSuccessRepo(private val data: List<Post>) : PostRepository() {
        override suspend fun getPosts(): List<Post> = data
    }

    class FakeErrorRepo : PostRepository() {
        override suspend fun getPosts(): List<Post> { throw RuntimeException("network error") }
    }

    @Test
    fun fetchPosts_success_populatesPosts() = runTest {
        val sample = listOf(Post(userId = 1, id = 1, title = "t1", body = "b1"))
        val vm = PostViewModel(repository = FakeSuccessRepo(sample))

        // advance until launched coroutines complete
        testDispatcher.scheduler.advanceUntilIdle()

        val posts = vm.posts.first()
        assertEquals(1, posts.size)
        assertEquals("t1", posts[0].title)
        assertEquals(false, vm.isLoading.first())
        assertEquals(null, vm.error.first())
    }

    @Test
    fun fetchPosts_error_setsError() = runTest {
        val vm = PostViewModel(repository = FakeErrorRepo())
        testDispatcher.scheduler.advanceUntilIdle()

        val error = vm.error.first()
        assertEquals(true, error != null)
        assertEquals(false, vm.isLoading.first())
        assertEquals(0, vm.posts.first().size)
    }
}
