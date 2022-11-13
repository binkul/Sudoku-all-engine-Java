package com.binkul.sudoku.element;

import com.binkul.sudoku.data.ConstantData;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Row {
    private final List<Cell> cells;

    public Row(int row) {
        cells = IntStream.range(0, ConstantData.WIDTH)
                .mapToObj(i -> new Cell(row, i))
                .collect(Collectors.toList());
    }

    public Cell getCell(int column) {
        return cells.get(column);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("|");
        for (Cell cell : cells) {
            if (cell.getValue() == ConstantData.NOT_SET_VALUE)
                result.append("   |");
            else
                result.append(" ")
                        .append(cell.getValue())
                        .append(" |");
        }
        result.append("\n-------------------------------------");
        return result.toString();
    }
}
