package com.example.a15_025

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class AddDataActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private var editData: DataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        database = AppDatabase.getDatabase(this)

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val saveButton: Button = findViewById(R.id.saveButton)

        editData = intent.getParcelableExtra("editData")
        editData?.let {
            nameEditText.setText(it.name)
            descriptionEditText.setText(it.description)
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (editData == null) {
                        database.dataDao().insert(DataModel(name = name, description = description))
                    } else {
                        database.dataDao().insert(editData!!.copy(name = name, description = description))
                    }
                    finish()
                }
            }
        }
    }
}
