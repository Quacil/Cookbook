package com.example.cookbook.data

import com.example.cookbook.model.Recipe
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

object Recipes {
    val Items: MutableList<Recipe> = ArrayList()
    init{
        val firestore = Firebase.firestore
        val recipes = firestore.collection("recipes")
        recipes.get().addOnSuccessListener { querySnapshot-> Items.addAll(querySnapshot.toObjects()) }
    }
}