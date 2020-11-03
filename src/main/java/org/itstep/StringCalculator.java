package org.itstep;

import java.util.Arrays;

public class StringCalculator {
    public static int add(String str) {
        //проверка входящей строки на её наличие
        if (str == null || "".equals(str)) {
            return 0;
        }

        //разделение чисел от пользовательских разделителей
        String begin;
        String number;
        {
            int indexBegin = getStartIndexNumbers(str);
            begin = str.substring(0, indexBegin).trim();
            number = str.substring(indexBegin).trim();
        }

        //получиние разделителей
        String regex = getRedex(begin);
        //проверка на исключение
        if (regex == null){
            return 0;
        }

        //сплит чисел на массив
        String[] strNums = number.split(regex);

        //проверка массива на наличие посторонних элементов
        for (int i = 0; i < strNums.length; i++) {
            strNums[i] = strNums[i].trim();
            for (int j = 0; j < strNums[i].length(); j++) {
                if (!(strNums[i].charAt(j) >= '0' && strNums[i].charAt(j) <= '9')) {
                    System.out.println("throw SpliterFormatException");
                    return 0;
                }
            }
        }

        //подсчет суммы
        int sum = 0;
        for (String tmp : strNums) {
            if (Integer.parseInt(tmp) < 1001) {
                sum += Integer.parseInt(tmp);
            }
        }

        return sum;
    }

    private static int getStartIndexNumbers(String str) {
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return 0;
        }
        return str.indexOf("\n") + 1;
    }

    private static String getRedex(String str) {
        //разделители по умолчанию
        String regex = ",|\\n";

        //проверка на пользовательские разделители
        if (str.length() == 0) {
            return regex;
        }

        //первая проверка на коректность пользовательскиз разделителей
        String[] regexUser;
        if (!(str.length() > 2 && str.substring(0, 2).equals("//"))) {
            System.out.println("throw SpliterFormatException");
            return null;
        }

        //сплит пользовательских разделителей на массив
        str = str.substring(2).trim();
        regexUser = str.split("]");
        for (int i = 0; i < regexUser.length; i++) {
            regexUser[i] = regexUser[i].trim();
            if (regexUser[i].length() > 1) {
                regexUser[i] = regexUser[i].substring(1);
            }
        }

        //Екранирование
        for (int i = 0; i < regexUser.length; i++) {
            String tmp = "";
            for (int j = 0; j < regexUser[i].length(); j++) {
                if (regexUser[i].charAt(j) == '*') {
                    tmp += "\\*";
                } else if (regexUser[i].charAt(j) == '|') {
                    tmp += "\\|";
                } else {
                    tmp += regexUser[i].charAt(j);
                }
            }
            regexUser[i] = tmp;
        }

        //проверка разделителя на число
        for (int i = 0; i < regexUser.length; i++) {
            boolean check = true;
            for (int j = 0; j < regexUser[i].length(); j++) {
                int num = regexUser[i].charAt(j);
                if (!(num <= '9' && num >= '0')) {
                    check = false;
                }
            }
            if (check) {
                System.out.println("throw SpliterFormatException");
                return null;
            }
        }

        //сумирование разделителей пользовательских и по умолчанию
        for (int i = 0; i < regexUser.length; i++) {
            regex += "|" + regexUser[i];
        }

        return regex;
    }
}
