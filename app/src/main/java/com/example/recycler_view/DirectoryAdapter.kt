package com.example.recycler_view

import DirectoryItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DirectoryAdapter(
    private var fullList: List<DirectoryItem>,
    private val onItemSelected: (DirectoryItem) -> Unit // Callback for breadcrumbs
) : RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {

    private var visibleList = mutableListOf<DirectoryItem>()

    init { generateVisibleList() }

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

            // Indentation - Using 24dp to keep 8 levels visible on screen
            val params = root.layoutParams as ViewGroup.MarginLayoutParams
            params.marginStart = item.level * 24
            root.layoutParams = params

            if (item.isFolder) {
                arrow.visibility = View.VISIBLE
                arrow.rotation = if (item.isExpanded) 90f else 0f
                icon.setImageResource(R.drawable.ic_folder)
            } else {
                arrow.visibility = View.INVISIBLE
                icon.setImageResource(R.drawable.ic_file)
            }

            root.setOnClickListener {
                onItemSelected(item)
                if (item.isFolder) {
                    // LAZY LOAD HERE
                    if (!item.isLoaded) {
                        ((root.context) as MainActivity).loadChildrenForItem(item)
                    }

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