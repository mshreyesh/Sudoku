package com.soulbuster.sudoku.game

class Board(val size : Int, val cells : List<Cell>){
    fun getCell(row : Int, col : Int) = cells[row * size + col]
}