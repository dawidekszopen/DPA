package com.example.moviebooktracker

data class BooksFilms(
    val name: String,
    val gatunek: String,
    val opis: String,
    val ocena: Int,
    val isBook: Boolean,
    var obajrzane: Boolean
)
