package com.example.goodnote.ui.models

import java.util.*

data class NoteDetailsModel(
    val noteId: String = UUID.randomUUID().toString(),
    var title: String,
    val text: String,
    val tags: MutableList<TagModel> = mutableListOf()
)