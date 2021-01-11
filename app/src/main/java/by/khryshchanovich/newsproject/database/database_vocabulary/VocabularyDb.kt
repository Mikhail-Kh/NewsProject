package by.khryshchanovich.newsproject.database.database_vocabulary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.khryshchanovich.newsproject.database.dao.VocabularyDao
import by.khryshchanovich.newsproject.database.entity.Vocabulary

@Database(entities = [Vocabulary::class], version = 1)
abstract class VocabularyDb : RoomDatabase() {

    abstract fun vocabularyDao(): VocabularyDao
}

object Db {
    fun getDb(ctx: Context) =
        Room.databaseBuilder(ctx, VocabularyDb::class.java, "VocabularyDataBase").build()
}