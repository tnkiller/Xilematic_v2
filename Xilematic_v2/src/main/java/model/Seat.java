package model;

public class Seat {

    private String ten_ghe;
    private String loai_ghe;
    private boolean da_dat;

    public Seat() {
    }

    public Seat(String ten_ghe, String loai_ghe, boolean da_dat) {
        this.ten_ghe = ten_ghe;
        this.loai_ghe = loai_ghe;
        this.da_dat = da_dat;
    }

    public String getTen_ghe() {
        return ten_ghe;
    }

    public void setTen_ghe(String ten_ghe) {
        this.ten_ghe = ten_ghe;
    }

    public String getLoai_ghe() {
        return loai_ghe;
    }

    public void setLoai_ghe(String loai_ghe) {
        this.loai_ghe = loai_ghe;
    }

    public boolean isDa_dat() {
        return da_dat;
    }

    public void setDa_dat(boolean da_dat) {
        this.da_dat = da_dat;
    }

    @Override
    public String toString() {
        return "Seat{" + "ten_ghe=" + ten_ghe + ", loai_ghe=" + loai_ghe + ", da_dat=" + da_dat + '}';
    }

}
