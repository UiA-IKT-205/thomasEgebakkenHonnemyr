package com.example.superpiano.data

data class Note(val value:String, val start:Long, val totalTime:Double){

    override fun toString(): String {
        return "$value, $start, $totalTime"
    }
}