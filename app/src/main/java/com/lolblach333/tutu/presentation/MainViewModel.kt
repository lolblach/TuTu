package com.lolblach333.tutu.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.lolblach333.tutu.data.ApiInterface
import com.lolblach333.tutu.data.AppDb
import com.lolblach333.tutu.data.news.Article
import com.lolblach333.tutu.data.news.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    private val _error = MutableLiveData<String>()
    private val _loading = MutableLiveData<Boolean>()

    val articles: LiveData<List<Article>>
        get() = _articles

    val loading: LiveData<Boolean>
        get() = _loading

    val error: LiveData<String>
        get() = _error

    fun getRandomDogs(context: Context) {
        _loading.value = true

        val articlesDao = getDb(context).articlesDao()

        ApiInterface.create().getNews("ru", "a031e9521cb34d0cb4d44027812de587")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    val articles = response.body()?.articles

                    articles?.map {
                        articlesDao.addOrUpdateArticle(it)
                    }

                    articles?.let {
                        _articles.postValue(it)
                    }

                    _loading.postValue(false)
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    _articles.postValue(articlesDao.getArticles())
                    _error.postValue(t.message)
                    _loading.postValue(false)
                }
            })
    }

    private fun getDb(context: Context): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, "articles")
            .allowMainThreadQueries()
            .build()
    }
}
