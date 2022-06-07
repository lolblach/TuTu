package com.lolblach333.tutu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lolblach333.tutu.data.news.Article

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdateArticle(account: Article)

    @Query("SELECT * FROM Articles")
    fun getArticles(): List<Article>
}
