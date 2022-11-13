package com.binkul.sudoku.element;

import com.binkul.sudoku.data.ConstantData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private int value;
    private final int section;
    private final int row;
    private final int column;
    private final List<Number> numbers = new ArrayList<>(
            List.of(
                    new Number(1),
                    new Number(2),
                    new Number(3),
                    new Number(4),
                    new Number(5),
                    new Number(6),
                    new Number(7),
                    new Number(8),
                    new Number(9))
    );

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        value = ConstantData.NOT_SET_VALUE;
        section = ConstantData.SECTION * (row / ConstantData.SECTION) + (column / ConstantData.SECTION);
    }

    public int getValue() {
        return value;
    }

    public List<Number> getNumbers() {
        return numbers;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSection() {
        return section;
    }

    public void removeNumber(int number) {
        numbers.stream()
                .filter(n -> n.getNumber() == number)
                .forEach(Number::unsetExist);
    }

    public boolean isOnlyOneNumber() {
        return numbers.stream()
                .filter(Number::exist)
                .mapToInt(Number::getNumber)
                .count() == 1;
    }

    public int getLastExistingNumber() {
        return numbers.stream()
                .filter(Number::exist)
                .mapToInt(Number::getNumber)
                .findFirst().orElse(ConstantData.NOT_SET_VALUE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return value == cell.value && section == cell.section && row == cell.row && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, section, row, column);
    }
}
