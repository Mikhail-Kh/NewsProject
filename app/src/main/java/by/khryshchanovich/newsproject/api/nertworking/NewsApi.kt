package by.khryshchanovich.newsproject.api.nertworking

import by.khryshchanovich.newsproject.api.dto.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val LANGUAGE = "en"
const val API_KEY = "kpG1Xczxk2De5xhK7tMuvX9Cxth_pyzRhrGgDoiQZsfGdVzO"

interface NewsApi {

    @GET("/v1/latest-news")
    fun getNewsApiAsync(
        @Query("language")
        language: String = LANGUAGE,
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("category")
        category: String
    ): Deferred<Response<NewsResponse>>
}