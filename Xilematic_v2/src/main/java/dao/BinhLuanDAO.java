package dao;

import model.BinhLuan;
import context.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BinhLuanDAO implements IBinhLuanDAO{
    // Thêm bình luận mới
    public boolean themBinhLuan(BinhLuan binhLuan) {
        String sql = "INSERT INTO BinhLuan (ma_nguoi_dung, ma_phim, noi_dung) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, binhLuan.getMaNguoiDung());
            pstmt.setInt(2, binhLuan.getMaPhim());
            pstmt.setString(3, binhLuan.getNoiDung());
//            pstmt.setDate(4, Date.valueOf(LocalDate.now())); // Sử dụng ngày hiện tại
//            pstmt.setBoolean(5, true); // Mặc định trạng thái là true
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        binhLuan.setMaBinhLuan(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm bình luận: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    // Lấy danh sách bình luận theo mã phim
    public List<BinhLuan> layBinhLuanTheoPhim(int maPhim) {
        List<BinhLuan> danhSachBinhLuan = new ArrayList<>();
        String sql = "SELECT bl.*, nd.ten_tai_khoan " +
                     "FROM BinhLuan bl " +
                     "JOIN NguoiDung nd ON bl.ma_nguoi_dung = nd.ma_nguoi_dung " +
                     "WHERE bl.ma_phim = ? AND bl.trang_thai = 1 " +
                     "ORDER BY bl.ngay_tao DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maPhim);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BinhLuan binhLuan = new BinhLuan();
                    binhLuan.setMaBinhLuan(rs.getInt("ma_binh_luan"));
                    binhLuan.setMaNguoiDung(rs.getInt("ma_nguoi_dung"));
                    binhLuan.setMaPhim(rs.getInt("ma_phim"));
                    binhLuan.setNoiDung(rs.getString("noi_dung"));
                    binhLuan.setNgayTao(rs.getDate("ngay_tao").toLocalDate());
                    binhLuan.setTrangThai(rs.getBoolean("trang_thai"));
                    
                   
                    danhSachBinhLuan.add(binhLuan);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return danhSachBinhLuan;
    }

    // Xóa bình luận (soft delete)
    public boolean xoaBinhLuan(int maBinhLuan, int maNguoiDung) {
        String sql = "UPDATE BinhLuan SET trang_thai = 0 WHERE ma_binh_luan = ? AND ma_nguoi_dung = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maBinhLuan);
            pstmt.setInt(2, maNguoiDung);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    // Đếm số lượng bình luận cho một phim
    public int demSoBinhLuan(int maPhim) {
        String sql = "SELECT COUNT(*) AS soLuongBinhLuan FROM BinhLuan WHERE ma_phim = ? AND trang_thai = 1";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maPhim);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("soLuongBinhLuan");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    // Cập nhật bình luận
    public boolean capNhatBinhLuan(int maBinhLuan, String noiDungMoi, int maNguoiDung) {
        String sql = "UPDATE BinhLuan SET noi_dung = ? WHERE ma_binh_luan = ? AND ma_nguoi_dung = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, noiDungMoi);
            pstmt.setInt(2, maBinhLuan);
            pstmt.setInt(3, maNguoiDung);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

   @Override
public BinhLuan layChiTietBinhLuan(int maBinhLuan) {
    String sql = "SELECT bl.*, nd.ten_tai_khoan, p.ten_phim " +
                 "FROM BinhLuan bl " +
                 "JOIN NguoiDung nd ON bl.ma_nguoi_dung = nd.ma_nguoi_dung " +
                 "JOIN Phim p ON bl.ma_phim = p.ma_phim " +
                 "WHERE bl.ma_binh_luan = ? AND bl.trang_thai = 1";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, maBinhLuan);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                
                 BinhLuan binhLuan = new BinhLuan();
                    binhLuan.setMaBinhLuan(rs.getInt("ma_binh_luan"));
                    binhLuan.setMaNguoiDung(rs.getInt("ma_nguoi_dung"));
                    binhLuan.setMaPhim(rs.getInt("ma_phim"));
                    binhLuan.setNoiDung(rs.getString("noi_dung"));
                    binhLuan.setNgayTao(rs.getDate("ngay_tao").toLocalDate());
                    binhLuan.setTrangThai(rs.getBoolean("trang_thai"));
                    
                
                
                return binhLuan;
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return null;
}

@Override
public List<BinhLuan> layBinhLuanCuaNguoiDung(int maNguoiDung) {
    List<BinhLuan> danhSachBinhLuan = new ArrayList<>();
    String sql = "SELECT bl.*, p.ten_phim " +
                 "FROM BinhLuan bl " +
                 "JOIN Phim p ON bl.ma_phim = p.ma_phim " +
                 "WHERE bl.ma_nguoi_dung = ? AND bl.trang_thai = 1 " +
                 "ORDER BY bl.ngay_tao DESC";
    
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, maNguoiDung);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                 BinhLuan binhLuan = new BinhLuan();
                    binhLuan.setMaBinhLuan(rs.getInt("ma_binh_luan"));
                    binhLuan.setMaNguoiDung(rs.getInt("ma_nguoi_dung"));
                    binhLuan.setMaPhim(rs.getInt("ma_phim"));
                    binhLuan.setNoiDung(rs.getString("noi_dung"));
                    binhLuan.setNgayTao(rs.getDate("ngay_tao").toLocalDate());
                    binhLuan.setTrangThai(rs.getBoolean("trang_thai"));
                    
                
              
                danhSachBinhLuan.add(binhLuan);
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return danhSachBinhLuan;
}

@Override
public boolean kiemTraQuyenSuaBinhLuan(int maBinhLuan, int maNguoiDung) {
    String sql = "SELECT COUNT(*) AS soLuong " +
                 "FROM BinhLuan " +
                 "WHERE ma_binh_luan = ? AND ma_nguoi_dung = ? AND trang_thai = 1";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, maBinhLuan);
        pstmt.setInt(2, maNguoiDung);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("soLuong") > 0;
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return false;
}

@Override
public List<BinhLuan> layBinhLuanTheoTrang(int maPhim, int trang, int soPhanTuTrenTrang) {
    List<BinhLuan> danhSachBinhLuan = new ArrayList<>();
    String sql = "SELECT bl.*, nd.ten_tai_khoan " +
                 "FROM BinhLuan bl " +
                 "JOIN NguoiDung nd ON bl.ma_nguoi_dung = nd.ma_nguoi_dung " +
                 "WHERE bl.ma_phim = ? AND bl.trang_thai = 1 " +
                 "ORDER BY bl.ngay_tao DESC " +
                 "LIMIT ? OFFSET ?";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, maPhim);
        pstmt.setInt(2, soPhanTuTrenTrang);
        pstmt.setInt(3, (trang - 1) * soPhanTuTrenTrang);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                 BinhLuan binhLuan = new BinhLuan();
                    binhLuan.setMaBinhLuan(rs.getInt("ma_binh_luan"));
                    binhLuan.setMaNguoiDung(rs.getInt("ma_nguoi_dung"));
                    binhLuan.setMaPhim(rs.getInt("ma_phim"));
                    binhLuan.setNoiDung(rs.getString("noi_dung"));
                    binhLuan.setNgayTao(rs.getDate("ngay_tao").toLocalDate());
                    binhLuan.setTrangThai(rs.getBoolean("trang_thai"));
                    
                
                danhSachBinhLuan.add(binhLuan);
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return danhSachBinhLuan;
}

@Override
public List<BinhLuan> timKiemBinhLuan(int maPhim, String tuKhoa) {
    List<BinhLuan> danhSachBinhLuan = new ArrayList<>();
    String sql = "SELECT bl.*, nd.ten_tai_khoan " +
                 "FROM BinhLuan bl " +
                 "JOIN NguoiDung nd ON bl.ma_nguoi_dung = nd.ma_nguoi_dung " +
                 "WHERE bl.ma_phim= ? AND bl.trang_thai = 1 " +
                 "AND LOWER(bl.noi_dung) LIKE LOWER(?) " +
                 "ORDER BY bl.ngay_tao DESC";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, maPhim);
        pstmt.setString(2, "%" + tuKhoa + "%");
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                 BinhLuan binhLuan = new BinhLuan();
                    binhLuan.setMaBinhLuan(rs.getInt("ma_binh_luan"));
                    binhLuan.setMaNguoiDung(rs.getInt("ma_nguoi_dung"));
                    binhLuan.setMaPhim(rs.getInt("ma_phim"));
                    binhLuan.setNoiDung(rs.getString("noi_dung"));
                    binhLuan.setNgayTao(rs.getDate("ngay_tao").toLocalDate());
                    binhLuan.setTrangThai(rs.getBoolean("trang_thai"));
                    
                
              
                danhSachBinhLuan.add(binhLuan);
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return danhSachBinhLuan;
}

}
