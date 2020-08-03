package com.soulbuster.sudoku.game

class Cell(
    val row : Int,
    val col : Int,
    var value : Int,
    var isStarting : Boolean = false,
    var notes: MutableSet<Int> = mutableSetOf()
)