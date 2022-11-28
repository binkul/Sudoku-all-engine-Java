package com.binkul.sudoku;

import com.binkul.sudoku.board.Sudoku;
import com.binkul.sudoku.engine.SimpleSolver;
import com.binkul.sudoku.engine.Solver;

public class Application {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku.SudokuBuilder()
                .setCell(1, 1, 9)
                .setCell(1, 2, 1)
                .setCell(1, 3, 5)
                .setCell(1, 4, 4)
                .setCell(1, 5, 2)
                .setCell(1, 6, 8)
                .setCell(1, 7, 3)
                .setCell(1, 9, 7)
                .setCell(2, 2, 7)
                .setCell(2, 6, 3)
                .setCell(2, 7, 5)
                .setCell(3, 2, 4)
                .setCell(3, 4, 5)
                .setCell(4, 1, 7)
                .setCell(4, 4, 8)
                .setCell(4, 8, 5)
                .setCell(5, 1, 8)
                .setCell(5, 5, 1)
                .setCell(5, 9, 3)
                .setCell(6, 2, 6)
                .setCell(6, 6, 5)
                .setCell(6, 9, 2)
                .setCell(7, 6, 9)
                .setCell(7, 8, 3)
                .setCell(8, 1, 5)
                .setCell(8, 3, 6)
                .setCell(8, 4, 1)
                .setCell(8, 8, 8)
                .setCell(9, 1, 3)
                .setCell(9, 4, 7)
                .setCell(9, 5, 8)
                .setCell(9, 7, 1)
                .setCell(9, 9, 5)
                .build();

        System.out.println(sudoku);

        Solver solver = new SimpleSolver(sudoku);
        solver.Solve();
        System.out.println(sudoku);
    }
}
