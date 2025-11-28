package com.vivitasol.carcasamvvm.repository

import com.vivitasol.carcasamvvm.model.Post
import com.vivitasol.carcasamvvm.remote.RetrofitInstance
import com.vivitasol.carcasamvvm.remote.ApiService

/**
 * Repositorio para operaciones CRUD sobre posts usando jsonplaceholder.
 */
open class PostRepository(
    private val api: ApiService = RetrofitInstance.api
) {
    open suspend fun getPosts(): List<Post> = api.getPosts()

    open suspend fun getPost(id: Int): Post = api.getPost(id)

    open suspend fun createPost(post: Post): Post = api.createPost(post)

    open suspend fun updatePost(id: Int, post: Post): Post = api.updatePost(id, post)

    open suspend fun deletePost(id: Int) = api.deletePost(id)
}
