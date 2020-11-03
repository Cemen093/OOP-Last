package org.itstep;

public class StringCalculator {
    public static int add(String str) {
        if (str == null || "".equals(str)){return 0;}

        String regex = ",|\\n";
        String regexUser = "";
        if (str.length() > 2 && str.substring(0,2).equals("//")){
            int index = str.indexOf("\n");
            regexUser = str.substring(2, index).trim();
            regex += "|" + regexUser;
            str = str.substring(index + 1);

            if (regexUser.equals("*")){
                regex = regex.substring(0, regex.length() - 2) + "[^0123456789]";
            }

            {
                boolean check = false;
                for (int i = 0; i < regexUser.length(); i++) {
                    int number = (int) regexUser.charAt(i);
                    if (!(number <= (int) '9' && number >= (int) '0')) {
                        check = true;
                    }
                }
                if (!check){
                    System.out.print("throw SpliterFormatException");
                    return 0;
                }
            }

            if (regexUser.length() == 1 && (int)regexUser.charAt(0) <= (int)'9' &&
                    (int)regexUser.charAt(0) >= (int)'1'){
                System.out.print("throw SpliterFormatException");
                return 0;
            }
        }

        String[] strNums = str.split(regex);

        for (int i = 0; i < strNums.length; i++) {
            strNums[i] = strNums[i].trim();
            for (int j = 0; j < strNums[i].length(); j++) {
                if (!(strNums[i].charAt(j) >= '0' && strNums[i].charAt(j) <= '9')){
                    System.out.print("throw SpliterFormatException");
                    return 0;
                }
            }
        }

        int sum = 0;
        for (String tmp: strNums) {
            if (Integer.parseInt(tmp) < 1001) {
                sum += Integer.parseInt(tmp);
            }
        }
        return sum;
    }
}
