package com.example.cpu.good

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cpu.good.databinding.ActivityCpuGoodBinding

class CpuGoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCpuGoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCpuGoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.list.layoutManager = LinearLayoutManager(this)
        val adapter = MainAdapter()
        binding.list.adapter = adapter

        for (i in 0 until 100) {
            adapter.add(Data(title = "Holman Beasley",
                date = 1428269768649))
            adapter.add(Data(title = "Stone Kidd",
                date = 1451418572731))
            adapter.add(Data(title = "Santos Dunlap",
                date = 1433545054011))
            adapter.add(Data(title = "Mooney Miranda",
                date = 1510624404842))
            adapter.add(Data(title = "Marian Hanson",
                date = 1467900453706))
            adapter.add(Data(title = "Cotton Stevenson",
                date = 1412726746959))
            adapter.add(Data(title = "Felicia Norman",
                date = 1437812933902))
            adapter.add(Data(title = "Clemons Clemons",
                date = 1455441076372))
            adapter.add(Data(title = "Jaime Webster",
                date = 1526476773545))
            adapter.add(Data(title = "Salas Sparks",
                date = 1448727114005))
        }
        adapter.notifyDataSetChanged()
    }
}