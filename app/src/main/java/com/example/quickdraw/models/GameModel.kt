package com.example.quickdraw.models

import java.util.*

data class Game(var players : List<User>? = null, var roundCounter : Long? = null , var score : List<Map<String, Int>>? = null)