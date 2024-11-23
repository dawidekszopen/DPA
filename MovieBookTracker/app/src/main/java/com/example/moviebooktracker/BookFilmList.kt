package com.example.moviebooktracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookFilmList(private val BooksFilms: List<BooksFilms>): RecyclerView.Adapter<BookFilmList.BookFilmListHolder>() {
    class BookFilmListHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nazwa: TextView = itemView.findViewById(R.id.nazwa)
        val gatunek: TextView = itemView.findViewById(R.id.gatunek)
        val ocena: TextView = itemView.findViewById(R.id.ocena)
        val opis: TextView = itemView.findViewById(R.id.opis)
        val typ: TextView = itemView.findViewById(R.id.typ)
        val obejrzane: CheckBox = itemView.findViewById(R.id.obejrzane)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookFilmListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_film_list, parent, false)

        return BookFilmListHolder(itemView)
    }

    override fun getItemCount(): Int = BooksFilms.size

    override fun onBindViewHolder(holder: BookFilmListHolder, position: Int) {
        val curentItem = BooksFilms[position]
        holder.nazwa.text = curentItem.name
        holder.gatunek.text = curentItem.gatunek
        holder.ocena.text = "Ocena: ${curentItem.ocena}/10"
        holder.opis.text = curentItem.opis

        if (curentItem.isBook){
            holder.typ.text = "Typ: książka"
        }
        else{
            holder.typ.text = "Typ: Film"
        }

        holder.obejrzane.isChecked = curentItem.obajrzane

        holder.obejrzane.setOnClickListener {
            curentItem.obajrzane = !curentItem.obajrzane
        }
    }
}