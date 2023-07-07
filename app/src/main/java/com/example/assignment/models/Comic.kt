package com.example.assignment.models

import java.io.Serializable

class Comic(
    var id: String?,
    var title: String?,
    var subTitle: String?,
    var country: String?,
    var synopsis: String?,
    var thumbnail: String?,
    var categories: String?,
    var author: Author?,
    var publishBy: User?
) : Serializable {

}