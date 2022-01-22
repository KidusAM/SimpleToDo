package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListner = object : TaskItemAdapter.ClickListener {
            override fun onItemLongClicked(position: Int) {
                // remove the item from the list
                listOfTasks.removeAt(position)
                // notify the adapter that there has been a change
                adapter.notifyDataSetChanged()
                saveItems()
            }

            override fun onItemClicked(position: Int) {
                // Create the editing activity
                Log.i("Kidus", "position at " + position)
                val editIntent = Intent(this@MainActivity, EditTaskActivity::class.java)
                editIntent.putExtra("taskDescription", listOfTasks.get(position))
                startActivityForResult(editIntent, position)
            }
        }

        // Get the previous To-do list from the file
        loadItems()

        // detect when th user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            // Code in here is going to be executed when the user clicks the button
//            Log.i("Kidus", "User clicked on button");
//        }

        // Lookup recyclerView in layout
        adapter = TaskItemAdapter(listOfTasks, onLongClickListner)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // attach the adapter to the recyclerView
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button and input field
        findViewById<Button>(R.id.button).setOnClickListener {
            // grab the text from the text field @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that the data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // clear the input field so that they can enter a new task easily
            inputTextField.setText("")
            saveItems()
        }
    }

    /**
     * handle the return from the edit activity
     * @param requestCode: this is the position of the item that was just edited
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("Kidus", "Result for " + resultCode)
        if (resultCode == RESULT_OK) {
            val newTaskDescription : String? = data?.getExtras()?.getString("newTaskDescription")
            if (newTaskDescription != null ) {
                // update the string in the list and reload the view
                listOfTasks.set(requestCode, newTaskDescription)
                adapter.notifyItemChanged(requestCode)
                saveItems()
            }
        }
    }

    // Save the data that the user has inputted by r/w from file

    // Get the file we need. Every line is going to represent a task
    fun getDataFile() : File {
        Log.i("Kidus", filesDir.absolutePath.toString())
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in our file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save our items into the file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}