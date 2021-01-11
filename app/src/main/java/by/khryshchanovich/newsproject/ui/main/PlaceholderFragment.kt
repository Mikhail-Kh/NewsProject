package by.khryshchanovich.newsproject.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.khryshchanovich.newsproject.R
import by.khryshchanovich.newsproject.api.adapter.NewsAdapter
import by.khryshchanovich.newsproject.api.dto.NewsResponse
import by.khryshchanovich.newsproject.api.mapper.NewsMapper
import by.khryshchanovich.newsproject.api.nertworking.API_KEY
import by.khryshchanovich.newsproject.api.nertworking.LANGUAGE
import by.khryshchanovich.newsproject.api.nertworking.NewsApi
import by.khryshchanovich.newsproject.api.retrofit.RetrofitFactory
import by.khryshchanovich.newsproject.database.dao.VocabularyDao
import by.khryshchanovich.newsproject.database.database_vocabulary.Db
import by.khryshchanovich.newsproject.database.entity.Vocabulary
import by.khryshchanovich.newsproject.database.utils.launchIo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import java.util.*

private const val ARG_SECTION_NUMBER = "section_number"

class PlaceholderFragment : Fragment() {

    lateinit var CATEGORY: String
    val TAB_CAT = TAB_TITLES.size

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val newsAdapter = NewsAdapter()
        val newsRecyclerView = root.findViewById(R.id.news_recycler_view) as RecyclerView
        newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        val retrofit: NewsApi = RetrofitFactory().getRetrofit()
        val fabFragment = root.findViewById<FloatingActionButton>(R.id.fab_fragment)
        val progressBar = root.findViewById<ProgressBar>(R.id.progress_bar_fragment)

        CoroutineScope(Dispatchers.IO).launch {
            for (position in 0..TAB_CAT) {
                if (position == arguments?.getInt(ARG_SECTION_NUMBER)) {
                    CATEGORY = resources.getString(TAB_TITLES[position]).toLowerCase(Locale.ROOT)
                    break
                }
            }

            try {
                val response: Response<NewsResponse> =
                    retrofit.getNewsApiAsync(LANGUAGE, API_KEY, CATEGORY).await()
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    val newsData = newsResponse?.news?.map {
                        NewsMapper().map(it)
                    }

                    withContext(Dispatchers.Main) {
                        if (newsData != null) {
                            newsAdapter.upDateList(newsData)
                            progressBar?.visibility = View.INVISIBLE
                            fabFragment?.show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fabFragment?.setOnClickListener {
            val bsd = BottomSheetDialog(root.context)
            val viewFragment = layoutInflater.inflate(R.layout.fragment_scrolling, null)

            val cancelButton = viewFragment.findViewById<Button>(R.id.cancel_button_bsb)
            cancelButton.setOnClickListener {
                bsd.cancel()
            }

            val wordET = viewFragment.findViewById<EditText>(R.id.enter_word_edit_text)
            val meaningET = viewFragment.findViewById<EditText>(R.id.enter_word_meaning_edit_text)

            val db: VocabularyDao? = context?.let { Db.getDb(it).vocabularyDao() }

            val addWordButton = viewFragment.findViewById<Button>(R.id.add_button_bsb)
            addWordButton.setOnClickListener {
                if ((wordET.text.toString() == "")
                    or (meaningET.text.toString() == "")
                ) {
                    Toast.makeText(context, "Enter word and meaning!", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val word = wordET.text.toString()
                val meaning = meaningET.text.toString()
                val vocabulary = Vocabulary(word, meaning)

                launchIo {
                    db?.addWord(vocabulary)
                }
                Toast.makeText(context, "Word added", Toast.LENGTH_SHORT).show()
                wordET.text.clear()
                meaningET.text.clear()
            }

            bsd.setCancelable(false)
            bsd.setContentView(viewFragment)
            bsd.show()
        }
        return root
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}