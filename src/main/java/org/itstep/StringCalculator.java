package org.itstep;

public class StringCalculator {
    public static int add(String str) {
        if (str == null || "".equals(str)){return 0;}

        String[] strNums = str.split(",");
        int sum = 0;
        for (String tmp: strNums) {
            sum += Integer.parseInt(tmp);
        }
        return sum;
    }
}
