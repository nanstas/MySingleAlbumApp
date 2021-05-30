package ru.netology.mysinglealbumapp.dto

data class Track(
    val id: Int,
    val file: String,
    val isPlayed: Boolean = false,
)