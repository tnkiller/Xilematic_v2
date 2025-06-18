package dao;

import model.BinhLuan;
import java.util.List;

public interface IBinhLuanDAO {
    /**
     * Thêm một bình luận mới vào hệ thống
     * 
     * @param binhLuan Đối tượng bình luận cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    boolean themBinhLuan(BinhLuan binhLuan);

    /**
     * Lấy danh sách bình luận theo mã phim
     * 
     * @param maPhim Mã phim cần lấy bình luận
     * @return Danh sách các bình luận của phim
     */
    List<BinhLuan> layBinhLuanTheoPhim(int maPhim);

    /**
     * Xóa bình luận (soft delete)
     * 
     * @param maBinhLuan Mã bình luận cần xóa
     * @param maNguoiDung Mã người dùng thực hiện xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    boolean xoaBinhLuan(int maBinhLuan, int maNguoiDung);

    /**
     * Đếm số lượng bình luận của một phim
     * 
     * @param maPhim Mã phim cần đếm số lượng bình luận
     * @return Số lượng bình luận
     */
    int demSoBinhLuan(int maPhim);

    /**
     * Cập nhật nội dung bình luận
     * 
     * @param maBinhLuan Mã bình luận cần cập nhật
     * @param noiDungMoi Nội dung mới của bình luận
     * @param maNguoiDung Mã người dùng thực hiện cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    boolean capNhatBinhLuan(int maBinhLuan, String noiDungMoi, int maNguoiDung);

    /**
     * Lấy chi tiết một bình luận theo mã bình luận
     * 
     * @param maBinhLuan Mã bình luận cần lấy
     * @return Đối tượng BinhLuan, null nếu không tìm thấy
     */
    BinhLuan layChiTietBinhLuan(int maBinhLuan);

    /**
     * Lấy danh sách bình luận của một người dùng
     * 
     * @param maNguoiDung Mã người dùng
     * @return Danh sách bình luận của người dùng
     */
    List<BinhLuan> layBinhLuanCuaNguoiDung(int maNguoiDung);

    /**
     * Kiểm tra quyền sửa/xóa bình luận
     * 
     * @param maBinhLuan Mã bình luận
     * @param maNguoiDung Mã người dùng
     * @return true nếu người dùng có quyền, false nếu không
     */
    boolean kiemTraQuyenSuaBinhLuan(int maBinhLuan, int maNguoiDung);

    /**
     * Lấy danh sách bình luận với phân trang
     * 
     * @param maPhim Mã phim
     * @param trang Trang hiện tại
     * @param soPhanTuTrenTrang Số phần tử trên mỗi trang
     * @return Danh sách bình luận theo trang
     */
    List<BinhLuan> layBinhLuanTheoTrang(int maPhim, int trang, int soPhanTuTrenTrang);

    /**
     * Tìm kiếm bình luận theo từ khóa
     * 
     * @param maPhim Mã phim
     * @param tuKhoa Từ khóa tìm kiếm
     * @return Danh sách bình luận phù hợp
     */
    List<BinhLuan> timKiemBinhLuan(int maPhim, String tuKhoa);
}
