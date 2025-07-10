package model;

public class PhimTheLoai {
    private int maPhim;
    private int maTheLoai;

    public PhimTheLoai() {
    }

    public PhimTheLoai(int maPhim, int maTheLoai) {
        this.maPhim = maPhim;
        this.maTheLoai = maTheLoai;
    }

    public int getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    @Override
    public String toString() {
        return "PhimTheLoai{" +
                "maPhim=" + maPhim +
                ", maTheLoai=" + maTheLoai +
                '}';
    }
}
