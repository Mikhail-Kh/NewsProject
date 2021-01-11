package by.khryshchanovich.newsproject.api.dto

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("news")
    val news: List<New?>? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("status")
    val status: String? = null
) {
    data class New(
        @SerializedName("author")
        val author: String? = null,
        @SerializedName("category")
        val category: List<String?>? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("language")
        val language: String? = null,
        @SerializedName("published")
        val published: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("url")
        val url: String? = null
    )
}