package com.binkul.sudoku.engine;

import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.Number;

import java.util.List;

public class Functions {
    public static void removeValuesFromNumbers(Cell cell, List<Integer> values) {
        cell.getNumbers().stream()
                .filter(i -> values.contains(i.getNumber()))
                .forEach(Number::unsetExist);
    }
}
