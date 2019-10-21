package com.example.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cookbook.model.Ingredient
import kotlinx.android.synthetic.main.fragment_recipe_ingredient_edit.*

class RecipeIngredientEditFragment : Fragment() {
    private val ingredientViewIds = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflated = inflater.inflate(R.layout.fragment_recipe_ingredient_edit, container, false)
        inflated.findViewById<Button>(R.id.add_ingredient_button).setOnClickListener { view -> onIngredientAdded(view) }
        return inflated
    }

    fun getIngredients(): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        val list = view!!.findViewById<LinearLayout>(R.id.ingredients_list)
        ingredientViewIds.forEach { viewId ->
            var textView = list!!.findViewById<EditText>(viewId)
            if (textView.text.isNotBlank()) {
                val ingredient = Ingredient()
                ingredient.name = textView.text.toString()
                ingredients.add(ingredient)
            }
        }
        return ingredients
    }

    private fun onIngredientAdded(view: View) {
        val linearLayout = ingredients_list
        val textView = EditText(context)
        textView.hint = getString(R.string.edit_ingredient)
        textView.id = View.generateViewId()
        ingredientViewIds.add(textView.id)
        val removeButton = Button(context)
        removeButton.text = "remove"
        removeButton.id = View.generateViewId()
        removeButton.setOnClickListener {
            ingredientViewIds.remove(textView.id)
            linearLayout.removeView(textView)
            linearLayout.removeView(removeButton)
        }
        linearLayout.addView(textView)
        linearLayout.addView(removeButton)
    }
}
