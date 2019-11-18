package com.example.goodnote.utils

import androidx.lifecycle.MutableLiveData
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

internal fun <T> MutableLiveData<List<T>>.addOne(item: T) {
    val value = this.value ?: emptyList()
    this.value = value + listOf(item)
}

internal fun Note.setId(): Note = this.apply {
    val uiid = UUID.randomUUID().toString()
    //_id = uiid
}

internal fun Tag.setId(): Tag = this.apply {
    val uuid = UUID.randomUUID().toString()
    //_id = uiid
}
