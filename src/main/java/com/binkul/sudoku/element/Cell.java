package com.binkul.sudoku.element;

import com.binkul.sudoku.data.ConstantData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private int value;
    private ValueType valueType;
    private final int section;
    private final int row;
    private final int column;
    private final List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
//    private final List<Number> numbers = new ArrayList<>(
//            List.of(
//                    new Number(1),
//                    new Number(2),
//                    new Number(3),
//                    new Number(4),
//                    new Number(5),
//                    new Number(6),
//                    new Number(7),
//                    new Number(8),
//                    new Number(9))
//    );

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        valueType = ValueType.ENTERED;
        value = ConstantData.NOT_SET_VALUE;
        section = ConstantData.SECTION * (row / ConstantData.SECTION) + (column / ConstantData.SECTION);
    }

    public int getValue() {
        return value;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public int getSection() {
        return section;
    }

    public int getColumn() { return column; }

    public int getRow() {
        return row;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public List<Integer> getExistingNumbers() {
        return numbers;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public void removeNumber(Integer number) {
        numbers.remove(number);
    }

    public int getNumbersSize() {
        return numbers.size();
    }

    public boolean isOnlyOneNumber() {
        return numbers.size() == 1;
    }

    public int getLastExistingNumber() {
        return numbers.stream().findFirst().orElse(ConstantData.NOT_SET_VALUE);
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
