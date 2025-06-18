/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class CumRap {
    private int maCumRap;
    private String tenCumRap;
    private String diaChi;
    private int maHeThongRap;

    public CumRap(int maCumRap, String tenCumRap, String diaChi, int maHeThongRap) {
        this.maCumRap = maCumRap;
        this.tenCumRap = tenCumRap;
        this.diaChi = diaChi;
        this.maHeThongRap = maHeThongRap;
    }

    public int getMaCumRap() {
        return maCumRap;
    }

    public void setMaCumRap(int maCumRap) {
        this.maCumRap = maCumRap;
    }

    public String getTenCumRap() {
        return tenCumRap;
    }

    public void setTenCumRap(String tenCumRap) {
        this.tenCumRap = tenCumRap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getMaHeThongRap() {
        return maHeThongRap;
    }

    public void setMaHeThongRap(int maHeThongRap) {
        this.maHeThongRap = maHeThongRap;
    }

    @Override
    public String toString() {
        return "CumRap{" + "maCumRap=" + maCumRap + ", tenCumRap=" + tenCumRap + ", diaChi=" + diaChi + ", maHeThongRap=" + maHeThongRap + '}';
    }

   
}

