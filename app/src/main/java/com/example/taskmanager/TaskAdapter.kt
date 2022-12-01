package com.example.taskmanager

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.query.RealmResults

class TaskAdapter(
    private val context: Context,
    private var tasks: RealmResults<Task>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task: Task = tasks[position] ?: return
        holder.container.setOnClickListener {
            listener.onItemClick(task)
        }

        holder.imageView.setImageResource(task.imageId)
        holder.contentTextView.text = task.content
    }

    override fun getItemCount(): Int = tasks.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(tasks: RealmResults<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container : LinearLayout = view.findViewById(R.id.container)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val contentTextView: TextView = view.findViewById(R.id.contentTextView)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Task)
    }
}