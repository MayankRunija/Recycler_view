package com.example.recycler_view

import DirectoryItem
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        // Using true/false instead of FileType
        val data = listOf(
            DirectoryItem("app", 0, true, subItems = listOf(
                DirectoryItem("src", 1, true, subItems = listOf(
                    DirectoryItem("main", 2, true, subItems = listOf(
                        DirectoryItem("java", 3, true, subItems = listOf(
                            DirectoryItem("com.example", 4, true, subItems = listOf(
                                DirectoryItem("MainActivity.kt", 5, false)
                            ))
                        )),
                        DirectoryItem("res", 3, true, subItems = listOf(
                            DirectoryItem("layout", 4, true, subItems = listOf(
                                DirectoryItem("activity_main.xml", 5, false)
                            ))
                        )),
                        DirectoryItem("AndroidManifest.xml", 3, false)
                    ))
                )),
                DirectoryItem("build.gradle.kts", 1, false)
            ))
        )

        rv.adapter = DirectoryAdapter(data)
    }
}