package com.example.a15_025

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var database: AppDatabase
    private val dataList = mutableListOf<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getDatabase(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DataAdapter(dataList,
            onEdit = { data ->
                val intent = Intent(this, AddDataActivity::class.java)
                intent.putExtra("editData", data)
                startActivity(intent)
            },
            onDelete = { data ->
                CoroutineScope(Dispatchers.IO).launch {
                    database.dataDao().deleteById(data.id)
                    loadData()
                }
            })
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.addFab).setOnClickListener {
            startActivity(Intent(this, AddDataActivity::class.java))
        }

        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = database.dataDao().getAllData()
            withContext(Dispatchers.Main) {
                dataList.clear()
                dataList.addAll(data)
                adapter.notifyDataSetChanged()
            }
        }
    }
}

