package com.myblog.network

import com.myblog.model.BlogDetailsResponse
import com.myblog.model.BlogResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // Suspend function for coroutine support
    @GET("v1/blog/public/")
    suspend fun getBlogs(@Query("page_no") pageNo: Int): Response<BlogResponse>

    // Regular Retrofit call for callback-based execution
    @GET("v1/blog/public/")
    fun getBlogDetailsBySlug(@Query("slug_str") slug: String): Call<BlogDetailsResponse>
}

