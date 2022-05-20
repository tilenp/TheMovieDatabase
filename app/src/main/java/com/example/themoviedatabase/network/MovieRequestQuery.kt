package com.example.themoviedatabase.network

class MovieRequestQuery private constructor(builder: Builder) {
    val sortBy: String = builder.sortBy
    val page: Int? = builder.page

    class Builder {
        var sortBy: String = SortBy.POPULARITY_DESC.value
        var page: Int? = null
        private val sortByList = arrayListOf<SortBy>()

        fun sortBy(sortBy: SortBy): Builder {
            sortByList.add(sortBy)
            return this
        }

        fun page(page: Int): Builder {
            this.page = page
            return this
        }

        fun build(): MovieRequestQuery {
            sortBy = buildSortByString()
            return MovieRequestQuery(this)
        }

        private fun buildSortByString(): String {
            return when {
                sortByList.isEmpty() -> SortBy.POPULARITY_DESC.value
                else -> sortByList.joinToString(separator = ",") { it.value }
            }
        }
    }
}

enum class SortBy(val value: String) {
    POPULARITY_DESC("popularity.desc")
}