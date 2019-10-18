package com.example.cookbook

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_recipe.view.*

class RecipeViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    val mTitleView: TextView = mView.recipeTitle
    val mDescriptionView: TextView = mView.recipeDescription
    val mImageView: ImageView = mView.recipeImage
    override fun toString(): String {
        return super.toString() + " '" + mTitleView.text + "'"
    }
}