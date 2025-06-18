package model;

public class Booking {

    private int ma_dat_ve;
    private int tai_khoan;
    private int ma_lich_chieu;
    private String ghe_da_dat;
    private long gia_ve;

    public Booking() {
    }

    public Booking(int ma_dat_ve, int tai_khoan, int ma_lich_chieu, String ghe_da_dat, long gia_ve) {
        this.ma_dat_ve = ma_dat_ve;
        this.tai_khoan = tai_khoan;
        this.ma_lich_chieu = ma_lich_chieu;
        this.ghe_da_dat = ghe_da_dat;
        this.gia_ve = gia_ve;
    }

    public Booking(int tai_khoan, int ma_lich_chieu, String ghe_da_dat, long gia_ve) {
        this.tai_khoan = tai_khoan;
        this.ma_lich_chieu = ma_lich_chieu;
        this.ghe_da_dat = ghe_da_dat;
        this.gia_ve = gia_ve;
    }

    public int getMa_dat_ve() {
        return ma_dat_ve;
    }

    public void setMa_dat_ve(int ma_dat_ve) {
        this.ma_dat_ve = ma_dat_ve;
    }

    public int getTai_khoan() {
        return tai_khoan;
    }

    public void setTai_khoan(int tai_khoan) {
        this.tai_khoan = tai_khoan;
    }

    public int getMa_lich_chieu() {
        return ma_lich_chieu;
    }

    public void setMa_lich_chieu(int ma_lich_chieu) {
        this.ma_lich_chieu = ma_lich_chieu;
    }

    public String getGhe_da_dat() {
        return ghe_da_dat;
    }

    public void setGhe_da_dat(String ghe_da_dat) {
        this.ghe_da_dat = ghe_da_dat;
    }

    public long getGia_ve() {
        return gia_ve;
    }

    public void setGia_ve(long gia_ve) {
        this.gia_ve = gia_ve;
    }

    @Override
    public String toString() {
        return "Booking{" + "ma_dat_ve=" + ma_dat_ve + ", tai_khoan=" + tai_khoan + ", ma_lich_chieu=" + ma_lich_chieu + ", ghe_da_dat=" + ghe_da_dat + ", gia_ve=" + gia_ve + '}';
    }

}
