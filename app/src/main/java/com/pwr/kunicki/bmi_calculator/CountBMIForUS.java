package com.pwr.kunicki.bmi_calculator;

import com.pwr.kunicki.bmi_calculator.Interfaces.ICountBMI;

public class CountBMIForUS implements ICountBMI {

    private static final float MAXIMUM_WEIGHT = 600f;
    private static final float MINIMUM_WEIGHT = 35f;
    private static final float MAXIMUM_HEIGHT = 9.5f;
    private static final float MINIMUM_HEIGHT = 1.4f;

    @Override
    public float countBMI(float weight, float height){
        if(isWeightValid(weight) && isHeightValid(height)) return (weight*4.88f)/(height*height);
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
