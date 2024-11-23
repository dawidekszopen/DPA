package com.example.moviebooktracker

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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

        val listBookFilm = mutableListOf(
            BooksFilms("idk", "idk", "lorem", 2, false, false)
        )

        val recyclerView: RecyclerView = findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = BookFilmList(listBookFilm)

        recyclerView.adapter = adapter


        val addBookFilmDialog = Dialog(this)
        addBookFilmDialog.setContentView(R.layout.add_book_film_layout)
        addBookFilmDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val nazwaBF:EditText = addBookFilmDialog.findViewById(R.id.nazwaBF)
        val gatunekBF:EditText = addBookFilmDialog.findViewById(R.id.gatunekBF)
        val opisBF:EditText = addBookFilmDialog.findViewById(R.id.opisBF)
        val ocenaBFText:TextView = addBookFilmDialog.findViewById(R.id.ocenaBFText)
        val ocenaBF:SeekBar = addBookFilmDialog.findViewById(R.id.ocenaBF)
        val forBRG:RadioGroup = addBookFilmDialog.findViewById(R.id.BorFRadioGroup)
        val sendBF:Button = addBookFilmDialog.findViewById(R.id.sendBtn)

        ocenaBF.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ocenaBFText.text = "Twoja ocena: $progress"

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        sendBF.setOnClickListener {
            if(nazwaBF.text.isNotBlank() && gatunekBF.text.isNotBlank() && opisBF.text.isNotBlank() && forBRG.checkedRadioButtonId != -1){
                if (forBRG.checkedRadioButtonId == 1){
                    listBookFilm.add(
                        BooksFilms(
                            nazwaBF.text.toString(),
                            nazwaBF.text.toString(),
                            nazwaBF.text.toString(),
                            ocenaBF.progress,
                            true,
                            false
                        ))
                }
                else{
                    listBookFilm.add(
                        BooksFilms(
                            nazwaBF.text.toString(),
                            gatunekBF.text.toString(),
                            opisBF.text.toString(),
                            ocenaBF.progress,
                            false,
                            false
                        ))
                }

                nazwaBF.text.clear()
                gatunekBF.text.clear()
                opisBF.text.clear()
                ocenaBF.progress = 1
                forBRG.clearCheck()
                addBookFilmDialog.dismiss()
                adapter.notifyDataSetChanged()
            }
        }

        val addBookFilm: Button = findViewById(R.id.AddBookFilmBtn)
        addBookFilm.setOnClickListener {
            addBookFilmDialog.show()
        }

        val saveBtn: Button = findViewById(R.id.save)
        val loadBtn: Button = findViewById(R.id.load)

        saveBtn.setOnClickListener {
            try {
                JsonManager.save(this, listBookFilm)
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
                listBookFilm.clear()
                listBookFilm.addAll(loadedList)
                adapter.notifyDataSetChanged()
            }
            catch (ex: Exception){
                Log.e("load", "ups: $ex")
            }
        }
    }
}