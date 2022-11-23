package com.binkul.sudoku.engine;

import com.binkul.sudoku.board.Sudoku;
import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.Number;
import com.binkul.sudoku.element.Row;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleSolver implements Solver {

    private final Sudoku sudoku;

    public SimpleSolver(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public Status Solve(Sudoku sudoku) {
        return null;
    }

    private void removeValuesFromAllSudokuCells() {
        List<Cell> cells = sudoku.getCells();
        for (Cell cell : cells) {
            Set<Integer> values = sudoku.getRowColSecValues(cell);
            removeValuesFromSingleCellNumbers(cell, values);
        }
    }

    private void removeValuesFromSingleCellNumbers(Cell cell, Set<Integer> values) {
        cell.getNumbers().stream()
                .filter(i -> values.contains(i.getNumber()))
                .forEach(Number::unsetExist);
    }

}
