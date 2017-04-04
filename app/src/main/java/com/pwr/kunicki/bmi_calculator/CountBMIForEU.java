package com.pwr.kunicki.bmi_calculator;


import com.pwr.kunicki.bmi_calculator.Interfaces.ICountBMI;

public class CountBMIForEU implements ICountBMI {

    private static final float MAXIMUM_WEIGHT = 300f;
    private static final float MINIMUM_WEIGHT = 20f;
    private static final float MAXIMUM_HEIGHT = 300f;
    private static final float MINIMUM_HEIGHT = 40f;

    @Override
    public float countBMI(float weight, float height){
        if(isWeightValid(weight) && isHeightValid(height)) return (weight/((height/100)*(height/100)));
        else throw new IllegalArgumentException("Wrong data, more or less sleeping & eating!");
    }

    @Override
    public boolean isWeightValid(float weight) {
        return weight > MINIMUM_WEIGHT && weight < MAXIMUM_WEIGHT;
    }

    @Override
    public boolean isHeightValid(float height) {
        return height > MINIMUM_HEIGHT && height < MAXIMUM_HEIGHT;
    }

}
