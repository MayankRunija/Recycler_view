package com.example.recycler_view

import DirectoryItem
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        // Only generate the top 10 folders
        val initialData = mutableListOf<DirectoryItem>()
        for (i in 1..10) {
            initialData.add(DirectoryItem("Root Folder $i", 0, true))
        }

        rv.adapter = DirectoryAdapter(initialData) { item ->
            findViewById<TextView>(R.id.tv_breadcrumb).text = getPath(item)
        }
    }

    // Only generates 10 items for the specific folder clicked
    fun loadChildrenForItem(parentItem: DirectoryItem) {
        if (parentItem.isLoaded || !parentItem.isFolder) return

        val nextLevel = parentItem.level + 1
        val newSubItems = mutableListOf<DirectoryItem>()

        for (i in 1..10) {
            val isFolder = nextLevel < 7 // Limit to 8 levels (0-7)
            newSubItems.add(
                DirectoryItem(
                    name = if (isFolder) "Folder $i (L$nextLevel)" else "Device $i",
                    level = nextLevel,
                    isFolder = isFolder,
                    parent = parentItem
                )
            )
        }
        parentItem.subItems = newSubItems
        parentItem.isLoaded = true
    }

    private fun getPath(item: DirectoryItem): String {
        val path = mutableListOf<String>()
        var curr: DirectoryItem? = item
        while (curr != null) {
            path.add(0, curr.name)
            curr = curr.parent
        }
        return path.joinToString(" > ")
    }
}