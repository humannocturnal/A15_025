package com.example.a15_025

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        // Mendapatkan data yang dikirimkan dari MainActivity
        val item = intent.getStringExtra("ITEM")


        // Menampilkan data di TextView
        val detailTextView: TextView = findViewById(R.id.detailTextView)
        detailTextView.text = "Detail for: $item"
    }

}
