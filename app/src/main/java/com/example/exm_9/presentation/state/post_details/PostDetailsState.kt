package com.example.exm_9.presentation.state.post_details

import com.example.exm_9.presentation.model.Post


data class PostDetailsState(
    val isLoading: Boolean = true,
    val data: Post? = null,
    val errorMessage: String? = null
)