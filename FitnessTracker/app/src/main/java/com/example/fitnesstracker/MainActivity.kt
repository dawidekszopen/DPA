package com.example.fitnesstracker

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listAct = mutableListOf(
            ActivitysList(1, 1, 1, 1, 1),
        )

        val recyclerView: RecyclerView = findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = AdapterForThisApp(listAct)
        recyclerView.adapter = adapter

        val addActivityDialog = Dialog(this)
        addActivityDialog.setContentView(R.layout.add_new_activity)
        addActivityDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val dystans: EditText = addActivityDialog.findViewById(R.id.Dystans)
        val czas: EditText = addActivityDialog.findViewById(R.id.Czas)
        val kalorie: EditText = addActivityDialog.findViewById(R.id.Kalorie)
        val intensywnoscTxt: TextView = addActivityDialog.findViewById(R.id.IntensywnoscText)
        val intensywnosc: SeekBar = addActivityDialog.findViewById(R.id.Intensywnosc)
        val typ: RadioGroup = addActivityDialog.findViewById(R.id.Typ)
        val send: Button = addActivityDialog.findViewById(R.id.Send)

        intensywnosc.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                intensywnoscTxt.text = "Intensywność: $progress"

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        send.setOnClickListener{
            if(dystans.text.isNotBlank() && czas.text.isNotBlank() && kalorie.text.isNotBlank() && typ.checkedRadioButtonId != -1){
                listAct.add(
                    ActivitysList(
                        Integer.parseInt(dystans.text.toString()),
                        Integer.parseInt(czas.text.toString()),
                        Integer.parseInt(kalorie.text.toString()),
                        intensywnosc.progress,
                        typ.checkedRadioButtonId
                    ))
            }
            dystans.text.clear()
            czas.text.clear()
            kalorie.text.clear()
            intensywnosc.progress = 1
            typ.clearCheck()
            addActivityDialog.dismiss()
            adapter.notifyDataSetChanged()
        }


        val addBookFilm: Button = findViewById(R.id.AddBookFilmBtn)
        addBookFilm.setOnClickListener {
            addActivityDialog.show()
        }


        val saveBtn: Button = findViewById(R.id.save)
        val loadBtn: Button = findViewById(R.id.load)

        saveBtn.setOnClickListener {
            try {
                JsonManager.save(this, listAct)
                Toast.makeText(this, "Zapisano dane \uD83E\uDD73", Toast.LENGTH_SHORT).show()
            }
            catch (ex: Exception){
                Log.e("save", "ups: $ex")
            }
        }

        loadBtn.setOnClickListener {
            try {
                val loadedList = JsonManager.load(this)
                Toast.makeText(this, "Wczytano dane o długości ${loadedList.size} elementów \uD83E\uDD73", Toast.LENGTH_SHORT).show()
                listAct.clear()
                listAct.addAll(loadedList)
                adapter.notifyDataSetChanged()
            }
            catch (ex: Exception){
                Log.e("load", "ups: $ex")
            }
        }

    }
}