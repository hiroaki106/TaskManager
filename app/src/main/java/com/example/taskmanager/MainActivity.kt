package com.example.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmUUID

class MainActivity : AppCompatActivity() {
    private val realm by lazy { (application as TodoApplication).realm }
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tasks: RealmResults<Task> = readAllTasks()

        val editText = findViewById<EditText>(R.id.memo_edit_text)
        val addButton = findViewById<Button>(R.id.add_button)

        addButton.setOnClickListener {
            val text = editText.text.toString()
            if (text.isEmpty()) {
                return@setOnClickListener
            }
            createTask(R.drawable.ic_launcher_background, text)

            editText.text.clear()
        }

        adapter = TaskAdapter(this, tasks, object : TaskAdapter.OnItemClickListener {
            override fun onItemClick(item: Task) {
                Toast.makeText(applicationContext, item.content + "を削除しました", Toast.LENGTH_SHORT).show()
                deleteTask(item.id)
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun readAllTasks() : RealmResults<Task> {
        return realm.query<Task>().find()
    }

    private fun createTask(imageId: Int, content: String) {
        realm.writeBlocking {
            copyToRealm(Task().apply {
                this.imageId = imageId
                this.content = content
            })
        }
        adapter.updateTasks(readAllTasks())
    }

    private fun deleteTask(id: RealmUUID) {
        realm.writeBlocking {
            val deleteTask = query<Task>("id == $0", id)
            delete(deleteTask)
        }
        adapter.updateTasks(readAllTasks())
    }
}