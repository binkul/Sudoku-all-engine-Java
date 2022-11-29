package com.binkul.sudoku.element;

import com.binkul.sudoku.data.ConstantData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTestSuite {

    @Test
    public void testCellCreation() {
        // Given Arrange
        Cell cell = new Cell(0,0);
        // When Act
        int size = cell.getNumbers().size();
        int value = cell.getValue();
        // Then Assert
        assertEquals(9, size);
        assertEquals(ConstantData.NOT_SET_VALUE,value);
        assertEquals(9, cell.getNumbersSize());
    }

    @Test
    public void testSectionCreation() {
        // Given Arrange
        Cell cell1 = new Cell(0,0);
        Cell cell2 = new Cell(0,3);
        Cell cell3 = new Cell(3,8);
        Cell cell4 = new Cell(7,2);
        Cell cell5 = new Cell(5,5);
        Cell cell6 = new Cell(8,8);
        // When Act
        int section1 = cell1.getSection();
        int section2 = cell2.getSection();
        int section3 = cell3.getSection();
        int section4 = cell4.getSection();
        int section5 = cell5.getSection();
        int section6 = cell6.getSection();
        // Then Assert
        assertEquals(0, section1);
        assertEquals(1, section2);
        assertEquals(5, section3);
        assertEquals(6, section4);
        assertEquals(4, section5);
        assertEquals(8, section6);
    }

    @Test
    public void testRemoveNumber() {
        // Given Arrange
        Cell cell = new Cell(0,0);
        // When Act
        cell.removeNumber(5);
        cell.removeNumber(9);
        int size = cell.getNumbersSize();
        // Then Assert
        System.out.println(cell.getNumbers());
        assertEquals(7, size);
        assertTrue(cell.getNumbers().contains(1));
        assertTrue(cell.getNumbers().contains(2));
        assertTrue(cell.getNumbers().contains(3));
        assertTrue(cell.getNumbers().contains(4));
        assertFalse(cell.getNumbers().contains(5));
        assertTrue(cell.getNumbers().contains(6));
        assertTrue(cell.getNumbers().contains(7));
        assertTrue(cell.getNumbers().contains(8));
        assertFalse(cell.getNumbers().contains(9));
    }

    @Test
    public void testRemoveEightNumbers() {
        // Given Arrange
        Cell cell = new Cell(0,0);
        // When Act
        cell.removeNumber(1);
        cell.removeNumber(2);
        cell.removeNumber(3);
        cell.removeNumber(4);
        cell.removeNumber(5);
        cell.removeNumber(6);
        cell.removeNumber(7);
        cell.removeNumber(8);
        // Then Assert
        assertTrue(cell.getNumbers().contains(9));
        assertTrue(cell.isOnlyOneNumber());
    }

    @Test
    public void testGetLastExistingNumber() {
        // Given Arrange
        Cell cell = new Cell(0,0);
        // When Act
        cell.removeNumber(1);
        cell.removeNumber(2);
        cell.removeNumber(3);
        cell.removeNumber(4);
        cell.removeNumber(6);
        cell.removeNumber(7);
        cell.removeNumber(8);
        cell.removeNumber(9);
        // Then Assert
        assertEquals(5, cell.getLastExistingNumber());
    }

    @Test
    public void testGetLastExistingNumberWhenNotExist() {
        // Given Arrange
        Cell cell = new Cell(0,0);
        // When Act
        cell.removeNumber(1);
        cell.removeNumber(2);
        cell.removeNumber(3);
        cell.removeNumber(4);
        cell.removeNumber(5);
        cell.removeNumber(6);
        cell.removeNumber(7);
        cell.removeNumber(8);
        cell.removeNumber(9);
        // Then Assert
        assertEquals(ConstantData.NOT_SET_VALUE, cell.getLastExistingNumber());
    }
}