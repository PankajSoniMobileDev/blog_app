package com.myblog.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myblog.model.BlogPost
import com.myblog.network.ApiService
import com.myblog.network.RetrofitHelper
import kotlinx.coroutines.launch

class BlogsViewModel : ViewModel() {

    private val data = MutableLiveData<List<BlogPost>>()

    fun getBlogsFromServer(pageNo: Int): LiveData<List<BlogPost>> {
        viewModelScope.launch {
            try {
                val response = RetrofitHelper.getInstance().create(ApiService::class.java).getBlogs(pageNo)
                if (response.isSuccessful && response.body() != null) {
                    data.postValue(response.body()?.data)
                } else {
                    // case where the response is not successful
                }
            } catch (e: Exception) {
                Log.d("error", e.stackTrace.toString())
            }
        }
        return data
    }
}

