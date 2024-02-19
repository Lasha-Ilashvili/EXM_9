package com.example.exm_9.domain.model

data class PostDomain(
    val id: Int,
    val images: List<String>?,
    val title: String,
    val comments: Int,
    val likes: Int,
    val shareContent: String,
    val owner: OwnerDomain
)

data class OwnerDomain(
    val firstName: String,
    val lastName: String,
    val profile: String?,
    val postDate: Long
)