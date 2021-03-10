package com.example.superpiano.data

data class Note(val value:String, val start:String, val totalTime:Double){

    override fun toString(): String {
        return "$value, $start, $totalTime"
    }
}