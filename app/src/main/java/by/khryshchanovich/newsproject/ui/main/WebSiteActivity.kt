package by.khryshchanovich.newsproject.ui.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import by.khryshchanovich.newsproject.R
import by.khryshchanovich.newsproject.api.adapter.NEWS_KEY
import by.khryshchanovich.newsproject.database.dao.VocabularyDao
import by.khryshchanovich.newsproject.database.database_vocabulary.Db
import by.khryshchanovich.newsproject.database.entity.Vocabulary
import by.khryshchanovich.newsproject.database.utils.launchIo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_web_site.view.*

class WebSiteActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled", "InflateParams")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_site)

        val intentNews = intent.getStringExtra(NEWS_KEY)

        val webView = findViewById<WebView>(R.id.web_view)
        webView.apply {
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            settings.useWideViewPort
            settings.loadWithOverviewMode
            webViewClient = MyWebViewClient()

            if (intentNews != null) {
                loadUrl(intentNews)
            } else {
                Toast.makeText(this@WebSiteActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        val fabWeb: FloatingActionButton = findViewById(R.id.fab_web)
        fabWeb.setOnClickListener {
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

    inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            view?.progress_bar_web?.visibility = View.INVISIBLE
        }
    }
}