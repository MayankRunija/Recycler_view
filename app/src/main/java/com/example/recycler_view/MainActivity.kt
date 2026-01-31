package com.example.recycler_view

import DirectoryItem
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        val initialData = mutableListOf<DirectoryItem>()
        for (i in 1..10) {
            initialData.add(DirectoryItem("Root Folder $i", 0, true))
        }

        val adapter = DirectoryAdapter(initialData) { item ->
            findViewById<TextView>(R.id.tv_breadcrumb).text = getPath(item)
        }
        rv.adapter = adapter

        // Setup Drag and Drop
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            // Visual effect: Fade the item when picked up
            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            // Restore visual state when dropped
            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }
        })

        itemTouchHelper.attachToRecyclerView(rv)
    }

    fun loadChildrenForItem(parentItem: DirectoryItem) {
        if (parentItem.isLoaded || !parentItem.isFolder) return

        val nextLevel = parentItem.level + 1
        val newSubItems = mutableListOf<DirectoryItem>()

        for (i in 1..10) {
            val isFolder = nextLevel < 7
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