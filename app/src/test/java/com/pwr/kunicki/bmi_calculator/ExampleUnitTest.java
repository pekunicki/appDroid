package com.pwr.kunicki.bmi_calculator;

import com.pwr.kunicki.bmi_calculator.Interfaces.ICountBMI;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void converter_isCorrect() throws Exception{
        ICountBMI bmic = new CountBMIForUS();
        Assert.assertEquals(42.056877f, bmic.countBMI(300,5.9f));
    }

    @Test
    public void kgm_isCorrect() throws Exception{
        ICountBMI bmic = new CountBMIForEU();
        Assert.assertEquals(24.69136f, bmic.countBMI(80,180));
    }
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}