package com.soulbuster.sudoku.view

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.soulbuster.sudoku.R
import com.soulbuster.sudoku.game.Cell
import kotlinx.android.synthetic.main.activity_main.*
import com.soulbuster.sudoku.view.custom.SudokuBoardView
import com.soulbuster.sudoku.viewmodel.PlaySudokuViewModel

class MainActivity : AppCompatActivity(), SudokuBoardView.onTouchListener {

    private lateinit var viewModel : PlaySudokuViewModel
    private lateinit var numberButtons : List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sudokuBoardView.registerListener(this)

        viewModel = ViewModelProviders.of(this).get(PlaySudokuViewModel::class.java)
        viewModel.sudokuGmae.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })
        viewModel.sudokuGmae.cellsLiveData.observe(this, Observer { updateCells(it) })
        viewModel.sudokuGmae.isTakingNotesLiveData.observe(this, Observer { updateNoteTakingUI(it) })
        viewModel.sudokuGmae.highlightedKeysLiveData.observe(this, Observer { updateHighlightedKeys(it) })

        numberButtons = listOf(btton1,btton2,btton3,btton4,btton5,btton6,btton7,btton8,btton9)
        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener { viewModel.sudokuGmae.handleInput(index+1) }
        }

        noteButton.setOnClickListener { viewModel.sudokuGmae.changeNoteTakingState() }
        deleteButton.setOnClickListener { viewModel.sudokuGmae.delete() }
    }

    private fun updateCells(cells : List<Cell>) = cells?.let {
        sudokuBoardView.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell : Pair<Int,Int?>) = cell?.let {
        sudokuBoardView.updateSelectedCellUI(cell.first, cell.second!!)
    }

    private fun updateNoteTakingUI(isNoteTaking : Boolean?) = isNoteTaking?.let {
        val color = if (it) ContextCompat.getColor(this,R.color.colorPrimary) else Color.LTGRAY
        noteButton.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }

    private fun updateHighlightedKeys(set: Set<Int>?) = set?.let {
        numberButtons.forEachIndexed{index, button ->
            val color = if (set.contains(index+1)) ContextCompat.getColor(this,R.color.colorPrimary) else Color.LTGRAY
            button.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun onCellTouched(row : Int, col : Int){
        viewModel.sudokuGmae.updatedSelectedCell(row,col)
    }
}