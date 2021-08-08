package com.voilaf;

public class Hello {

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int addResult = a + b;
        int subResult = b - a;
        int mulResult = a * b;
        int divReslt = b / 2;

        if (addResult > 0) {
            System.out.println("addResult is gt 0");
        } else {
            System.out.println("addResult is lt 0");
        }

        for (int i=0; i<3; i++) {
            System.out.println(subResult);
        }
    }
}
