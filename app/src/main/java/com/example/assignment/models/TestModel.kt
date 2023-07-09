package com.example.assignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test")
class TestModel(
    @PrimaryKey var id: String,
    var uid: String,
    var account_number: String,
    var iban: String,
    var bank_name: String,
    var routing_number: String,
    var swift_bic: String
) : java.io.Serializable {
}