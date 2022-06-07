package com.lolblach333.tutu

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lolblach333.tutu.data.ApiInterface
import com.lolblach333.tutu.data.AppDb
import com.lolblach333.tutu.data.ArticlesDao
import com.lolblach333.tutu.data.news.Article
import com.lolblach333.tutu.data.news.NewsResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun testApiCall() = runBlocking {
        ApiInterface.create().getNews("ru", "a031e9521cb34d0cb4d44027812de587")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    Assert.assertNotNull(response.body()?.articles)
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    throw t
                }
            })

        delay(5_000L)
    }

    @Test
    fun testCaching() {
        val baseArticle = Article(
            "author",
            "Title",
            "some text",
            null,
            null,
            System.currentTimeMillis().toString(),
            "Text"
        )

        val mockedList = mutableListOf(baseArticle)

        repeat(10) {
            val newArticle =
                baseArticle.copy(publishedAt = (System.currentTimeMillis() + Random.nextInt()).toString())

            mockedList.add(newArticle)
        }

        val articlesDao = getArticlesDao()

        mockedList.forEach {
            articlesDao.addOrUpdateArticle(it)
        }

        val cachedArticles = articlesDao.getArticles()

        Assert.assertEquals(mockedList, cachedArticles)
    }

    private fun getArticlesDao(): ArticlesDao {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDb::class.java
        ).build().articlesDao()
    }
}
