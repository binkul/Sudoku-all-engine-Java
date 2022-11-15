package com.binkul.sudoku.engine;

import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.Number;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionsTestSuite {

    @Test
    public void testRemoveValuesFromNumbers() {
        // Given Arrange
        Cell cell = new Cell(0, 0);
        List<Integer> values = List.of(1, 4, 6, 8);
        // When Act
        boolean isRemoved = Functions.removeValuesFromNumbers(cell, values);
        List<Integer> numbers = cell.getNumbers().stream()
                .filter(Number::exist)
                .map(Number::getNumber)
                .toList();
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
        List<Integer> numbers = cell.getNumbers().stream()
                .filter(Number::exist)
                .map(Number::getNumber)
                .toList();
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

}