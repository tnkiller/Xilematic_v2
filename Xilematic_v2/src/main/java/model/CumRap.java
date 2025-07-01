package model;

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



    public CumRap(String tenCumRap, String diaChi) {
        this.tenCumRap = tenCumRap;
        this.diaChi = diaChi;
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
