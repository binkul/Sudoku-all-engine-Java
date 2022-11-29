package com.binkul.sudoku.board;

import com.binkul.sudoku.data.ConstantData;
import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.ValueType;
import com.binkul.sudoku.engine.Functions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuTestSuite {

    @Test
    public void testSudokuGetRowValues() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();

        // When Act
        List<Integer> values1 = sudoku.getValues(i -> i.getRow() == 1);
        List<Integer> values2 = sudoku.getValues(i -> i.getRow() == 8);

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
        List<Integer> values1 = sudoku.getValues(i -> i.getColumn() == 0);
        List<Integer> values2 = sudoku.getValues(i -> i.getColumn() == 6);

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
        List<Integer> values1 = sudoku.getValues(i -> i.getSection() == 4);
        List<Integer> values2 = sudoku.getValues(i -> i.getSection() == 8);

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
    public void testSudokuGetRowColSectionValues() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        Cell cell = sudoku.getCell(0, 1);
        Cell cell1 = sudoku.getCell(3, 4);
        // When Act
        Set<Integer> values = sudoku.getRowColSecValues(cell);
        Set<Integer> values1 = sudoku.getRowColSecValues(cell1);

        // Then Assert
        System.out.println(values1);
        assertEquals(7, values.size());
        assertEquals(5, values1.size());
        assertFalse(values.contains(1));
        assertFalse(values.contains(3));
        assertTrue(values.contains(2));
        assertTrue(values.contains(4));
        assertTrue(values.contains(5));
        assertTrue(values.contains(6));
        assertTrue(values.contains(7));
        assertTrue(values.contains(8));
        assertTrue(values.contains(9));
        assertFalse(values1.contains(3));
        assertFalse(values1.contains(4));
        assertFalse(values1.contains(6));
        assertFalse(values1.contains(9));
        assertTrue(values1.contains(1));
        assertTrue(values1.contains(2));
        assertTrue(values1.contains(5));
        assertTrue(values1.contains(7));
        assertTrue(values1.contains(8));
    }

    @Test
    public void testSudokuGetRowExistingNumbers() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        for (int i = 1; i < ConstantData.WIDTH; i++) {
            sudoku.getCell(0, i).removeNumber(1);
            sudoku.getCell(0, i).removeNumber(5);
            sudoku.getCell(0, i).removeNumber(9);
        }
        // When Act
        int row = 0;
        Cell testCell = sudoku.getCell(row,0);
        Set<Integer> values = sudoku.getExistingNumbers(i -> i.getRow() == row, testCell);
        System.out.println(values);

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
            sudoku.getCell(i, 1).removeNumber(1);
            sudoku.getCell(i, 1).removeNumber(5);
            sudoku.getCell(i, 1).removeNumber(9);
        }
        // When Act
        int column = 1;
        Cell testCell = sudoku.getCell(0, column);
        Set<Integer> values = sudoku.getExistingNumbers(i -> i.getColumn() == column, testCell);

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
                sudoku.getCell(i, j).removeNumber(1);
                sudoku.getCell(i, j).removeNumber(5);
                sudoku.getCell(i, j).removeNumber(9);
            }
        }
        // When Act
        int section = 4;
        Cell testCell = sudoku.getCell(3,3);
        Set<Integer> values = sudoku.getExistingNumbers(i -> i.getSection() == section, testCell);

        // Then Assert
        assertEquals(6, values.size());
        assertFalse(values.contains(1));
        assertFalse(values.contains(5));
        assertFalse(values.contains(9));
    }

    @Test
    public void testSudokuDeepCopyExtended() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        Sudoku sudokuCopy = sudoku.deepCopy();
        Cell cell = sudoku.getCell(5,5);
        // When Act
        Set<Integer> values = sudoku.getRowColSecValues(cell);
        Functions.removeValuesFromNumbers(cell, new ArrayList<>(values));
        List<Integer> existingValues = cell.getExistingNumbers();
        // Then Assert
        System.out.println(values);
        System.out.println(existingValues);
        assertNotEquals(cell.getNumbersSize(), sudokuCopy.getCell(5,5).getNumbersSize());
        assertEquals(9, sudokuCopy.getCell(5,5).getNumbersSize());
        assertEquals(9, existingValues.size() + values.size());
    }

    @Test
    public void testDeepCopySimpleCopy() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        Sudoku sudokuCopy = sudoku.deepCopy();
        // When Act

        // Then Assert
        assertEquals(sudokuCopy.getCell(0,0).getValue(), sudoku.getCell(0,0).getValue());
        assertEquals(sudokuCopy.getCell(0,2).getValue(), sudoku.getCell(0,2).getValue());
        assertEquals(sudokuCopy.getCell(0,4).getValue(), sudoku.getCell(0,4).getValue());
        assertEquals(sudokuCopy.getCell(0,5).getValue(), sudoku.getCell(0,5).getValue());
        assertEquals(sudokuCopy.getCell(0,8).getValue(), sudoku.getCell(0,8).getValue());

        assertEquals(sudokuCopy.getCell(1,1).getValue(), sudoku.getCell(1,1).getValue());
        assertEquals(sudokuCopy.getCell(1,5).getValue(), sudoku.getCell(1,5).getValue());
        assertEquals(sudokuCopy.getCell(1,6).getValue(), sudoku.getCell(1,6).getValue());
        assertEquals(sudokuCopy.getCell(1,8).getValue(), sudoku.getCell(1,8).getValue());

        assertEquals(sudokuCopy.getCell(2,1).getValue(), sudoku.getCell(2,1).getValue());
        assertEquals(sudokuCopy.getCell(2,3).getValue(), sudoku.getCell(2,3).getValue());

        assertEquals(sudokuCopy.getCell(3,0).getValue(), sudoku.getCell(3,0).getValue());
        assertEquals(sudokuCopy.getCell(3,3).getValue(), sudoku.getCell(3,3).getValue());
        assertEquals(sudokuCopy.getCell(3,7).getValue(), sudoku.getCell(3,7).getValue());

        assertEquals(sudokuCopy.getCell(4,0).getValue(), sudoku.getCell(4,0).getValue());
        assertEquals(sudokuCopy.getCell(4,4).getValue(), sudoku.getCell(4,4).getValue());
        assertEquals(sudokuCopy.getCell(4,8).getValue(), sudoku.getCell(4,8).getValue());

        assertEquals(sudokuCopy.getCell(5,1).getValue(), sudoku.getCell(5,1).getValue());
        assertEquals(sudokuCopy.getCell(5,5).getValue(), sudoku.getCell(5,5).getValue());
        assertEquals(sudokuCopy.getCell(5,8).getValue(), sudoku.getCell(5,8).getValue());

        assertEquals(sudokuCopy.getCell(6,5).getValue(), sudoku.getCell(6,5).getValue());
        assertEquals(sudokuCopy.getCell(6,7).getValue(), sudoku.getCell(6,7).getValue());

        assertEquals(sudokuCopy.getCell(7,0).getValue(), sudoku.getCell(7,0).getValue());
        assertEquals(sudokuCopy.getCell(7,2).getValue(), sudoku.getCell(7,2).getValue());
        assertEquals(sudokuCopy.getCell(7,3).getValue(), sudoku.getCell(7,3).getValue());
        assertEquals(sudokuCopy.getCell(7,7).getValue(), sudoku.getCell(7,7).getValue());

        assertEquals(sudokuCopy.getCell(8,0).getValue(), sudoku.getCell(8,0).getValue());
        assertEquals(sudokuCopy.getCell(8,3).getValue(), sudoku.getCell(8,3).getValue());
        assertEquals(sudokuCopy.getCell(8,4).getValue(), sudoku.getCell(8,4).getValue());
        assertEquals(sudokuCopy.getCell(8,6).getValue(), sudoku.getCell(8,6).getValue());
        assertEquals(sudokuCopy.getCell(8,8).getValue(), sudoku.getCell(8,8).getValue());
    }

    @Test
    public void testDeepCopyValueType() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        Sudoku sudokuCopy = sudoku.deepCopy();
        Cell cell = sudoku.getCell(5,5);
        cell.setValueType(ValueType.SIMPLE_ALGORITHM);
        cell.setValue(9);
        cell.removeNumber(2);
        // When Act
        Cell cellCopy = sudokuCopy.getCell(5,5);
        // Then Assert
        assertNotEquals(cell.getValueType(), cellCopy.getValueType());
        assertNotEquals(cellCopy.getValue(), cell.getValue());
        assertEquals(8, cell.getNumbersSize());
        assertEquals(9, cellCopy.getNumbersSize());
    }

    @Test
    public void testFullFilledSudokuFalse() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        // When Act
        boolean result = sudoku.isAllFilled();
        // Then Assert
        assertFalse(result);
    }

    @Test
    public void testFullFilledSudokuTrue() {
        // Given Arrange
        Sudoku sudoku = new Sudoku.SudokuBuilder().build();
        for (int i = 0; i < ConstantData.WIDTH; i++) {
            for (int j = 0; j < ConstantData.WIDTH; j++) {
                sudoku.getCell(i, j).setValue(1);
            }
        }
        // When Act
        boolean result = sudoku.isAllFilled();
        // Then Assert
        assertTrue(result);
    }

    @Test
    public void testIsPartiallyFilledCorrect() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        // When Act
        boolean result = sudoku.isAllFilledCorrect();
        // Then Assert
        assertTrue(result);
    }

    @Test
    public void testIsPartiallyFilledWrong() {
        // Given Arrange
        Sudoku sudoku = getTestSudoku();
        Cell cell = sudoku.getCell(0,0);
        cell.setValue(2);
        // When Act
        boolean result = sudoku.isAllFilledCorrect();
        // Then Assert
        assertFalse(result);
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