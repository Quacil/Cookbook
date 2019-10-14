package com.example.cookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookbook.model.Recipe
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_recipe_edit.*

class RecipeEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_edit)
        saveButton.setOnClickListener { view -> save() }
    }

    fun save() {
        var recipe = Recipe()
        recipe.Title = editTitle.text.toString()
        recipe.Description = editDescription.text.toString()
        val firestore = Firebase.firestore
        firestore.collection("recipes").add(recipe)
            .addOnSuccessListener { finish() }
            .addOnFailureListener{
                Snackbar.make(coordinatorLayout, "Adding recipe failed", Snackbar.LENGTH_LONG).show()}
    }
}
