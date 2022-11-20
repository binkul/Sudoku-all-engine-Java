package com.binkul.sudoku.board;

import com.binkul.sudoku.data.ConstantData;
import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.Number;
import com.binkul.sudoku.element.Row;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sudoku {
    private final List<Row> rows;

    private Sudoku() {
        rows = IntStream.range(0, ConstantData.WIDTH)
                .mapToObj(Row::new)
                .collect(Collectors.toList());
    }

    public Cell getCell(int row, int column) {
        return rows.get(row).getCell(column);
    }

    public boolean isAllFilled() {
        return rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream)
                .noneMatch(i -> i.getValue() == ConstantData.NOT_SET_VALUE);
    }

    public boolean isAllFilledCorrect() {

        if (IntStream.range(0, ConstantData.WIDTH)
                .mapToObj(i -> isDuplicates(getRowValues(i)))
                .anyMatch(i -> i)) return false;
        if (IntStream.range(0, ConstantData.WIDTH)
                .mapToObj(i -> isDuplicates(getColumnValues(i)))
                .anyMatch(i -> i)) return false;
        return IntStream.range(0, ConstantData.WIDTH)
                .mapToObj(i -> isDuplicates(getSectionValues(i)))
                .noneMatch(i -> i);

//        for (int i = 0; i < ConstantData.WIDTH; i++) {
//            if (isDuplicates(getRowValues(i))) return false;
//        }
//        for (int i = 0; i < ConstantData.WIDTH; i++) {
//            if (isDuplicates(getColumnValues(i))) return false;
//        }
//        for (int i = 0; i < ConstantData.WIDTH; i++) {
//            if (isDuplicates(getSectionValues(i))) return false;
//        }
    }

    private boolean isDuplicates(List<Integer> inputs) {
        return inputs.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .anyMatch(v -> v.getValue() > 1);
    }

    public List<Integer> getRowValues(int row) {
        return rows.get(row).getCells().stream()
                .map(Cell::getValue)
                .filter(i -> i != ConstantData.NOT_SET_VALUE)
                .collect(Collectors.toList());
    }

    public List<Integer> getColumnValues(int column) {
        return rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream)
                .filter(i -> i.getColumn() == column)
                .map(Cell::getValue)
                .filter(i -> i != ConstantData.NOT_SET_VALUE)
                .collect(Collectors.toList());
    }

    public List<Integer> getSectionValues(int section) {
        return rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream)
                .filter(i -> i.getSection() == section)
                .map(Cell::getValue)
                .filter(i -> i != ConstantData.NOT_SET_VALUE)
                .collect(Collectors.toList());
    }

    public Set<Integer> getRowColSecValues(Cell cell) {
        int row = cell.getRow();
        int column = cell.getColumn();
        int section = cell.getSection();
        return rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream)
                .filter(i -> !i.equals(cell))
                .filter(i -> i.getRow() == row || i.getColumn() == column || i.getSection() == section)
                .map(Cell::getValue)
                .filter(i -> i != ConstantData.NOT_SET_VALUE)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getExistingRowNumbers(int row, Cell actualCell) {
        return rows.get(row).getCells().stream()
                .filter(i -> !i.equals(actualCell))
                .map(Cell::getNumbers)
                .flatMap(Collection::stream)
                .filter(Number::exist)
                .map(Number::getNumber)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getExistingColumnNumbers(int column, Cell actualCell) {
        return rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream)
                .filter(i -> !i.equals(actualCell))
                .filter(i -> i.getColumn() == column)
                .map(Cell::getNumbers)
                .flatMap(Collection::stream)
                .filter(Number::exist)
                .map(Number::getNumber)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getExistingSectionNumbers(int section, Cell actualCell) {
        return rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream)
                .filter(i -> !i.equals(actualCell))
                .filter(i -> i.getSection() == section)
                .map(Cell::getNumbers)
                .flatMap(Collection::stream)
                .filter(Number::exist)
                .map(Number::getNumber)
                .collect(Collectors.toSet());
    }

    public Sudoku deepCopy() {
        Sudoku sudokuCopy = new Sudoku();

        List<Cell> cells = rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream).toList();

        for (Cell cell : cells) {
            Cell copyCell = sudokuCopy.getCell(cell.getRow(), cell.getColumn());
            copyCell.setValue(cell.getValue());
            copyCell.setValueType(cell.getValueType());

            for (Number number : cell.getNumbers()) {
                int index = number.getNumber();
                Number copyNumber = copyCell.getNumbers().get(index - 1);
                if (!number.exist()) copyNumber.unsetExist();
                if (number.isChecked()) copyNumber.setChecked();
            }
        }

        return sudokuCopy;
    }

    public static class SudokuBuilder {
        private final List<Integer> rows = new ArrayList<>();
        private final List<Integer> columns = new ArrayList<>();
        private final List<Integer> values = new ArrayList<>();

        public SudokuBuilder setCell(int row, int column, int value) {
            if (row < 1 || row > ConstantData.WIDTH) throw new IllegalArgumentException("Row must be between 1-9");
            if (column < 1 || column > ConstantData.WIDTH) throw new IllegalArgumentException("Column must be between 1-9");
            if (value < 1 || value > ConstantData.WIDTH) throw new IllegalArgumentException("Value must be between 1-9");
            rows.add(row - 1);
            columns.add(column - 1);
            values.add(value);

            return this;
        }

        public Sudoku build() {
            Sudoku sudoku = new Sudoku();

            IntStream.range(0, rows.size())
                    .forEach(i -> sudoku.getCell(rows.get(i), columns.get(i)).setValue(values.get(i)));

            return sudoku;
        }
    }

    @Override
    public String toString() {
        String startFrame = "-------------------------------------\n";
        String endFrame = "\n";
        return rows.stream()
                .map(Row::toString)
                .collect(Collectors.joining("\n", startFrame, endFrame));
    }
}
