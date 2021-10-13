package com.company;

public class Matrix {
    public int ZP,ZN,FP,FN;
    public String Z,F;

    public Matrix(String f) {
        this.ZP = 0;
        this.ZN = 0;
        this.FP = 0;
        this.FN = 0;
        Z = "";
        F = f;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "ZP=" + ZP +
                ", ZN=" + ZN +
                ", FP=" + FP +
                ", FN=" + FN +
                ", Z='" + Z + '\'' +
                ", F='" + F + '\'' +
                '}';
    }
}
