package by.khryshchanovich.newsproject.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabulary_table")
data class Vocabulary(
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "meaning_translation")
    val meaning: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}