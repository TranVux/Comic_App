package com.example.assignment.models

import java.io.Serializable

class Category(
    var id: String?,
    var title: String?,
    var thumbnail: String?,
    var displayColor: String?
) : Serializable {
    override fun toString(): String {
        return "$title "
    }
}