package com.example.goodnote.repository.domainModels

class NoteDomanModel(
    val id: Int,
    val noteId: String,
    val title: String,
    val text: String,
    val tags: List<TagDomainModel>
)