package com.example.lab4back1.util;

public class HitValidator {
    public static boolean isHit(double x, double y, double r){
        return isTopLeft(x, y, r) || isTopRight(x, y, r) || isBottomLeft(x, y, r);
    }

    public static boolean isTopRight(double x, double y, double r){
        return x >= 0 && y >= 0 && (x * x + y * y <= r*r/4);
    }

    public static boolean isBottomLeft(double x, double y, double r){
        return x <= 0 && y <= 0 && x >= -r && y >= - x / 2 - r / 2;
    }

    public static boolean isTopLeft(double x, double y, double r){
        return x <= 0 && x >= -r && y >= 0 && y <= r / 2;
    }


}
