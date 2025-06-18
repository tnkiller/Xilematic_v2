/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class RapPhim {
    private int maRap;
    private String tenRap;
    private int maCumRap;

    public RapPhim(int maRap, String tenRap, int maCumRap) {
        this.maRap = maRap;
        this.tenRap = tenRap;
        this.maCumRap = maCumRap;
    }

    public int getMaRap() {
        return maRap;
    }

    public void setMaRap(int maRap) {
        this.maRap = maRap;
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }

    public int getMaCumRap() {
        return maCumRap;
    }

    public void setMaCumRap(int maCumRap) {
        this.maCumRap = maCumRap;
    }

    @Override
    public String toString() {
        return "RapPhim{" + "maRap=" + maRap + ", tenRap=" + tenRap + ", maCumRap=" + maCumRap + '}';
    }

    
    
}

