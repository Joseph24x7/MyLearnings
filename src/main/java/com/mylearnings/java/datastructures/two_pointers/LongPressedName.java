package com.mylearnings.java.datastructures.two_pointers;

public class LongPressedName {

    public boolean isLongPressedName(String name, String typed) {
        char[] nameArr = name.toCharArray();
        char[] typedArr = typed.toCharArray();
        int i = 0, j = 0;
        while (i < nameArr.length || j < typedArr.length) {

            if (i < nameArr.length && j < typedArr.length) {
                if (nameArr[i] == typedArr[j]) {
                    i++;
                    j++;
                } else {
                    if (i > 0 && typedArr[j] == nameArr[i - 1]) {
                        j++;
                    } else {
                        return false;
                    }
                }
            } else if (j < typedArr.length) {
                if (typedArr[j] != typedArr[j - 1]) {
                    return false;
                } else {
                    j++;
                }
            } else if (i < nameArr.length) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        LongPressedName lpn = new LongPressedName();
        String name = "alex";
        String typed = "aaleexa";
        boolean result = lpn.isLongPressedName(name, typed);
        System.out.println("Is Long Pressed Name: " + result);
    }

}
