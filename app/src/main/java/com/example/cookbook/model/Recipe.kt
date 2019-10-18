package com.example.cookbook.model

import java.io.Serializable
import java.time.Duration

class Recipe:Serializable {
    var Title = ""
    var Description = ""
    var Image = ""
    var PrepTime = Duration.ZERO
}