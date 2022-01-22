package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class EditTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val textField = findViewById<TextView>(R.id.newTask)

        // make the value of the text field what it was before to make it easier to edit
        val oldTask = getIntent().getStringExtra("taskDescription")
        textField.setText(oldTask)

        Log.i("Kidus", "Edit activity created for " + oldTask)

        // now attach the button to returning the new task and closing the activity
        findViewById<Button>(R.id.saveEdit).setOnClickListener {
            val newTaskDescription = findViewById<TextView>(R.id.newTask).text.toString()
            Log.i("Kidus", "Returning: " + newTaskDescription)
            val returnData = Intent()
            returnData.putExtra("newTaskDescription", newTaskDescription)
            setResult(RESULT_OK, returnData)
            finish()
        }

    }

}