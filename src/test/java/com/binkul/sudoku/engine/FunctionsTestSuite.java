package com.binkul.sudoku.engine;

import com.binkul.sudoku.board.Sudoku;
import com.binkul.sudoku.element.Cell;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionsTestSuite {

    @Test
    public void testRemoveValuesFromNumbers() {
        // Given Arrange
        Cell cell = new Cell(0, 0);
        List<Integer> values = List.of(1, 4, 6, 8);
        // When Act
        boolean isRemoved = Functions.removeValuesFromNumbers(cell, values);
        List<Integer> numbers = cell.getNumbers();
        // Then Assert
        assertTrue(isRemoved);
        assertEquals(5, cell.getNumbersSize());
        assertTrue(numbers.contains(2));
        assertTrue(numbers.contains(3));
        assertTrue(numbers.contains(5));
        assertTrue(numbers.contains(7));
        assertTrue(numbers.contains(9));
        assertFalse(numbers.contains(1));
        assertFalse(numbers.contains(4));
        assertFalse(numbers.contains(6));
        assertFalse(numbers.contains(8));
    }

    @Test
    public void testRemoveEightValuesFromNumbers() {
        // Given Arrange
        Cell cell = new Cell(0, 0);
        List<Integer> values = List.of(1, 3, 4, 5, 6, 7, 8, 9);
        // When Act
        boolean isRemoved = Functions.removeValuesFromNumbers(cell, values);
        List<Integer> numbers = cell.getNumbers();
        // Then Assert
        assertTrue(isRemoved);
        assertTrue(cell.isOnlyOneNumber());
        assertEquals(1, cell.getNumbersSize());
        assertTrue(numbers.contains(2));
        assertFalse(numbers.contains(1));
        assertFalse(numbers.contains(3));
        assertFalse(numbers.contains(4));
        assertFalse(numbers.contains(5));
        assertFalse(numbers.contains(6));
        assertFalse(numbers.contains(7));
        assertFalse(numbers.contains(8));
        assertFalse(numbers.contains(9));
    }

    @Test void testRemoveCellValuesFromSudoku() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        Cell cell = sudoku.getCell(3, 4);

        // When Act
        Set<Integer> values = sudoku.getRowColSecValues(cell);
        boolean isRemoved = Functions.removeValuesFromNumbers(cell, new ArrayList<>(values));
        List<Integer> numbers = cell.getNumbers();

        // Then Assert
        assertEquals(4, numbers.size());
        assertTrue(numbers.contains(3));
        assertTrue(numbers.contains(4));
        assertTrue(numbers.contains(6));
        assertTrue(numbers.contains(9));
    }

    private Sudoku getTestSudoku() {
        return new Sudoku.SudokuBuilder()
                .setCell(1, 1, 9)
                .setCell(1, 3, 5)
                .setCell(1, 5, 2)
                .setCell(1, 6, 8)
                .setCell(1, 9, 7)
                .setCell(2, 2, 7)
                .setCell(2, 6, 3)
                .setCell(2, 7, 5)
                .setCell(2, 9, 6)
                .setCell(3, 2, 4)
                .setCell(3, 4, 5)
                .setCell(4, 1, 7)
                .setCell(4, 4, 8)
                .setCell(4, 8, 5)
                .setCell(5, 1, 8)
                .setCell(5, 5, 1)
                .setCell(5, 9, 3)
                .setCell(6, 2, 6)
                .setCell(6, 6, 5)
                .setCell(6, 9, 2)
                .setCell(7, 6, 9)
                .setCell(7, 8, 3)
                .setCell(8, 1, 5)
                .setCell(8, 3, 6)
                .setCell(8, 4, 1)
                .setCell(8, 8, 8)
                .setCell(9, 1, 3)
                .setCell(9, 4, 7)
                .setCell(9, 5, 8)
                .setCell(9, 7, 1)
                .setCell(9, 9, 5)
                .build();
    }
}