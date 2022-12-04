package com.binkul.sudoku.engine;

import com.binkul.sudoku.board.Sudoku;
import com.binkul.sudoku.data.ConstantData;
import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.ValueType;

import java.util.HashSet;
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
        boolean anyChange;

        do {
            anyChange = false;
            List<Cell> cells = sudoku.getCells().stream()
                    .filter(i -> i.getValue() == ConstantData.NOT_SET_VALUE)
                    .toList();

            for (Cell cell : cells) {
                int sizeChange = cell.getNumbersSize();

                if (!findOnlyOneNumberAlgorithm(cell)) return Status.ERROR;

                anyChange |= cell.getValue() != ConstantData.NOT_SET_VALUE;
                anyChange |= (sizeChange != cell.getNumbersSize());
            }
        } while (anyChange);
        return sudoku.isAllFilled() ? Status.FINISHED : Status.NOT_FINISHED;
    }

    private boolean findOnlyOneNumberAlgorithm(Cell cell) {
        Set<Integer> values = sudoku.getRowColSecValues(cell);
        removeValuesFromSingleCellNumbers(cell, values);                // usuń z możliwych wartości wpisane numery

        if (cell.isNoneNumber()) {                                      // jak nc nie zostało to błąd
            return false;
        } else if (cell.isOnlyOneNumber()) {                            // jak jest tylko jedna możliwa wartość to ją wpisz
            cell.setValue(cell.getLastExistingNumber());
            cell.setValueType(ValueType.SIMPLE_ALGORITHM);
        } else {                                                        // jak więcej możliwości, to druga część algorytmu//
            findNotRepeatedValueAlgorithm(cell);
        }

        return true;
    }

    private void findNotRepeatedValueAlgorithm(Cell cell) {
        Set<Integer> numbers = sudoku.getExistingNumbers(i -> i.getRow() == cell.getRow(), cell);
        Set<Integer> numbersCol = sudoku.getExistingNumbers(i -> i.getColumn() == cell.getColumn(), cell);
        Set<Integer> numbersSection = sudoku.getExistingNumbers(i -> i.getSection() == cell.getSection(), cell);
        numbers.addAll(numbersCol);
        numbers.addAll(numbersSection);

        int value = cell.getNumbers().stream()
                .filter(i -> !numbers.contains(i))
                .findFirst().orElse(ConstantData.NOT_SET_VALUE);

        if (value != ConstantData.NOT_SET_VALUE) {
            cell.setValue(value);
            cell.setValueType(ValueType.ADVANCE_ALGORITHM);
        }
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
        List<Integer> toRemove = cell.getNumbers().stream()
                .filter(i -> values.contains(i))
                .toList();

        toRemove.forEach(i -> cell.removeNumber(i));
    }

}
