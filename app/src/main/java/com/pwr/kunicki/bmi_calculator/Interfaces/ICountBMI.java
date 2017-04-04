package com.pwr.kunicki.bmi_calculator.Interfaces;

public interface ICountBMI {

    float countBMI(float weight, float height);
    boolean isWeightValid(float weight);
    boolean isHeightValid(float height);
}
