package by.khryshchanovich.newsproject.ui.main

import androidx.lifecycle.*
import by.khryshchanovich.newsproject.api.dto.NewsResponse
import by.khryshchanovich.newsproject.api.entity.NewsData
import by.khryshchanovich.newsproject.api.mapper.NewsMapper
import by.khryshchanovich.newsproject.api.nertworking.API_KEY
import by.khryshchanovich.newsproject.api.nertworking.LANGUAGE
import by.khryshchanovich.newsproject.api.nertworking.NewsApi
import by.khryshchanovich.newsproject.api.retrofit.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PageViewModel : ViewModel() {

    private var _listNewsData = MutableLiveData<List<NewsData>>()
    private lateinit var category: String

    fun setCategory(_category: String) {
        category = _category
    }

    private fun setNewsDataList(listNewsData: List<NewsData>?) {
        _listNewsData.value = listNewsData
    }

    val setNewsData: LiveData<List<NewsData>> = Transformations.map(_listNewsData) {
        it
    }

    suspend fun getNewsDataList() = withContext(Dispatchers.IO) {
        val retrofit: NewsApi = RetrofitFactory().getRetrofit()
        try {
            val response: Response<NewsResponse> =
                retrofit.getNewsApiAsync(LANGUAGE, API_KEY, category).await()
            val newsResponse = response.body()
            val newsData = newsResponse?.news?.map {
                NewsMapper().map(it)
            }
            withContext(Dispatchers.Main) {
                setNewsDataList(newsData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        viewModelScope.launch {
            getNewsDataList()
        }
    }
}