package com.example.imagebutton

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri

const val REQUEST_GET_SINGLE_FILE = 1

class MainActivity : AppCompatActivity() {
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null){
            selectedImageUri = savedInstanceState.getParcelable("path")
            if (selectedImageUri != null) {
                imageView.setImageURI(selectedImageUri)
            }
        }

        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_GET_SINGLE_FILE){
            if (resultCode == Activity.RESULT_OK){
                selectedImageUri = data!!.data
                if(selectedImageUri != null) {
                    imageView.setImageURI(selectedImageUri)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(selectedImageUri != null)
            outState?.putParcelable("path", selectedImageUri)
    }
}
