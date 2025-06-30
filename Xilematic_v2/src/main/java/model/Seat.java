package model;

public class Seat {

    private int ma_ghe;
    private String ten_ghe;
    private String loai_ghe;
    private boolean da_dat;
    private String trang_thai;

    public Seat() {
    }

    public Seat(int ma_ghe, String ten_ghe, String loai_ghe, boolean da_dat, String trang_thai) {
        this.ma_ghe = ma_ghe;
        this.ten_ghe = ten_ghe;
        this.loai_ghe = loai_ghe;
        this.da_dat = da_dat;
        this.trang_thai = trang_thai;
    }

    public Seat(String ten_ghe, String loai_ghe, boolean da_dat, String trang_thai) {
        this.ten_ghe = ten_ghe;
        this.loai_ghe = loai_ghe;
        this.da_dat = da_dat;
        this.trang_thai = trang_thai;
    }

    public String getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        this.trang_thai = trang_thai;
    }

    public int getMa_ghe() {
        return ma_ghe;
    }

    public void setMa_ghe(int ma_ghe) {
        this.ma_ghe = ma_ghe;
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
        return "Seat{" + "ma_ghe=" + ma_ghe + ", ten_ghe=" + ten_ghe + ", loai_ghe=" + loai_ghe + ", da_dat=" + da_dat + ", trang_thai=" + trang_thai + '}';
    }

}
