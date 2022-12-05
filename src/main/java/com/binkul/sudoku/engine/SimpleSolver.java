package com.binkul.sudoku.engine;

import com.binkul.sudoku.board.Sudoku;
import com.binkul.sudoku.data.ConstantData;
import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.ValueType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
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

            for (Cell cell : sudoku.getCells()) {
                if (cell.getValue() != ConstantData.NOT_SET_VALUE) continue;

                int sizeChange = cell.getNumbersSize();

                if (!findOnlyOneNumberAlgorithm(cell)) {
                    return Status.ERROR;
                }

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
        if (tryToSetValue(cell, getUniqueNumbers(i -> i.getRow() == cell.getRow()))) return;
        if (tryToSetValue(cell, getUniqueNumbers(i -> i.getColumn() == cell.getColumn()))) return;
        tryToSetValue(cell, getUniqueNumbers(i -> i.getSection() == cell.getSection()));
    }

    private boolean tryToSetValue(Cell cell, List<Integer> uniqueNumbers) {
        if (uniqueNumbers.size() > 0) {
            cell.getNumbers().stream()
                    .filter(i -> uniqueNumbers.contains(i))
                    .forEach(i -> {
                        cell.setValue(i);
                        cell.setValueType(ValueType.ADVANCE_ALGORITHM);
                    });
        }
        return cell.getValue() != ConstantData.NOT_SET_VALUE;
    }

    private List<Integer> getUniqueNumbers(Predicate<Cell> predicate) {
        return sudoku.getCells().stream()
                .filter(i -> i.getValue() == ConstantData.NOT_SET_VALUE)
                .filter(predicate)
                .map(i -> i.getNumbers())
                .flatMap(i -> i.stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(i -> i.getValue() == 1)
                .map(i -> i.getKey())
                .collect(Collectors.toList());
    }

    private void removeValuesFromSingleCellNumbers(Cell cell, Set<Integer> values) {
        List<Integer> toRemove = cell.getNumbers().stream()
                .filter(i -> values.contains(i))
                .toList();

        toRemove.forEach(i -> cell.removeNumber(i));
    }

}
