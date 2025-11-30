package com.asisten.lp_8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asisten.lp_8.databinding.ActivityMainBinding
import com.asisten.lp_8.databinding.UploadDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        booksRef = FirebaseDatabase.getInstance().getReference("Books")
        fetchData()
        addData()
    }


    private fun fetchData() {
        val database = FirebaseDatabase.getInstance()
        val booksRef = database.getReference("Books")

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
                Toast.makeText(
                    this@MainActivity,
                    "Firebase Error: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun addData() {
        val fab = binding.fabAddBooks


        fab.setOnClickListener {
            val dialogBinding = UploadDialogBinding.inflate(LayoutInflater.from(this))



            MaterialAlertDialogBuilder(this)
                .setTitle("Tambah Buku")
                .setView(dialogBinding.root)
                .setPositiveButton("Tambah") { dialog, _ ->

                    val title = dialogBinding.editTextTitleBook.text.toString()
                    val release = dialogBinding.editTextRelease.text.toString()

                    if (title.isEmpty() || release.isEmpty()) {
                        Toast.makeText(this, "Isi semua data!", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    val id = booksRef.push().key
                    val newBook = Book(title, release)

                    id?.let {
                        booksRef.child(it).setValue(newBook)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Data berhasil ditambah!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { error ->
                                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                            }
                    }

                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

}
