package com.example.cookbook

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.cookbook.model.Recipe
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_recipe_edit.*
import java.util.*

class RecipeEditActivity : AppCompatActivity() {
    private val galleryCode = 1
    private var imageUri: Uri? = null
    private val storage = FirebaseStorage.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_edit)
        saveButton.setOnClickListener { save() }
        editImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, galleryCode)
        }
    }

    fun save() {
        val recipe = Recipe()
        recipe.title = editTitle.text.toString()
        recipe.description = editDescription.text.toString()

        if (imageUri != null) {
            val extension = "jpeg"
            val imageName = UUID.randomUUID().toString() + "." + extension
            val imageRef = storage.reference.child("images/$imageName")
            imageRef.putFile(imageUri!!)
            recipe.image = imageRef.path
        }
        val firestore = Firebase.firestore
        firestore.collection("recipes").add(recipe)
            .addOnSuccessListener { finish() }
            .addOnFailureListener {
                Snackbar.make(coordinatorLayout, "Adding recipe failed", Snackbar.LENGTH_LONG)
                    .show()
            }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            imageUri = data.data
            editImage.setImageURI(imageUri)
        }
    }
}
