package com.myblog.model

data class BlogDetailsResponse(
    val data: BlogDetails,
    val message: String,
    val error: Boolean
)

data class BlogDetails(
    val title: String,
    val subtitle: String,
    val content: String,
    val feature_image_url: String,
)
