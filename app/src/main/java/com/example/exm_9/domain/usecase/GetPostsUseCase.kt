package com.example.exm_9.domain.usecase

import com.example.exm_9.domain.repository.post.PostsRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke() =
        postsRepository.getPosts()
}