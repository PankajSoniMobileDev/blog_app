package com.myblog.model

import android.os.Parcel
import android.os.Parcelable

data class BlogResponse(
    val data: List<BlogPost>,
    val message: String,
    val error: Boolean
)

data class BlogPost(
    val title: String,
    val subtitle: String,
    val slug: String,
    val thumbnail_image_url: String?,
    val promotion_image_url: String?,
    val category: String,
    val label: String?,
    val tags: List<String>,
    val viewers_count: Int,
    val blog_for: String?,
    val created_at: String,
    val author: String,
    val read_time: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        title = parcel.readString() ?: "",
        subtitle = parcel.readString() ?: "",
        slug = parcel.readString() ?: "",
        thumbnail_image_url = parcel.readString(),
        promotion_image_url = parcel.readString(),
        category = parcel.readString() ?: "",
        label = parcel.readString(),
        tags = parcel.createStringArrayList() ?: listOf(),
        viewers_count = parcel.readInt(),
        blog_for = parcel.readString(),
        created_at = parcel.readString() ?: "",
        author = parcel.readString() ?: "",
        read_time = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeString(slug)
        parcel.writeString(thumbnail_image_url)
        parcel.writeString(promotion_image_url)
        parcel.writeString(category)
        parcel.writeString(label)
        parcel.writeStringList(tags)
        parcel.writeInt(viewers_count)
        parcel.writeString(blog_for)
        parcel.writeString(created_at)
        parcel.writeString(author)
        parcel.writeInt(read_time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BlogPost> {
        override fun createFromParcel(parcel: Parcel): BlogPost {
            return BlogPost(parcel)
        }

        override fun newArray(size: Int): Array<BlogPost?> {
            return arrayOfNulls(size)
        }
    }
}

