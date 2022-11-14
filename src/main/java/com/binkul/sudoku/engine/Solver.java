package com.binkul.sudoku.engine;

import com.binkul.sudoku.board.Sudoku;

public interface Solver {
    Status Solve(Sudoku sudoku);
}
