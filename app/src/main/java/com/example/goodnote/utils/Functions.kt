package com.example.goodnote.utils

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.MutableLiveData
import com.example.goodnote.database.models.*
import java.util.*

internal fun List<Tag>.toTagsString(): String = joinToString(separator = ", ") { it.name } // shortest
    // long:
    // val names: List<String> = this.map { it.name }
   // return names.joinToString(separator = ", ")

    // shorter: map { it.name }.joinToString(separator = ", ")

internal fun <T> MutableLiveData<List<T>>.addOne(item: T) {
    val value = this.value ?: emptyList()
    this.value = value + listOf(item)
}
