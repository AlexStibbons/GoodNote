package com.example.goodnote.repository.domainModels

data class NoteDomanModel(
    val noteId: String,
    val title: String,
    val text: String,
    val tags: List<TagDomainModel>
)