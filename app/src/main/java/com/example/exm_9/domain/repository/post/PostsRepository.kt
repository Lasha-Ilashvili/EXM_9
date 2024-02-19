package com.example.exm_9.domain.repository.post

import com.example.exm_9.data.common.Resource
import com.example.exm_9.domain.model.PostDomain
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    suspend fun getPosts(): Flow<Resource<List<PostDomain>>>
}