package org.itstep;

import org.junit.Assert;
import org.junit.Test;

public class StringCalculatorTest {

    @Test
    public void kata01(){
        Assert.assertEquals(0, StringCalculator.add(null));
        Assert.assertEquals(0, StringCalculator.add(""));
        Assert.assertEquals(1, StringCalculator.add("1"));
        Assert.assertEquals(3, StringCalculator.add("1,2"));
        Assert.assertEquals(4, StringCalculator.add("4,"));
    }
}
