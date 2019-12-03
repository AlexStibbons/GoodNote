package com.example.goodnote.ui.models

import java.util.*

data class TagModel(
    val tagId: String = UUID.randomUUID().toString(),
    val name: String
)