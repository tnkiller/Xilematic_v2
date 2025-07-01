/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Favorite {

    private int id;
    private int ma_nguoi_dung;
    private int ma_phim;

    public Favorite() {
    }

    public Favorite(int id, int ma_nguoi_dung, int ma_phim) {
        this.id = id;
        this.ma_nguoi_dung = ma_nguoi_dung;
        this.ma_phim = ma_phim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMa_nguoi_dung() {
        return ma_nguoi_dung;
    }

    public void setMa_nguoi_dung(int ma_nguoi_dung) {
        this.ma_nguoi_dung = ma_nguoi_dung;
    }

    public int getMa_phim() {
        return ma_phim;
    }

    public void setMa_phim(int ma_phim) {
        this.ma_phim = ma_phim;
    }

    @Override
    public String toString() {
        return "Favorite{" + "id=" + id + ", ma_nguoi_dung=" + ma_nguoi_dung + ", ma_phim=" + ma_phim + '}';
    }

}
