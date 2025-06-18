
package service;

import dao.BinhLuanDAO;
import java.util.List;
import model.BinhLuan;


public class CommentService implements ICommentService{
    BinhLuanDAO binhLuanDAO;
    public CommentService() {
        binhLuanDAO = new BinhLuanDAO();
    }

    @Override
    public boolean themBinhLuan(BinhLuan binhLuan) {
        return binhLuanDAO.themBinhLuan(binhLuan);
    }

    @Override
    public List<BinhLuan> layBinhLuanTheoPhim(int maPhim) {
        return  binhLuanDAO.layBinhLuanTheoPhim(maPhim);
    }

    @Override
    public boolean xoaBinhLuan(int maBinhLuan, int maNguoiDung) {
       return binhLuanDAO.xoaBinhLuan(maBinhLuan, maNguoiDung);
    }

    @Override
    public int demSoBinhLuan(int maPhim) {
      return binhLuanDAO.demSoBinhLuan(maPhim);
    }

    @Override
    public boolean capNhatBinhLuan(int maBinhLuan, String noiDungMoi, int maNguoiDung) {
       return binhLuanDAO.capNhatBinhLuan(maBinhLuan, noiDungMoi, maNguoiDung);
    }

    @Override
    public BinhLuan layChiTietBinhLuan(int maBinhLuan) {
            return binhLuanDAO.layChiTietBinhLuan(maBinhLuan);
    }

    @Override
    public List<BinhLuan> layBinhLuanCuaNguoiDung(int maNguoiDung) {
        return layBinhLuanCuaNguoiDung(maNguoiDung);
    }

    @Override
    public boolean kiemTraQuyenSuaBinhLuan(int maBinhLuan, int maNguoiDung) {
        return kiemTraQuyenSuaBinhLuan(maBinhLuan, maNguoiDung);
    }

    @Override
    public List<BinhLuan> layBinhLuanTheoTrang(int maPhim, int trang, int soPhanTuTrenTrang) {
       return binhLuanDAO.layBinhLuanTheoTrang(maPhim, trang, soPhanTuTrenTrang);
    }

    @Override
    public List<BinhLuan> timKiemBinhLuan(int maPhim, String tuKhoa) {
           return timKiemBinhLuan(maPhim, tuKhoa);
    }
    
    
}
