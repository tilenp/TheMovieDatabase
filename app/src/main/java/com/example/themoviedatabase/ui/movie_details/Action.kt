package com.example.themoviedatabase.ui.movie_details

sealed class Action {
    data class SelectVideo(val url: String) : Action()

    class Load private constructor(builder: Builder): Action() {
        val actions: List<ActionType> = builder.actions

        class Builder(action: Load? = null) {
            val actions = ArrayList<ActionType>()
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
    LOAD_VIDEOS
}