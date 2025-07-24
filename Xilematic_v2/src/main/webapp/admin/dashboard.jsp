<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Dashboard chỉ là một “mảnh” để include, KHÔNG có <html> -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<h3 class="mb-4">Dashboard thống kê</h3>

<!-- KPI -->
<div class="row g-4">
    <div class="col-md-3">
        <div class="card text-bg-primary text-center shadow-sm">
            <div class="card-body">
                <h6 class="mb-1">TỔNG DOANH THU (VNĐ)</h6>
                <h3 class="fw-bold" id="totalRevenue">0</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-bg-success text-center shadow-sm">
            <div class="card-body">
                <h6 class="mb-1">SỐ HỆ THỐNG RẠP</h6>
                <h3 class="fw-bold">${fn:length(listTenHeThongRap)}</h3>
            </div>
        </div>
    </div>
</div>

<!-- CHART + Danh sách -->
<div class="row mt-4 gy-4">
    <div class="col-lg-6">
        <div class="card shadow-sm">
            <div class="card-header fw-semibold">Doanh thu theo tháng</div>
            <div class="card-body">
                <canvas id="monthlyRevenueChart" height="140"></canvas>
            </div>
        </div>
    </div>

    <div class="col-lg-6">
        <div class="card shadow-sm">
            <div class="card-header fw-semibold">Danh sách hệ thống rạp</div>
            <div class="card-body">
                <ul class="list-group">
                    <c:forEach var="name" items="${listTenHeThongRap}">
                        <li class="list-group-item"><i class="bi bi-building me-2 text-primary"></i>${name}</li>
                        </c:forEach>
                        <c:if test="${empty listTenHeThongRap}">
                        <li class="list-group-item text-muted">Không có dữ liệu.</li>
                        </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- Dữ liệu ẩn để JS đọc -->
<div id="serverData" style="display:none;">
    <c:forEach var="e" items="${monthlyRevenue}">
        <span data-month="${e.key}" data-val="${e.value}"></span>
    </c:forEach>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        /* Tổng doanh thu & mảng labels/data */
        const spans = document.querySelectorAll('#serverData span');
        const labels = [], values = [];
        let total = 0;
        spans.forEach(s => {
            labels.push(s.dataset.month);
            const v = parseInt(s.dataset.val);
            values.push(v);
            total += v;
        });
        document.getElementById('totalRevenue').innerText = total.toLocaleString('vi-VN');

        /* Chart.js */
        new Chart(document.getElementById('monthlyRevenueChart'), {
            type: 'bar',
            data: {labels: labels, datasets: [{label: 'Doanh thu', data: values, borderWidth: 1}]},
            options: {
                responsive: true,
                animation: {duration: 1200},
                scales: {y: {beginAtZero: true, ticks: {callback: v => v.toLocaleString('vi-VN')}}}
            }
        });
    });
</script>
