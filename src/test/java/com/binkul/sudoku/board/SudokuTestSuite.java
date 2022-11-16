package com.binkul.sudoku.board;

import com.binkul.sudoku.data.ConstantData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuTestSuite {

    @Test
    public void testSudokuGetRowValues() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();

        // When Act
        List<Integer> values1 = sudoku.getRowValues(1);
        List<Integer> values2 = sudoku.getRowValues(8);

        // Then Assert
        assertEquals(4, values1.size());
        assertTrue(values1.contains(3));
        assertTrue(values1.contains(5));
        assertTrue(values1.contains(6));
        assertTrue(values1.contains(7));
        assertFalse(values1.contains(1));
        assertFalse(values1.contains(2));
        assertFalse(values1.contains(4));
        assertFalse(values1.contains(8));
        assertFalse(values1.contains(9));

        assertEquals(5, values2.size());
        assertTrue(values2.contains(1));
        assertTrue(values2.contains(3));
        assertTrue(values2.contains(5));
        assertTrue(values2.contains(7));
        assertTrue(values2.contains(8));
        assertFalse(values2.contains(2));
        assertFalse(values2.contains(4));
        assertFalse(values2.contains(6));
        assertFalse(values2.contains(9));
    }

    @Test
    public void testSudokuGetColumnValues() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();

        // When Act
        List<Integer> values1 = sudoku.getColumnValues(0);
        List<Integer> values2 = sudoku.getColumnValues(6);

        // Then Assert
        assertEquals(5, values1.size());
        assertTrue(values1.contains(3));
        assertTrue(values1.contains(5));
        assertTrue(values1.contains(7));
        assertTrue(values1.contains(8));
        assertTrue(values1.contains(9));
        assertFalse(values1.contains(1));
        assertFalse(values1.contains(2));
        assertFalse(values1.contains(4));
        assertFalse(values1.contains(6));

        assertEquals(2, values2.size());
        assertTrue(values2.contains(1));
        assertTrue(values2.contains(5));
        assertFalse(values2.contains(2));
        assertFalse(values2.contains(3));
        assertFalse(values2.contains(4));
        assertFalse(values2.contains(6));
        assertFalse(values2.contains(7));
        assertFalse(values2.contains(8));
        assertFalse(values2.contains(9));
    }

    @Test
    public void testSudokuGetSectionValues() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();

        // When Act
        List<Integer> values1 = sudoku.getSectionValues(4);
        List<Integer> values2 = sudoku.getSectionValues(8);

        // Then Assert
        assertEquals(3, values1.size());
        assertTrue(values1.contains(1));
        assertTrue(values1.contains(5));
        assertTrue(values1.contains(8));
        assertFalse(values1.contains(2));
        assertFalse(values1.contains(3));
        assertFalse(values1.contains(4));
        assertFalse(values1.contains(6));
        assertFalse(values1.contains(7));
        assertFalse(values1.contains(9));

        assertEquals(4, values2.size());
        assertTrue(values2.contains(1));
        assertTrue(values2.contains(3));
        assertTrue(values2.contains(5));
        assertTrue(values2.contains(8));
        assertFalse(values2.contains(2));
        assertFalse(values2.contains(4));
        assertFalse(values2.contains(6));
        assertFalse(values2.contains(7));
        assertFalse(values2.contains(9));
    }

    @Test
    public void testSudokuGetRowExistingNumbers() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        for (int i = 1; i < ConstantData.WIDTH; i++) {
            sudoku.getCell(0, i).getNumbers().get(0).unsetExist();
            sudoku.getCell(0, i).getNumbers().get(4).unsetExist();
            sudoku.getCell(0, i).getNumbers().get(8).unsetExist();
        }
        // When Act
        Set<Integer> values = sudoku.getExistingRowNumbers(0, sudoku.getCell(0,0));

        // Then Assert
        assertEquals(6, values.size());
        assertFalse(values.contains(1));
        assertFalse(values.contains(5));
        assertFalse(values.contains(9));
    }

    @Test
    public void testSudokuGetColumnExistingNumbers() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        for (int i = 1; i < ConstantData.WIDTH; i++) {
            sudoku.getCell(i, 1).getNumbers().get(0).unsetExist();
            sudoku.getCell(i, 1).getNumbers().get(4).unsetExist();
            sudoku.getCell(i, 1).getNumbers().get(8).unsetExist();
        }
        // When Act
        Set<Integer> values = sudoku.getExistingColumnNumbers(1, sudoku.getCell(0,1));

        // Then Assert
        assertEquals(6, values.size());
        assertFalse(values.contains(1));
        assertFalse(values.contains(5));
        assertFalse(values.contains(9));
    }

    @Test
    public void testSudokuGetSectionExistingNumbers() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                if (i == 3 && j == 3) continue;
                sudoku.getCell(i, j).getNumbers().get(0).unsetExist();
                sudoku.getCell(i, j).getNumbers().get(4).unsetExist();
                sudoku.getCell(i, j).getNumbers().get(8).unsetExist();
            }
        }
        // When Act
        Set<Integer> values = sudoku.getExistingSectionNumbers(4, sudoku.getCell(3,3));

        // Then Assert
        assertEquals(6, values.size());
        assertFalse(values.contains(1));
        assertFalse(values.contains(5));
        assertFalse(values.contains(9));
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