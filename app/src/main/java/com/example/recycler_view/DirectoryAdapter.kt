package com.example.recycler_view

import DirectoryItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

class DirectoryAdapter(private var fullList: List<DirectoryItem>) :
    RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {

    private var visibleList = mutableListOf<DirectoryItem>()

    init {
        generateVisibleList()
    }

    private fun generateVisibleList() {
        visibleList.clear()
        fun addChildren(items: List<DirectoryItem>) {
            for (item in items) {
                visibleList.add(item)
                if (item.isExpanded) addChildren(item.subItems)
            }
        }
        addChildren(fullList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_name)
        val arrow: ImageView = view.findViewById(R.id.iv_arrow)
        val icon: ImageView = view.findViewById(R.id.iv_icon)
        val root: LinearLayout = view.findViewById(R.id.root_layout)

        fun bind(item: DirectoryItem) {
            name.text = item.name

            val params = root.layoutParams as ViewGroup.MarginLayoutParams
            params.marginStart = item.level * 40
            root.layoutParams = params

            if (item.isFolder) {
                arrow.visibility = View.VISIBLE
                arrow.rotation = if (item.isExpanded) 90f else 0f
            } else {
                arrow.visibility = View.INVISIBLE
            }

            root.setOnClickListener {
                if (item.isFolder) {
                    item.isExpanded = !item.isExpanded
                    generateVisibleList()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_directory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(visibleList[position])
    override fun getItemCount() = visibleList.size
}