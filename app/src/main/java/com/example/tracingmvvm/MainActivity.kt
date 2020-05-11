package com.example.tracingmvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set contactItemClick & contactItemLongClick lambda
        val adapter = ContactAdapter({ contact ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
        }, { contact ->
            deleteDialog(contact)
        })

        val lm = LinearLayoutManager(this)
        main_recyclerview.adapter = adapter
        main_recyclerview.layoutManager = lm
        main_recyclerview.setHasFixedSize(true)

        // ViewModel 접근부분
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contacts ->
            adapter.setContacts(contacts!!)
        })

        main_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        // 반복문
        for(x in 1..5){
            println(x)
            Log.e("dong", x.toString());
        }

        var arrayList = ArrayList<String>()
        for(s in arrayList){
            Log.e("dong", "value : " + s)
        }

        Log.e("dong func", Dong(5, 5).toString())
    }



    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()

        
    }
}

