package com.example.bmicalc;

public class BmiOption {

    double value;
    String label;
    String color;

    public BmiOption(double value, String label) {
        this.value = value;
        this.label = label;
        this.color = color;
    }

    public double getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
