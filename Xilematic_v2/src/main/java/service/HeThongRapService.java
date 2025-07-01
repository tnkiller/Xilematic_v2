/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.HeThongRapDAO;
import java.sql.SQLException;
import java.util.List;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.RapPhim;

/**
 *
 * @author ASUS
 */
public class HeThongRapService implements IHeThongRapService{
    private HeThongRapDAO heThongRapDAO;

    public HeThongRapService() {
        heThongRapDAO = new HeThongRapDAO();
    }
    
    @Override
    public List<HeThongRap> getHeThongRapsForPage(int currentPage, int pageSize) {
       return heThongRapDAO.getHeThongRapsForPage(currentPage, pageSize);
    }

    @Override
    public List<CumRap> getCumRapByIDForPage(int currentPage, int pageSize, int heThongRapID) throws SQLException {
        return heThongRapDAO.getCumRapByIDForPage(currentPage, pageSize, heThongRapID);
    }

    @Override
    public List<RapPhim> getRapPhimsByCumRapIDForPage(int currentPage, int pageSize, int cumRapID) throws SQLException {
        return heThongRapDAO.getRapPhimsByCumRapIDForPage(currentPage, pageSize, cumRapID);
    }

    @Override
    public List<LichChieu> getLichChieusByMaRapForPage(int currentPage, int pageSize, int RapID) throws SQLException {
        return heThongRapDAO.getLichChieusByMaRapForPage(currentPage, pageSize, RapID);
    }

    @Override
    public void addHeThongRap(HeThongRap heThongRap) {
        heThongRapDAO.addHeThongRap(heThongRap);
    }

    @Override
    public void updateHeThongRap(HeThongRap heThongRap) {
        heThongRapDAO.updateHeThongRap(heThongRap);
    }

    @Override
    public void deleteHeThongRap(int maHeThongRap) {
        heThongRapDAO.deleteHeThongRap(maHeThongRap);
    }

    @Override
    public void addCumRap(CumRap cumRap) {
        heThongRapDAO.addCumRap(cumRap);
    }

    @Override
    public void updateCumRap(CumRap cumRap) {
    heThongRapDAO.updateCumRap(cumRap);
        }

    @Override
    public void deleteCumRap(int maCumRap) {
        heThongRapDAO.deleteCumRap(maCumRap);
    }

    @Override
    public void addRapPhim(RapPhim rapPhim) {
        heThongRapDAO.addRapPhim(rapPhim);
    }

    @Override
    public void updateRapPhim(RapPhim rapPhim) {
        heThongRapDAO.updateRapPhim(rapPhim);
    }

    @Override
    public void deleteRapPhim(int maRap) {
        heThongRapDAO.deleteRapPhim(maRap);
    }

    @Override
    public void addLichChieu(LichChieu lichChieu) {
        heThongRapDAO.addLichChieu(lichChieu);
    }

    @Override
    public void updateLichChieu(LichChieu lichChieu) {
        heThongRapDAO.updateLichChieu(lichChieu);
    }

    @Override
    public void deleteLichChieu(int maLichChieu) {
        heThongRapDAO.deleteLichChieu(maLichChieu);
    }

    @Override
    public int getTotalHeThongRap() {
       return heThongRapDAO.getTotalHeThongRap();
    }

    @Override
    public int getTotalCumRap(int heThongRapID) {
        return heThongRapDAO.getTotalCumRap(heThongRapID);
    }

    @Override
    public int getTotalRap(int cumRapID) {
       return heThongRapDAO.getTotalRap(cumRapID);
    }

    @Override
    public int getTotalLichChieu(int RapID) {
        return heThongRapDAO.getTotalLichChieu(RapID);
    }

    @Override
    public List<CumRap> getCumRapForPage(int currentPage, int pageSize) throws SQLException {
        return heThongRapDAO.getCumRapForPage(currentPage, pageSize);
    }

    @Override
    public List<RapPhim> getRapPhimsForPage(int currentPage, int pageSize) throws SQLException {
        return heThongRapDAO.getRapPhimsForPage(currentPage, pageSize);
    }

    @Override
    public List<LichChieu> getLichChieusForPage(int currentPage, int pageSize) throws SQLException {
        return heThongRapDAO.getLichChieusForPage(currentPage, pageSize);
    }

    @Override
    public int getTotalCumRap() {
       return heThongRapDAO.getTotalCumRap();
    }

    @Override
    public int getTotalRap() {
        return  heThongRapDAO.getTotalRap();
    }

    @Override
    public int getTotalLichChieu() {
        return heThongRapDAO.getTotalLichChieu();
    }

    @Override
    public HeThongRap getHeThongRapById(int maHeThongRap) {
       return heThongRapDAO.getHeThongRapById(maHeThongRap);
    }

    @Override
    public CumRap getCumRapById(int maCumRap) {
        return heThongRapDAO.getCumRapById(maCumRap);
    }

    @Override
    public RapPhim getRapPhimById(int maRap) {
        return heThongRapDAO.getRapPhimById(maRap);
    }

    @Override
    public LichChieu getLichChieuById(int maLichChieu) {
        return heThongRapDAO.getLichChieuById(maLichChieu);
    }
    public List<LichChieu> getLichChieuByRapPhimAndPhim(int maRap, int maPhim){
    return  heThongRapDAO.getLichChieuByRapPhimAndPhim(maRap, maPhim);
            }
    
}
