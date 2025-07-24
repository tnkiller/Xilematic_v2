/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.HeThongRapService;
import service.IHeThongRapService;

/**
 *
 * @author DELL
 */
@WebServlet(name = "StatsServlet", urlPatterns = {"/stats"})
public class StatsServlet extends HttpServlet {

    private IHeThongRapService service;

    @Override
    public void init() { service = new HeThongRapService(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("type", "stats");                       // nav‑bar active
        req.setAttribute("listTenHeThongRap", service.getAllTenHeThongRap());
        req.setAttribute("monthlyRevenue",   service.getMonthlyRevenue());

        /* KHÔNG chuyển trang – cứ forward về admin.jsp để nó tự include dashboard.jsp */
        req.getRequestDispatcher("/admin/admin.jsp").forward(req, resp);
    }
}


