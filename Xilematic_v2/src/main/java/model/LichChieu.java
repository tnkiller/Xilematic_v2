
package model;

import java.time.LocalDateTime;

public class LichChieu {
    private int maLichChieu;
    private int maRap;
    private int maPhim;
    private LocalDateTime ngayGioChieu;

    public LichChieu(int maLichChieu, int maRap, int maPhim, LocalDateTime ngayGioChieu) {
        this.maLichChieu = maLichChieu;
        this.maRap = maRap;
        this.maPhim = maPhim;
        this.ngayGioChieu = ngayGioChieu;
    }

    public LichChieu() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getMaLichChieu() {
        return maLichChieu;
    }

    public void setMaLichChieu(int maLichChieu) {
        this.maLichChieu = maLichChieu;
    }

    public int getMaRap() {
        return maRap;
    }

    public void setMaRap(int maRap) {
        this.maRap = maRap;
    }

    public int getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }

    public LocalDateTime getNgayGioChieu() {
        return ngayGioChieu;
    }

    public void setNgayGioChieu(LocalDateTime ngayGioChieu) {
        this.ngayGioChieu = ngayGioChieu;
    }

    @Override
    public String toString() {
        return "LichChieu{" + "maLichChieu=" + maLichChieu + ", maRap=" + maRap + ", maPhim=" + maPhim + ", ngayGioChieu=" + ngayGioChieu + '}';
    }
}

