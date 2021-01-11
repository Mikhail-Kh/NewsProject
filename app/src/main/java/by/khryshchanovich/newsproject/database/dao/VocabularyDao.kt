package by.khryshchanovich.newsproject.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.khryshchanovich.newsproject.database.entity.Vocabulary

@Dao
interface VocabularyDao {

    @Insert
    suspend fun addWord(word: Vocabulary)

    @Query("SELECT * FROM vocabulary_table")
    suspend fun getAllWords(): List<Vocabulary>
}