package com.example.heartz.model

import com.google.firebase.firestore.FieldValue
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime


data class History(
    val bpm: String,
    val outcome: String,
    val time: Timestamp
){
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "bpm" to this.bpm,
            "outcome" to this.outcome,
            "time" to this.time,
            )
    }
}