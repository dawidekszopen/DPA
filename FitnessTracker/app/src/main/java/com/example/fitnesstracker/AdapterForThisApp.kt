package com.example.fitnesstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterForThisApp (private val activitysList: List<ActivitysList>):
    RecyclerView.Adapter<AdapterForThisApp.Holder>() {
    class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nr = itemView.findViewById<TextView>(R.id.NrAct)
        val dystans = itemView.findViewById<TextView>(R.id.DystansAct)
        val czas = itemView.findViewById<TextView>(R.id.CzasAct)
        val kalorie = itemView.findViewById<TextView>(R.id.KalorieAct)
        val intensywnosc = itemView.findViewById<TextView>(R.id.IntensywnoscAct)
        val typ = itemView.findViewById<TextView>(R.id.TypAct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adaptero, parent, false)

        return Holder(itemView)
    }

    override fun getItemCount(): Int = activitysList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val curentItem = activitysList[position]

        holder.nr.text = "${position+1}"
        holder.dystans.text = "Dystans: ${curentItem.dystans}"
        holder.czas.text = "Czas: ${curentItem.czas}"
        holder.kalorie.text = "Czas: ${curentItem.kalorie}"
        holder.intensywnosc.text = "Intensywność: ${curentItem.intensywnosc}/10"

        if(curentItem.typ == 1){
            holder.typ.text = "Spacer"
        }
        else if(curentItem.typ == 2){
            holder.typ.text = "Bieganie"
        }
        else {
            holder.typ.text = "Trening Siłowy"
        }
    }
}