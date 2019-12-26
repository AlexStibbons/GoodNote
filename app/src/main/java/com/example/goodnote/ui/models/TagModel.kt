package com.example.goodnote.ui.models

import com.yalantis.filter.model.FilterModel
import java.util.*

data class TagModel(
    val tagId: String = UUID.randomUUID().toString(),
    val name: String
) : FilterModel {

    override fun getText(): String {
        return this.name
    }

    override fun toString(): String  = "$name"
}