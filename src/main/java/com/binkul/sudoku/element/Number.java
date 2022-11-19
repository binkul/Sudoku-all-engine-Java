package com.binkul.sudoku.element;

import java.util.Objects;

public class Number {
    private final int number;
    private boolean exist;
    private boolean checked;

    public Number(int number) {
        this.number = number;
        setExist();
    }

    public int getNumber() {
        return number;
    }

    public boolean exist() {
        return exist;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setExist() {
        this.exist = true;
    }

    public void unsetExist() {
        this.exist = false;
    }

    public void setChecked() {
        this.checked = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number1 = (Number) o;
        return number == number1.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
