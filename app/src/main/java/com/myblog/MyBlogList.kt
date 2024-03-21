package com.myblog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myblog.Adapter.BlogAdapter
import com.myblog.ViewModels.BlogsViewModel

class MyBlogList : AppCompatActivity() {

    private lateinit var viewModel: BlogsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_blog_list)

        viewModel = ViewModelProvider(this).get(BlogsViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.blog_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getBlogsFromServer(1).observe(this, Observer { blogPosts ->
            recyclerView.adapter = BlogAdapter(blogPosts)
        })
    }
}
