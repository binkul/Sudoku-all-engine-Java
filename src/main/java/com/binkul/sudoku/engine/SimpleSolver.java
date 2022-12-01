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

        List<Cell> cells = sudoku.getCells().stream()
                .filter(i -> i.getRow() == 0).toList();
        Set<Integer> values = new HashSet<>(sudoku.getValues(i -> i.getRow() == 0));
        boolean anyChange = false;

        for (Cell cell : cells) {
            if (cell.getValue() != ConstantData.NOT_SET_VALUE) continue;    // tylko dla pól bez numeru

            int valueChange = cell.getValue();
            int sizeChange = cell.getNumbersSize();

            removeValuesFromSingleCellNumbers(cell, values);                // usuń z możliwych wartości wpisane numery
            if (cell.isNoneNumber()) {                                      // jak nc nie zostało to błąd
                return Status.ERROR;
            } else if (cell.isOnlyOneNumber()) {                            // jak jest tylko jedna możliwa wartość to ją wpisz
                cell.setValue(cell.getLastExistingNumber());
                cell.setValueType(ValueType.SIMPLE_ALGORITHM);
            } else {                                                        // jak więcej możliwości, to druga część algorytmu
                Set<Integer> numbers = sudoku.getExistingNumbers(i -> i.getRow() == 0, cell);
                int value = cell.getNumbers().stream()
                                .filter(i -> !numbers.contains(i))
                                .findFirst().orElse(ConstantData.NOT_SET_VALUE);
                if (value != ConstantData.NOT_SET_VALUE) {
                    cell.setValue(value);
                    cell.setValueType(ValueType.ADVANCE_ALGORITHM);
                }
            }

            anyChange |= (valueChange != cell.getValue());
            anyChange |= (sizeChange != cell.getNumbersSize());
        }

        //removeValuesFromAllSudokuCells();

        //findAndSetSingleNumber();

        return sudoku.isAllFilled() ? Status.FINISHED : Status.NOT_FINISHED;
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

    private boolean removeValuesFromSingleCellNumbers(Cell cell, Set<Integer> values) {
        int size = cell.getNumbersSize();

        List<Integer> toRemove = cell.getNumbers().stream()
                .filter(i -> values.contains(i))
                .toList();

        toRemove.forEach(i -> cell.removeNumber(i));

        return size != cell.getNumbersSize();
    }

}
