package com.binkul.sudoku.engine;

import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.Number;

import java.util.List;

public class Functions {
    public static boolean removeValuesFromNumbers(Cell cell, List<Integer> values) {
        long sizeBefore = cell.getNumbersSize();
        cell.getNumbers().stream()
                .filter(i -> values.contains(i.getNumber()))
                .forEach(Number::unsetExist);
        long sizeAfter = cell.getNumbersSize();

        return (sizeBefore - sizeAfter) > 0;
    }
}
