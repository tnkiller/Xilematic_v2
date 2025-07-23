package model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Phim_TheLoai")
public class PhimTheLoai implements Serializable {

    @Id
    @Column(name = "ma_phim")
    private int maPhim;

    @Id
    @Column(name = "ma_the_loai")
    private int maTheLoai;

    // Quan hệ với bảng Phim
    @ManyToOne
    @JoinColumn(name = "ma_phim", insertable = false, updatable = false)
    private Movie phim;

    // Quan hệ với bảng TheLoai
    @ManyToOne
    @JoinColumn(name = "ma_the_loai", insertable = false, updatable = false)
    private TheLoai theLoai;

    public PhimTheLoai() {}

    public PhimTheLoai(int maPhim, int maTheLoai) {
        this.maPhim = maPhim;
        this.maTheLoai = maTheLoai;
    }

    // Getters and Setters

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

    public Movie getPhim() {
        return phim;
    }

    public void setPhim(Movie phim) {
        this.phim = phim;
    }

    public TheLoai getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(TheLoai theLoai) {
        this.theLoai = theLoai;
    }
}
