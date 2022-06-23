package com.example.themoviedatabase.ui.movie_details

sealed class Event {
    class Load private constructor(builder: Builder): Event() {
        val actions: Set<ActionType> = builder.actions

        class Builder(action: Load? = null) {
            val actions = HashSet<ActionType>()
                .apply { action?.actions?.let { addAll(it) } }

            fun add(actionType: ActionType): Builder {
                this.actions.add(actionType)
                return this
            }

            fun build(): Load {
                return Load(this)
            }
        }
    }
}

enum class ActionType {
    LOAD_MOVIE_DETAILS,
    LOAD_VIDEOS,
    LOAD_SIMILAR_MOVIES
}