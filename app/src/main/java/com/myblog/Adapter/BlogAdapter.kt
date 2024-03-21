package com.myblog.Adapter

import BlogDetailsFragment
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myblog.R
import com.myblog.model.BlogPost

class BlogAdapter(private val blogPosts: List<BlogPost>) :
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    class BlogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.blog_title)
        val subtitleTextView: TextView = view.findViewById(R.id.blog_subtitle)
        val imageView: ImageView = view.findViewById(R.id.blog_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blogPost = blogPosts[position]
        holder.titleTextView.text = blogPost.title
        holder.subtitleTextView.text = blogPost.subtitle

        val placeholder = R.drawable.default_image
        val imageUrl = blogPost.thumbnail_image_url ?: blogPost.promotion_image_url
        if (imageUrl != null) {
            Glide.with(holder.imageView.context)
                .load(imageUrl)
                .placeholder(placeholder)
                .into(holder.imageView)
        } else {
            Glide.with(holder.imageView.context)
                .load(placeholder)
                .into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            val learnTextView =
                (holder.itemView.context as Activity).findViewById<TextView>(R.id.tvLearnTitle)
            val lineView = (holder.itemView.context as Activity).findViewById<View>(R.id.view)

            learnTextView.visibility = View.GONE
            lineView.visibility = View.GONE
            val blogDetailsFragment = BlogDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("slug", blogPost.slug)
                }
            }
            (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, blogDetailsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount() = blogPosts.size
}
