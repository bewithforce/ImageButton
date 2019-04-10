package com.example.imagebutton

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri


class MainActivity : AppCompatActivity() {
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState?.let {
            selectedImageUri = savedInstanceState.getParcelable(PATH)
            if (selectedImageUri != null && selectedImageUri != Uri.EMPTY) {
                imageView.setImageURI(selectedImageUri)
            }
        }

        button.setOnClickListener { openGallery() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_GET_SINGLE_FILE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            if (selectedImageUri != null && selectedImageUri != Uri.EMPTY) {
                imageView.setImageURI(selectedImageUri)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (selectedImageUri != null && selectedImageUri != Uri.EMPTY)
            outState?.putParcelable(PATH, selectedImageUri)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = INTENT_TYPE
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE)
    }

    private companion object {
        private const val REQUEST_GET_SINGLE_FILE = 1
        private const val INTENT_TYPE = "image/*"
        private const val PATH = "image/*"
    }
}
