package org.itstep;

import java.util.ArrayList;
import java.util.Arrays;

public class StringCalculator {
    public static int add(CharSequence ... s) {
        //засекаем время
        long time = System.nanoTime();

        //проверка входящей строки на её наличие
        if (s == null || s[0].equals("")) {
            return 0;
        }
        int sum = 0;
        for (int k = 0; k < s.length; k++) {
            String str = s[k].toString();

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
            if (regex == null) {
                return 0;
            }

            //сплит чисел на массив
            String[] strNums = number.split(regex);

            //проверка массива на наличие посторонних элементов
            ArrayList<String> negativeValues = new ArrayList<>();
            for (int i = 0; i < strNums.length; i++) {
                strNums[i] = strNums[i].trim();
                for (int j = 0; j < strNums[i].length(); j++) {
                    char c = strNums[i].charAt(j);
                    if (!(c >= '0' && c <= '9')) {
                        //подсчет отрицательных чисел
                        if (c == '–'){
                            if (checkNegativeValues(strNums[i])){
                                negativeValues.add(strNums[i]);
                                break;
                            }
                        }
                        //возведение в степень
                        if (c == '^' && j > 0){
                            if (j == strNums[i].length() - 1) {
                                strNums[i] = String.valueOf((int) Math.pow(Integer.parseInt(strNums[i].substring(0, j)), 2));
                                break;
                            }
                            else {
                                String[] tmp = strNums[i].split("\\^");
                                strNums[i] = String.valueOf((int)Math.pow(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1])));
                                break;
                            }
                        }
                        System.out.println("throw SpliterFormatException");
                        return 0;
                    }
                }
            }

            //проверка и вывод отрицательных чисел
            if (negativeValues.size() != 0){
                for (int i = 0; i < negativeValues.size() - 1; i++) {
                    System.out.print(negativeValues.get(i) + " ");
                }
                System.out.println(negativeValues.get(negativeValues.size() - 1) + "\n" +
                        "throw NumberNegativException");
                return 0;
            }

            //подсчет суммы
            for (String tmp : strNums) {
                if (Integer.parseInt(tmp) < 1001) {
                    sum += Integer.parseInt(tmp);
                }
            }
        }

        //проверяем время
        time = System.nanoTime() - time;
        if(time/1_000_000.0 > 30){
            System.out.println("throw TimeException");
            return 0;
        }

        return sum;
    }

    private static int getStartIndexNumbers(String str) {
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return 0;
        }
        if (str.length() > 2 && str.substring(0,2).equals("//")) {
            return str.indexOf("\n") + 1;
        }
        return 0;
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

        str = str.substring(2).trim();
        //одинарные разделители
        if (str.length() == 1){
            regexUser = new String[]{str};
        }else {
            //сплит пользовательских разделителей на массив
            regexUser = str.split("]");
            for (int i = 0; i < regexUser.length; i++) {
                regexUser[i] = regexUser[i].trim();
                if (regexUser[i].length() > 0) {
                    regexUser[i] = regexUser[i].substring(1);
                }
            }
        }

        //проверка разделителей на пустую строку
        String[] newArr = new String[0];
        for (int i = 0; i < regexUser.length; i++) {
            if (regexUser[i].length() != 0){
                newArr = Arrays.copyOf(newArr, newArr.length + 1);
                newArr[newArr.length - 1] = regexUser[i];
            }
        }
        regexUser = newArr;

        //Екранирование
        for (int i = 0; i < regexUser.length; i++) {
            String tmp = "";
            for (int j = 0; j < regexUser[i].length(); j++) {
                if (regexUser[i].charAt(j) == '*') {
                    tmp += "\\*";
                } else if (regexUser[i].charAt(j) == '|') {
                    tmp += "\\|";
                } else if (regexUser[i].charAt(j) == '$') {
                    tmp += "\\$";
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

    private static boolean checkNegativeValues(String str){
        if (str.charAt(0) == '–' && str.charAt(1) == ' '){
            String sub = str.substring(2);
            for (int i = 0; i < sub.length(); i++) {
                char c = sub.charAt(i);
                if (!(c >= '0' && c <= '9')) {
                    return false;
                }
            }
        }
        return true;
    }
}
