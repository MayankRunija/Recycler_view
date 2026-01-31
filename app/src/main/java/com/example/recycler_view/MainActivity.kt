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
            DirectoryItem("New York City", 0, true, subItems = listOf(
                DirectoryItem("Manhattan District", 1, true, subItems = listOf(
                    DirectoryItem("Upper East Side", 2, true, subItems = listOf(
                        DirectoryItem("5th Avenue", 3, true, subItems = listOf(
                            DirectoryItem("Skyline Towers (Building A)", 4, true, subItems = listOf(
                                DirectoryItem("Penthouse Suite", 5, true, subItems = listOf(
                                    DirectoryItem("Living Room", 6, true, subItems = listOf(
                                        DirectoryItem("Main Chandelier (Smart Light)", 7, false),
                                        DirectoryItem("AC Thermostat", 7, false),
                                        DirectoryItem("Security Camera", 7, false)
                                    )),
                                    DirectoryItem("Kitchen", 6, true, subItems = listOf(
                                        DirectoryItem("Smart Fridge", 7, false),
                                        DirectoryItem("Coffee Machine", 7, false)
                                    ))
                                ))
                            ))
                        ))
                    ))
                ))
            )),
            DirectoryItem("System Logs.txt", 0, false)
        )

        rv.adapter = DirectoryAdapter(data)
    }
}