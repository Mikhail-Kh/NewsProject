package by.khryshchanovich.newsproject.api.mapper

import by.khryshchanovich.newsproject.api.dto.NewsResponse
import by.khryshchanovich.newsproject.api.entity.NewsData

class NewsMapper {

    fun map(from: NewsResponse.New?): NewsData {
        return NewsData(
            author = from?.author.orEmpty(),
            description = from?.description.orEmpty(),
            image = from?.image.orEmpty(),
            published = from?.published.orEmpty(),
            title = from?.title.orEmpty(),
            url = from?.url.orEmpty()
        )
    }
}