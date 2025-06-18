package model;

import java.time.LocalDate;

public class BinhLuan {

    private int maBinhLuan;
    private int maNguoiDung;
    private int maPhim;
    private String noiDung;
    private LocalDate ngayTao; // LocalDate for the creation date
    private boolean trangThai;
    private String tenNguoiDung;

    // Default constructor
    public BinhLuan() {}

    // Constructor with all fields
    public BinhLuan(int maBinhLuan, int maNguoiDung, int maPhim, String noiDung, LocalDate ngayTao, boolean trangThai) {
        this.maBinhLuan = maBinhLuan;
        this.maNguoiDung = maNguoiDung;
        this.maPhim = maPhim;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    // Getter and Setter methods

    public int getMaBinhLuan() {
        return maBinhLuan;
    }

    public void setMaBinhLuan(int maBinhLuan) {
        this.maBinhLuan = maBinhLuan;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public int getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }
    
    

    @Override
    public String toString() {
        return "BinhLuan{" +
                "maBinhLuan=" + maBinhLuan +
                ", maNguoiDung=" + maNguoiDung +
                ", maPhim=" + maPhim +
                ", noiDung='" + noiDung + '\'' +
                ", ngayTao=" + ngayTao +
                ", trangThai=" + trangThai +
                '}';
    }
}
