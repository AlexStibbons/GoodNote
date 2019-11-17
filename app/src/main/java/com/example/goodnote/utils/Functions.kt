package com.example.goodnote.utils

import com.example.goodnote.database.models.*
import java.util.*

internal fun List<Tag>.toTagsString(): String {
    // long:
    // val names: List<String> = this.map { it.name }
   // return names.joinToString(separator = ", ")

    // shorter: map { it.name }.joinToString(separator = ", ")

    // shortest:
    return joinToString(separator = ", ") { it.name }

}

internal fun Note.setId() {
    val uiid = UUID.randomUUID().toString()

    //this.apply { _id = uiid }
}

internal fun Tag.setId() {
    val uuid = UUID.randomUUID().toString()

    //this.apply { _id = uuid }
}