package com.example.cookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.cookbook.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_full.*

class RecipeFullActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_full)
        val recipe = intent.getSerializableExtra("Item") as Recipe
        textView.text = recipe.Title
    }
}
