package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A bridge that tells the RecyclerView how to display the data that we give it
 */
class TaskItemAdapter(val listOfItems: List<String>, val clickListener: ClickListener)
    : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface ClickListener {
        fun onItemLongClicked(position: Int)
        fun onItemClicked(position: Int)
    }

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the default string layout
        val stringView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)

        // Return a new holder instance
        return ViewHolder(stringView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // store references to elements in our layout view
        val textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)

            // register what needs to happen when the item is clicked/long-clicked

            itemView.setOnLongClickListener {
                clickListener.onItemLongClicked(adapterPosition)
                true
            }
            itemView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
                true
            }
        }
    }

}