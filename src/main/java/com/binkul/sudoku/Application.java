package com.binkul.sudoku;

import com.binkul.sudoku.board.Sudoku;

public class Application {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku.SudokuBuilder()
                .setCell(1, 1, 1)
                .setCell(5, 3, 5)
                .setCell(7, 1, 8)
                .setCell(3, 8, 2)
                .setCell(9, 9, 9)
                .build();

        System.out.println(sudoku);
    }
}
