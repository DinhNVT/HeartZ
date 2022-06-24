package com.example.heartz.model

import java.sql.Timestamp

data class HistoryDetails(
    val bpm: String,
    val outcome: String,
    val date: String,
    val hour: String,
    val time: Int
){

}