package com.example.goodnote.utils

import com.example.goodnote.database.models.*

internal fun List<Tag>.toTagsString(): String {
    val names: List<String> = this.map { it.name }
    return names.joinToString(separator = ", ")
}