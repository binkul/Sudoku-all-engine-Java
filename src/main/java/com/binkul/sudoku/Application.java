package com.binkul.sudoku;

import com.binkul.sudoku.board.Sudoku;
import com.binkul.sudoku.engine.SimpleSolver;
import com.binkul.sudoku.engine.Solver;

public class Application {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku.SudokuBuilder()
                .setCell(1, 2, 3)
                .setCell(1, 5, 7)

                .setCell(2, 1, 6)
                .setCell(2, 4, 1)
                .setCell(2, 5, 9)
                .setCell(2, 6, 5)

                .setCell(3, 2, 9)
                .setCell(3, 3, 8)
                .setCell(3, 8, 6)

                .setCell(4, 1, 8)
                .setCell(4, 5, 6)
                .setCell(4, 9, 3)

                .setCell(5, 4, 8)
                .setCell(5, 7, 3)
                .setCell(5, 9, 1)

                .setCell(6, 1, 7)
                .setCell(6, 5, 2)
                .setCell(6, 9, 6)

                .setCell(7, 2, 6)
                .setCell(7, 7, 2)
                .setCell(7, 8, 8)

                .setCell(8, 4, 4)
                .setCell(8, 6, 9)

                .setCell(9, 5, 8)
                .setCell(9, 8, 7)
                .setCell(9, 9, 9)
                .build();

        System.out.println(sudoku);

        Solver solver = new SimpleSolver(sudoku);
        solver.Solve();
        System.out.println(sudoku);
    }
}
