package com.example.exm_9.di

import com.example.exm_9.data.common.HandleResponse
import com.example.exm_9.data.repository.post.PostsRepositoryImpl
import com.example.exm_9.data.service.post.PostsService
import com.example.exm_9.domain.repository.post.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostsRepository(
        postsService: PostsService,
        handleResponse: HandleResponse
    ): PostsRepository {
        return PostsRepositoryImpl(
            postsService = postsService,
            handleResponse = handleResponse
        )
    }
}