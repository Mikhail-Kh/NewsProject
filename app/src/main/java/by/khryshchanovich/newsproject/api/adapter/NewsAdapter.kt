package by.khryshchanovich.newsproject.api.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.khryshchanovich.newsproject.R
import by.khryshchanovich.newsproject.ui.main.ReadingActivity
import by.khryshchanovich.newsproject.api.entity.NewsData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val TITLE_KEY = "title"
const val AUTHOR_KEY = "author"
const val DATE_KEY = "date"
const val IMAGE_KEY = "image"
const val DESCRIPTION_KEY = "description"
const val NEWS_KEY = "news"

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsVH>() {
    private var listNews = ArrayList<NewsData?>()

    fun upDateList(list: List<NewsData?>) {
        listNews = list as ArrayList<NewsData?>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsVH(view)
    }

    override fun getItemCount() = listNews.size

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        listNews[position]?.let { holder.bind(it) }
    }

    class NewsVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var descriptionArticle: String
        private lateinit var dateArticle: String
        private lateinit var linkArticle: String
        private lateinit var pictureArticle: String

        private val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.getDefault())
        private val formatter = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())

        fun bind(newsData: NewsData) {
            with(itemView) {
                itemView.setOnClickListener(this@NewsVH)
                val dateTime = formatter.format(parser.parse(newsData.published))
                dateArticle = dateTime
                descriptionArticle = newsData.description.toString()
                linkArticle = newsData.url.toString()
                pictureArticle = newsData.image.toString()
                author_text_view.text = newsData.author
                title_text_view.text = newsData.title
                if (newsData.image != "None") {
                    Glide.with(this).load(newsData.image)
                        .override(130, 100)
                        .centerCrop()
                        .into(image_view)
                } else {
                    Glide.with(this).load(R.drawable.newspaper_icon_1)
                        .override(130, 100)
                        .centerCrop()
                        .into(image_view)
                }
            }
        }

        override fun onClick(view: View?) {
            val context = view?.context
            val intentReading = Intent(context, ReadingActivity::class.java)
            intentReading.putExtra(TITLE_KEY, view?.title_text_view?.text)
            intentReading.putExtra(AUTHOR_KEY, view?.author_text_view?.text)
            intentReading.putExtra(DATE_KEY, dateArticle)
            intentReading.putExtra(IMAGE_KEY, pictureArticle)
            intentReading.putExtra(DESCRIPTION_KEY, descriptionArticle)
            intentReading.putExtra(NEWS_KEY, linkArticle)
            context?.startActivity(intentReading)
        }
    }
}