package model;

public class Showtime {

    private int ma_lich_chieu;
    private int ma_rap;
    private int ma_phim;
    private String ngay_gio_chieu;

    public Showtime() {
    }

    public Showtime(int ma_lich_chieu, int ma_rap, int ma_phim, String ngay_gio_chieu) {
        this.ma_lich_chieu = ma_lich_chieu;
        this.ma_rap = ma_rap;
        this.ma_phim = ma_phim;
        this.ngay_gio_chieu = ngay_gio_chieu;
    }

    public Showtime(int ma_rap, int ma_phim, String ngay_gio_chieu) {
        this.ma_rap = ma_rap;
        this.ma_phim = ma_phim;
        this.ngay_gio_chieu = ngay_gio_chieu;
    }

    public int getMa_lich_chieu() {
        return ma_lich_chieu;
    }

    public void setMa_lich_chieu(int ma_lich_chieu) {
        this.ma_lich_chieu = ma_lich_chieu;
    }

    public int getMa_rap() {
        return ma_rap;
    }

    public void setMa_rap(int ma_rap) {
        this.ma_rap = ma_rap;
    }

    public int getMa_phim() {
        return ma_phim;
    }

    public void setMa_phim(int ma_phim) {
        this.ma_phim = ma_phim;
    }

    public String getNgay_gio_chieu() {
        return ngay_gio_chieu;
    }

    public void setNgay_gio_chieu(String ngay_gio_chieu) {
        this.ngay_gio_chieu = ngay_gio_chieu;
    }

    @Override
    public String toString() {
        return "Showtime{" + "ma_lich_chieu=" + ma_lich_chieu + ", ma_rap=" + ma_rap + ", ma_phim=" + ma_phim + ", ngay_gio_chieu=" + ngay_gio_chieu + '}';
    }

}
