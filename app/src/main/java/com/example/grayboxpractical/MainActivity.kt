package com.example.grayboxpractical

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val numberOfItems = 12
    private val arrIndex = mutableListOf<Int>()
    private val arrItems = mutableListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
    }
    private fun setupUI() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        val adapter = GridViewAdapter(arrItems) { index -> onItemSelected(index) }
        recyclerView.adapter = adapter

        val random = checkAndGenerateRandomNumber()
        for (i in 0 until numberOfItems) {
            if (i == random) {
                arrItems.add(Item(CellType.BLUE))
            } else {
                arrItems.add(Item(CellType.BLANK))
            }
        }

        adapter.notifyDataSetChanged()
    }

    private fun checkAndGenerateRandomNumber(): Int? {
        if (arrIndex.size >= numberOfItems) return null
        var random = (0 until numberOfItems).random()
        while (arrIndex.contains(random)) {
            random = (0 until numberOfItems).random()
        }
        arrIndex.add(random)
        return random
    }

    private fun onItemSelected(index: Int) {
        val item = arrItems[index]
        if (item.type != CellType.BLUE) return

        for (data in arrItems) {
            if (data.type == CellType.RED) {
                data.type = CellType.GRAYED
            }
        }
        item.type = CellType.RED

        checkAndGenerateRandomNumber()?.let { nextIndex ->
            arrItems[nextIndex].type = CellType.BLUE
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }
}