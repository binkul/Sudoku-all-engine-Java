package com.binkul.sudoku.engine;

import com.binkul.sudoku.board.Sudoku;
import com.binkul.sudoku.data.ConstantData;
import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.Number;
import com.binkul.sudoku.element.Row;
import com.binkul.sudoku.element.ValueType;

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
    public Status Solve() {
        removeValuesFromAllSudokuCells();

        findAndSetSingleNumber();

        return null;
    }

    private void findAndSetSingleNumber() {
        sudoku.getCells().stream()
                .filter(i -> i.getValue() == ConstantData.NOT_SET_VALUE)
                .filter(Cell::isOnlyOneNumber)
                .forEach(i -> {
                    i.setValue(i.getLastExistingNumber());
                    i.setValueType(ValueType.SIMPLE_ALGORITHM);
                });
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
