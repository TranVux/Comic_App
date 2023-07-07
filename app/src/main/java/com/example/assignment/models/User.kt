package com.example.assignment.models

import java.io.Serializable

class User : Serializable {
    var id: String? = null
    var user_name: String? = null
    var email: String? = null
    var uid: String? = null
    var avatar: String? = null

    constructor(id: String?, user_name: String?, email: String?, uid: String?, avatar: String?) {
        this.id = id
        this.user_name = user_name
        this.email = email
        this.uid = uid
        this.avatar = avatar
    }

    constructor(id: String?, user_name: String?, email: String?, avatar: String?) {
        this.id = id
        this.user_name = user_name
        this.email = email
        this.avatar = avatar
    }

    constructor() {}
}