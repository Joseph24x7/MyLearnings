package com.mylearnings.java.datastructures.stringandintegers;

public class ReverseAStringWords {

    public static void main(String[] args) {

        String str = "Hello World! How are you?";

        String[] arr = str.split(" ");
        str = "";
        for (int i = arr.length - 1; i >= 0; i--) {
            if (i == 0)
                str = str + arr[i];
            else
                str = str + arr[i] + " ";
        }

        System.out.println(str);
    }

}
