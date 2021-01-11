package by.khryshchanovich.newsproject.database

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.khryshchanovich.newsproject.R
import by.khryshchanovich.newsproject.database.adapter_vocabulary.VocabularyAdapter
import by.khryshchanovich.newsproject.database.dao.VocabularyDao
import by.khryshchanovich.newsproject.database.database_vocabulary.Db
import by.khryshchanovich.newsproject.database.entity.Vocabulary
import by.khryshchanovich.newsproject.database.utils.launchForResult
import by.khryshchanovich.newsproject.database.utils.launchIo
import by.khryshchanovich.newsproject.database.utils.launchUi

class ShowVocabularyActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_vocabulary)

        val db: VocabularyDao = Db.getDb(this).vocabularyDao()
        val vocabularyRecycler = findViewById<RecyclerView>(R.id.vocabulary_recycler)
        val wordSearch = findViewById<EditText>(R.id.word_search)

        launchIo {
            val result: List<Vocabulary> = db.getAllWords()

            launchForResult {
                launchUi {
                    val adapter = VocabularyAdapter(result)
                    vocabularyRecycler.adapter = adapter
                    vocabularyRecycler.layoutManager = LinearLayoutManager(this)
                    vocabularyRecycler.hasFixedSize()
                }
            }

            wordSearch.addTextChangedListener {
                vocabularyRecycler.adapter = VocabularyAdapter(result.filter { word ->
                    word.word.contains(wordSearch.text.toString(), true)
                })
            }
        }
    }
}