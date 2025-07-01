/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class HeThongRap {
    private int maHeThongRap;
    private String tenHeThongRap;
    private String logo;

    public HeThongRap(int maHeThongRap, String tenHeThongRap, String logo) {
        this.maHeThongRap = maHeThongRap;
        this.tenHeThongRap = tenHeThongRap;
        this.logo = logo;
    }

    public int getMaHeThongRap() {
        return maHeThongRap;
    }

    public void setMaHeThongRap(int maHeThongRap) {
        this.maHeThongRap = maHeThongRap;
    }

    public String getTenHeThongRap() {
        return tenHeThongRap;
    }

    public void setTenHeThongRap(String tenHeThongRap) {
        this.tenHeThongRap = tenHeThongRap;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "HeThongRap{" + "maHeThongRap=" + maHeThongRap + ", tenHeThongRap=" + tenHeThongRap + ", logo=" + logo + '}';
    }

    
}

