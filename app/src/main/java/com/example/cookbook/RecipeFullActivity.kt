package com.example.cookbook

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.cookbook.model.Recipe
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_recipe_full.*

class RecipeFullActivity : AppCompatActivity() {
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_full)
        val recipe = intent.getSerializableExtra("Item") as Recipe
        textView.text = recipe.Title
        val imageView = findViewById<ImageView>(R.id.imageView)
        if (recipe.Image.isNotEmpty()) {
            val image = storage.reference.child(recipe.Image)
            Glide.with(this /* context */)
                .load(image)
                .into(imageView)
        }
    }
}
