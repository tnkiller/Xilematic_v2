
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Đặt Vé Xem Phim</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        /* CSS đơn giản để kết quả trông đẹp hơn */
        #lichChieuResult { margin-top: 20px; border-top: 1px solid #ccc; padding-top: 10px; }
        .suat-chieu-item { 
            border: 1px solid #eee; 
            padding: 10px; 
            margin-bottom: 10px; 
            border-radius: 5px; 
            display: flex; 
            justify-content: space-between; 
            align-items: center; 
        }
        .btn-chon-ghe { 
            background-color: #007bff; 
            color: white; 
            padding: 5px 10px; 
            text-decoration: none; 
            border-radius: 4px; 
        }
        .gio-chieu { color: #dc3545; font-size: 1.2em; }
        select, input, button { margin: 10px 0; padding: 5px; }
    </style>
</head>
<body>
<h1>Đặt Vé Xem Phim</h1>
<p>Ngày hiện tại: <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) %></p>

<div>
    <%-- 1. Chọn Hệ Thống Rạp --%>
    <select id="heThongRapSelect">
        <option value="">Chọn hệ thống rạp</option>
        <c:forEach items="${listHeThongRap}" var="htr">
            <option value="${htr.maHeThongRap}">${htr.tenHeThongRap}</option>
        </c:forEach>
    </select>

    <%-- 2. Chọn Cụm Rạp (sẽ được load bằng AJAX) --%>
    <select id="cumRapSelect" disabled>
        <option value="">Vui lòng chọn hệ thống rạp trước</option>
    </select>

    <%-- 3. Chọn Rạp Phim (sẽ được load bằng AJAX) --%>
    <select id="rapPhimSelect" disabled>
        <option value="">Vui lòng chọn cụm rạp trước</option>
    </select>

    <%-- 4. Chọn Phim --%>
    <select id="phimSelect">
        <option value="">Chọn phim</option>
        <c:forEach items="${listPhim}" var="p">
            <option value="${p.id}">${p.movieName}</option>
        </c:forEach>
    </select>
    
    <%-- Ngày chiếu --%>
    <input type="date" id="ngayChieuInput" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">

    <button id="btnXemLichChieu" disabled>Xem Lịch Chiếu</button>
</div>

<%-- KHU VỰC HIỂN THỊ LỊCH CHIẾU --%>
<div id="lichChieuResult">
    <%-- Kết quả lịch chiếu sẽ được hiển thị ở đây --%>
</div>

<script>
$(document).ready(function() {
    // Reset các trạng thái ban đầu
    function resetSelects() {
        $('#cumRapSelect').prop('disabled', true)
            .html('<option value="">Vui lòng chọn hệ thống rạp trước</option>');
        $('#rapPhimSelect').prop('disabled', true)
            .html('<option value="">Vui lòng chọn cụm rạp trước</option>');
        $('#btnXemLichChieu').prop('disabled', true);
        $('#lichChieuResult').empty();
    }

    // Khi chọn Hệ Thống Rạp
    $('#heThongRapSelect').change(function() {
        resetSelects();
        
        var heThongRapId = $(this).val();
        if (heThongRapId) {
            $.ajax({
                url: 'SelectCalendar', 
                type: 'GET',
                data: {
                    action: 'getCumRap',
                    heThongRapId: heThongRapId
                },
                success: function(data) {
                    $('#cumRapSelect').empty().append('<option value="">Chọn cụm rạp</option>');
                    $.each(data, function(index, cumRap) {
                        $('#cumRapSelect').append(
                            '<option value="' + cumRap.maCumRap + '">' + cumRap.tenCumRap + '</option>'
                        );
                    });
                    $('#cumRapSelect').prop('disabled', false);
                },
                error: function() {
                    alert('Lỗi khi tải cụm rạp.');
                }
            });
        }
    });

    // Khi chọn Cụm Rạp
    $('#cumRapSelect').change(function() {
        // Reset các select phía dưới
        $('#rapPhimSelect').prop('disabled', true)
            .html('<option value="">Vui lòng chọn cụm rạp trước</option>');
        $('#btnXemLichChieu').prop('disabled', true);
        $('#lichChieuResult').empty();

        var cumRapId = $(this).val();
        if (cumRapId) {
            $.ajax({
                url: 'SelectCalendar', 
                type: 'GET',
                data: {
                    action: 'getRapPhim',
                    cumRapId: cumRapId
                },
                success: function(data) {
                    $('#rapPhimSelect').empty().append('<option value="">Chọn rạp</option>');
                    $.each(data, function(index, rapPhim) {
                        $('#rapPhimSelect').append(
                            '<option value="' + rapPhim.maRap + '">' + rapPhim.tenRap + '</option>'
                        );
                    });
                    $('#rapPhimSelect').prop('disabled', false);
                },
                error: function() {
                    alert('Lỗi khi tải rạp phim.');
                }
            });
        }
    });

    // Kiểm tra điều kiện enable nút "Xem Lịch Chiếu"
    $('#rapPhimSelect, #phimSelect').change(function() {
        var rapPhimId = $('#rapPhimSelect').val();
        var maPhim = $('#phimSelect').val();
        
        if (rapPhimId && maPhim) {
            $('#btnXemLichChieu').prop('disabled', false);
        } else {
            $('#btnXemLichChieu').prop('disabled', true);
        }
    });

    // Khi click nút "Xem Lịch Chiếu"
    $('#btnXemLichChieu').click(function() {
        var rapPhimId = $('#rapPhimSelect').val();
        var maPhim = $('#phimSelect').val();
        var ngayChieu = $('#ngayChieuInput').val();

        if(!rapPhimId || !maPhim || !ngayChieu) {
            alert("Vui lòng chọn đầy đủ thông tin!");
            return;
        }
        
        // Hiển thị loading...
        $('#lichChieuResult').html("<p>Đang tải lịch chiếu...</p>");

        $.ajax({
            url: 'SelectCalendar',
            type: 'GET',
            data: {
                action: 'getLichChieu',
                rapPhimId: rapPhimId,
                maPhim: maPhim,
                ngayChieu: ngayChieu
            },
            success: function(responseHtml) {
                $('#lichChieuResult').html(responseHtml);
            },
            error: function() {
                $('#lichChieuResult').html("<p>Lỗi khi tải lịch chiếu.</p>");
            }
        });
    });
});
</script>
</body>
</html>
