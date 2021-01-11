package by.khryshchanovich.newsproject.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import by.khryshchanovich.newsproject.R
import by.khryshchanovich.newsproject.api.adapter.*
import by.khryshchanovich.newsproject.database.dao.VocabularyDao
import by.khryshchanovich.newsproject.database.database_vocabulary.Db
import by.khryshchanovich.newsproject.database.entity.Vocabulary
import by.khryshchanovich.newsproject.database.utils.launchIo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class ReadingActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var webLink: String

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        val intentTitle = intent.getStringExtra(TITLE_KEY)
        val intentAuthor = intent.getStringExtra(AUTHOR_KEY)
        val intentDate = intent.getStringExtra(DATE_KEY)
        val intentImage = intent.getStringExtra(IMAGE_KEY)
        val intentDescription = intent.getStringExtra(DESCRIPTION_KEY)
        val intentLink = intent.getStringExtra(NEWS_KEY)

        val readingTitle = findViewById<TextView>(R.id.reading_title_tv)
        val readingAuthor = findViewById<TextView>(R.id.reading_author_tv)
        val readingDate = findViewById<TextView>(R.id.reading_date_tv)
        val readingImage = findViewById<ImageView>(R.id.reading_image_view)
        val readingDescription = findViewById<TextView>(R.id.reading_description_tv)
        val readingMore = findViewById<TextView>(R.id.read_more_text_view)

        readingMore.setOnClickListener(this)
        readingTitle.text = intentTitle
        readingAuthor.text = intentAuthor
        readingDate.text = intentDate
        Picasso.get().load(intentImage).into(readingImage)
        readingDescription.text = intentDescription
        if (intentLink != null) {
            webLink = intentLink
        }

        val fabReading: FloatingActionButton = findViewById(R.id.fab_reading)
        fabReading.setOnClickListener {
            val bsd = BottomSheetDialog(this)
            val viewFragment = layoutInflater.inflate(R.layout.fragment_scrolling, null)

            val cancelButton = viewFragment.findViewById<Button>(R.id.cancel_button_bsb)
            cancelButton.setOnClickListener {
                bsd.cancel()
            }
            val wordET = viewFragment.findViewById<EditText>(R.id.enter_word_edit_text)
            val meaningET = viewFragment.findViewById<EditText>(R.id.enter_word_meaning_edit_text)

            val db: VocabularyDao? = Db.getDb(this).vocabularyDao()

            val addWordButton = viewFragment.findViewById<Button>(R.id.add_button_bsb)
            addWordButton.setOnClickListener {
                if ((wordET.text.toString() == "")
                    or (meaningET.text.toString() == "")
                ) {
                    Toast.makeText(this, "Enter word and meaning!", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val word = wordET.text.toString()
                val meaning = meaningET.text.toString()
                val vocabulary = Vocabulary(word, meaning)

                launchIo {
                    db?.addWord(vocabulary)
                }
                Toast.makeText(this, "Word added", Toast.LENGTH_SHORT).show()
                wordET.text.clear()
                meaningET.text.clear()
            }
            bsd.setCancelable(false)
            bsd.setContentView(viewFragment)
            bsd.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(view: View?) {
        val context = view?.context
        val intentReadMore = Intent(context, WebSiteActivity::class.java)
        intentReadMore.putExtra(NEWS_KEY, webLink)
        context?.startActivity(intentReadMore)
    }
}