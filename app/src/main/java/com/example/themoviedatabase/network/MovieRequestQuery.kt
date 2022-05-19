package com.example.themoviedatabase.network

class MovieRequestQuery private constructor(builder: Builder) {
    val sortBy: SortBy = builder.sortBy
    val page: Int? = builder.page

    class Builder {
        var sortBy: SortBy = SortBy.POPULARITY_DESC
        var page: Int? = null

        fun sortBy(sortBy: SortBy): Builder {
            this.sortBy = sortBy
            return this
        }

        fun page(page: Int): Builder {
            this.page = page
            return this
        }

        fun build(): MovieRequestQuery {
            return MovieRequestQuery(this)
        }
    }
}

enum class SortBy(val value: String) {
    POPULARITY_DESC("popularity.desc")
}