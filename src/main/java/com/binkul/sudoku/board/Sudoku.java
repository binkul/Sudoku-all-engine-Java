package com.binkul.sudoku.board;

import com.binkul.sudoku.data.ConstantData;
import com.binkul.sudoku.element.Cell;
import com.binkul.sudoku.element.Number;
import com.binkul.sudoku.element.Row;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public List<Cell> getCells() {
        return rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public boolean isAllFilled() {
        return rows.stream()
                .map(Row::getCells)
                .flatMap(Collection::stream)
                .noneMatch(i -> i.getValue() == ConstantData.NOT_SET_VALUE);
    }

    public boolean isAllFilledCorrect() {

        if (IntStream.range(0, ConstantData.WIDTH)
                .mapToObj(i -> isDuplicates(getValues(cell -> cell.getRow() == i)))
                .anyMatch(i -> i)) return false;
        if (IntStream.range(0, ConstantData.WIDTH)
                .mapToObj(i -> isDuplicates(getValues(cell -> cell.getColumn() == i)))
                .anyMatch(i -> i)) return false;
        return IntStream.range(0, ConstantData.WIDTH)
                .mapToObj(i -> isDuplicates(getValues(cell -> cell.getSection() == i)))
                .noneMatch(i -> i);
    }

    private boolean isDuplicates(List<Integer> inputs) {
        return inputs.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .anyMatch(v -> v.getValue() > 1);
    }

    public List<Integer> getValues(Predicate<Cell> position) {
        return getCells().stream()
                .filter(position)
                .map(Cell::getValue)
                .filter(i -> i != ConstantData.NOT_SET_VALUE)
                .collect(Collectors.toList());
    }

    public Set<Integer> getRowColSecValues(Cell cell) {
        List<Integer> rowValues = getValues(i -> i.getRow() == cell.getRow()); // getRowValues(cell.getRow());
        List<Integer> columnValues = getValues(i -> i.getColumn() == cell.getColumn()); // getColumnValues(cell.getColumn());
        List<Integer> sectionValues = getValues(i -> i.getSection() == cell.getSection()); // getSectionValues(cell.getSection());
        Set<Integer> values = new HashSet<>();
        values.addAll(rowValues);
        values.addAll(columnValues);
        values.addAll(sectionValues);

        return values;
    }

    public Set<Integer> getExistingNumbers(Predicate<Cell> position, Cell actualCell) {
        return getCells().stream()
                .filter(i -> !i.equals(actualCell))
                .filter(position)
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
