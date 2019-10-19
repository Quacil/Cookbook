package com.example.cookbook.model

import java.io.Serializable
import java.time.Duration

class Recipe:Serializable {
    var title = ""
    var description = ""
    var image = ""
    var prepTime: Duration = Duration.ZERO
    var ingredients = arrayListOf<Ingredient>()
}