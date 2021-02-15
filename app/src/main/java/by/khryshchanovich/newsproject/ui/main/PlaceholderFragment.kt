package by.khryshchanovich.newsproject.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.khryshchanovich.newsproject.R
import by.khryshchanovich.newsproject.api.adapter.NewsAdapter
import by.khryshchanovich.newsproject.api.entity.NewsData
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
import java.util.*

class PlaceholderFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
            .apply {
                setCategory(
                    resources.getString(TAB_TITLES[arguments?.getInt(ARG_SECTION_NUMBER) ?: 0])
                        .toLowerCase(
                            Locale.ROOT
                        )
                )
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val newsRecyclerView = root.findViewById(R.id.news_recycler_view) as RecyclerView
        newsAdapter = NewsAdapter()
        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        val fabFragment = root.findViewById<FloatingActionButton>(R.id.fab_fragment)
        val progressBar = root.findViewById<ProgressBar>(R.id.progress_bar_fragment)

        pageViewModel.setNewsData.observe(viewLifecycleOwner,
            Observer<List<NewsData>> {
                it.let {
                    if (it.isNotEmpty()) {
                        newsAdapter.upDateList(it)
                        progressBar?.visibility = View.INVISIBLE
                        fabFragment?.show()
                    } else {
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)

        showFABPlaceholderFragment(fabFragment, root)
        return root
    }

    @SuppressLint("InflateParams")
    private fun showFABPlaceholderFragment(fabFragment: FloatingActionButton, root: View) {
        fabFragment.setOnClickListener {
            val bsd = BottomSheetDialog(root.context)
            val viewFragment = layoutInflater.inflate(R.layout.fragment_scrolling, null)
            bsd.setCancelable(false)
            bsd.setContentView(viewFragment)
            bsd.show()

            val cancelButton = viewFragment.findViewById<Button>(R.id.cancel_button_bsb)
            cancelButton.setOnClickListener {
                bsd.cancel()
            }

            addWordDatabaseFragment(viewFragment)
        }
    }

    private fun addWordDatabaseFragment(viewFragment: View) {
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
    }

    override fun onRefresh() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                pageViewModel.getNewsDataList()
                swipeRefreshLayout.setColorSchemeColors(Color.RED)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    companion object {

        const val ARG_SECTION_NUMBER = "section_number"

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