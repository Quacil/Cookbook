package com.example.cookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.cookbook.model.Recipe
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_recipe_full.*

class RecipeFullActivity : AppCompatActivity() {
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_full)
        val recipe = intent.getSerializableExtra("Item") as Recipe
        titleTextView.text = recipe.title
        descriptionTextView.text = recipe.description
        val imageView = findViewById<ImageView>(R.id.imageView)
        if (recipe.image.isNotEmpty()) {
            val image = storage.reference.child(recipe.image)
            Glide.with(this /* context */)
                .load(image)
                .into(imageView)
        }
    }
}
