package com.lolblach333.tutu.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lolblach333.tutu.data.news.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
}
