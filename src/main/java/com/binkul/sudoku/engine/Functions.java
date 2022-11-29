package com.binkul.sudoku.engine;

import com.binkul.sudoku.element.Cell;

import java.util.List;

public class Functions {
    public static boolean removeValuesFromNumbers(Cell cell, List<Integer> values) {
        long sizeBefore = cell.getNumbersSize();

        List<Integer> toRemove = cell.getNumbers().stream()
                .filter(values::contains)
                .toList();

        toRemove.forEach(cell::removeNumber);

        long sizeAfter = cell.getNumbersSize();

        return (sizeBefore - sizeAfter) > 0;
    }
}
