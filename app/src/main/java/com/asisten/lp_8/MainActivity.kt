package com.asisten.lp_8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asisten.lp_8.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var rvBooks: RecyclerView
    private lateinit var booksRef : DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvBooks = binding.rvBooks
        rvBooks.layoutManager = LinearLayoutManager(this)

        booksRef = FirebaseDatabase.getInstance().getReference("books")

        fetchData()
        setupAddButton()
    }

    private fun fetchData() {

        booksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val books = mutableListOf<Book>()
                for (data in snapshot.children) {
                    val book = data.getValue(Book::class.java)
                    book?.let { books.add(it) }
                }
                rvBooks.adapter = BookAdapter(books)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupAddButton() {
        binding.fabAddBooks.setOnClickListener {
            val dialog = AddBookDialog(this, booksRef)
            dialog.show()
        }
    }
}