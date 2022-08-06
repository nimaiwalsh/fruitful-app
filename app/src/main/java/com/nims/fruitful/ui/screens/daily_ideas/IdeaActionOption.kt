package com.nims.fruitful.ui.screens.daily_ideas

enum class IdeaActionOption(val title: String) {
    EditIdea("Edit idea"),
    ToggleFavourite("Toggle favourite"),
    DeleteIdea("Delete idea");

    companion object {
        fun getByTitle(title: String): IdeaActionOption {
            values().forEach { action ->
                if (title == action.title) return action
            }

            return EditIdea
        }

        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            values().forEach { taskAction -> options.add(taskAction.title) }
            return options
        }
    }
}