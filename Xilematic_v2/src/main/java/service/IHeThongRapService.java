/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ASUS
 */
import java.sql.SQLException;
import java.util.List;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.RapPhim;

public interface IHeThongRapService {
    // Pagination methods
    public List<HeThongRap> getHeThongRapsForPage(int currentPage , int pageSize);
    public List<CumRap> getCumRapByIDForPage(int currentPage, int pageSize,int heThongRapID) throws SQLException  ;
    public List<CumRap> getCumRapForPage(int currentPage, int pageSize) throws SQLException ;
    public List<RapPhim> getRapPhimsByCumRapIDForPage(int currentPage, int pageSize,int cumRapID) throws SQLException ;
    public List<RapPhim> getRapPhimsForPage(int currentPage, int pageSize) throws SQLException ;
    public List<LichChieu> getLichChieusByMaRapForPage(int currentPage, int pageSize,int RapID) throws SQLException ;
    public List<LichChieu> getLichChieusForPage(int currentPage, int pageSize) throws SQLException ;

    // HeThongRap CRUD Operations
    void addHeThongRap(HeThongRap heThongRap);
    void updateHeThongRap(HeThongRap heThongRap);
    void deleteHeThongRap(int maHeThongRap);

    // CumRap CRUD Operations
    void addCumRap(CumRap cumRap);
    void updateCumRap(CumRap cumRap);
    void deleteCumRap(int maCumRap);

    // RapPhim CRUD Operations
    void addRapPhim(RapPhim rapPhim);
    void updateRapPhim(RapPhim rapPhim);
    void deleteRapPhim(int maRap);

    // LichChieu CRUD Operations
    void addLichChieu(LichChieu lichChieu);
    void updateLichChieu(LichChieu lichChieu);
    void deleteLichChieu(int maLichChieu);
    //
   public int getTotalHeThongRap();
    public int getTotalCumRap(int heThongRapID);
    public int getTotalCumRap();
     public int getTotalRap(int cumRapID);
     public int getTotalRap();
     public int getTotalLichChieu(int RapID);
     public int getTotalLichChieu();
     
     public HeThongRap getHeThongRapById(int maHeThongRap);
     public CumRap getCumRapById(int maCumRap) ;
     public RapPhim getRapPhimById(int maRap);
     public LichChieu getLichChieuById(int maLichChieu);
}

