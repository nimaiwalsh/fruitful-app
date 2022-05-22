package com.nims.fruitful.model

import java.util.UUID

data class Idea(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val userId: String = ""
)