package com.nims.fruitful.model

import java.util.UUID

data class Idea(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val favourite: Boolean = false,
    val userId: String = ""
) {
    /** Used to update ui if the idea has been removed from database */
    var isRemoved = false
}