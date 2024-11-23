package com.example.moviebooktracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

object JsonManager {
    private  const val  FILE_NAME = "savedData.json"

    fun save(context: Context, list: List<BooksFilms>){
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(list)
        val file = File(context.filesDir, FILE_NAME)

        file.writeText(jsonString)
    }

    fun load(context: Context): List<BooksFilms>{
        val file = File(context.filesDir, FILE_NAME)
        return if(file.exists()){
            val jsonString = file.readText()
            Gson().fromJson(jsonString, Array<BooksFilms>::class.java).toList()
        }else{
            emptyList()
        }
    }
}