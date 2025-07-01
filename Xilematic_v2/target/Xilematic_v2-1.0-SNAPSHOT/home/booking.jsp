<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Đặt Vé Xem Phim - CinemaVision</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary: #6e45e2;
            --primary-light: rgba(110, 69, 226, 0.1);
            --secondary: #88d3ce;
            --dark: #1a1a2e;
            --darker: #0f0f1a;
            --light: #f8f9fa;
            --lighter: #ffffff;
            --accent: #ff6b6b;
            --text: #333;
            --text-light: #666;
            --card-bg: #ffffff;
            --shadow: 0 8px 30px rgba(0,0,0,0.08);
            --shadow-hover: 0 12px 40px rgba(0,0,0,0.12);
            --radius: 16px;
            --radius-sm: 8px;
            --transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
            --glass: rgba(255, 255, 255, 0.08);
            --glass-border: rgba(255, 255, 255, 0.1);
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Montserrat', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
            color: var(--text);
            min-height: 100vh;
            padding: 0;
            margin: 0;
            line-height: 1.6;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }
        
        .headerForWeb {
            background: var(--darker);
            backdrop-filter: blur(10px);
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 5%;
            height: 80px;
            position: sticky;
            top: 0;
            z-index: 1000;
            border-bottom: 1px solid var(--glass-border);
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        
        .logo {
            font-size: 1.8rem;
            font-weight: 700;
            color: var(--lighter);
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }
        
        .logo i {
            color: var(--accent);
        }
        
        .date-display {
            background: var(--primary);
            color: white;
            padding: 0.5rem 1.2rem;
            border-radius: 50px;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            font-size: 0.9rem;
            font-weight: 500;
            box-shadow: 0 4px 12px rgba(110, 69, 226, 0.25);
        }
        
        .booking-section {
            display: flex;
            gap: 2rem;
            margin-bottom: 2.5rem;
        }
        
        .movie-poster-container {
            flex: 0 0 280px;
            height: 420px;
            border-radius: var(--radius);
            overflow: hidden;
            box-shadow: var(--shadow);
            position: relative;
        }
        
        .movie-poster-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: var(--transition);
        }
        
        .movie-poster-container:hover img {
            transform: scale(1.03);
        }
        
        .movie-poster-overlay {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            background: linear-gradient(to top, rgba(0,0,0,0.8), transparent);
            padding: 1.5rem;
            color: white;
        }
        
        .movie-poster-title {
            font-size: 1.3rem;
            font-weight: 600;
            margin-bottom: 0.5rem;
        }
        
        .movie-poster-meta {
            display: flex;
            gap: 1rem;
            font-size: 0.85rem;
            opacity: 0.9;
        }
        
        .booking-card {
            background: var(--card-bg);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 2rem;
            flex: 1;
            transition: var(--transition);
        }
        
        .booking-card:hover {
            box-shadow: var(--shadow-hover);
        }
        
        .booking-title {
            font-size: 1.5rem;
            margin-bottom: 1.5rem;
            color: var(--dark);
            position: relative;
            padding-bottom: 0.75rem;
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }
        
        .booking-title:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 60px;
            height: 3px;
            background: linear-gradient(90deg, var(--primary), var(--secondary));
            border-radius: 3px;
        }
        
        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-bottom: 1rem;
        }
        
        .select-wrapper {
            position: relative;
        }
        
        .select-wrapper i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: var(--primary);
            z-index: 1;
        }
        
        select, input {
            width: 100%;
            padding: 14px 15px 14px 40px;
            border: 1px solid #e0e0e0;
            border-radius: var(--radius-sm);
            font-family: 'Montserrat', sans-serif;
            font-size: 0.95rem;
            background-color: var(--light);
            transition: var(--transition);
            appearance: none;
            -webkit-appearance: none;
        }
        
        select:focus, input:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px var(--primary-light);
        }
        
        button {
            background: linear-gradient(135deg, var(--primary), #8a63e8);
            color: white;
            border: none;
            padding: 16px 24px;
            border-radius: var(--radius-sm);
            font-family: 'Montserrat', sans-serif;
            font-weight: 600;
            font-size: 1rem;
            cursor: pointer;
            transition: var(--transition);
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            width: 100%;
            box-shadow: 0 4px 15px rgba(110, 69, 226, 0.3);
        }
        
        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(110, 69, 226, 0.4);
        }
        
        button:disabled {
            background: #e0e0e0;
            color: #a0a0a0;
            cursor: not-allowed;
            transform: none;
            box-shadow: none;
        }
        
        .showtimes-container {
            background: var(--card-bg);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 2rem;
        }
        
        .showtimes-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }
        
        .showtimes-title {
            font-size: 1.5rem;
            color: var(--dark);
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }
        
        .theater-info {
            display: flex;
            gap: 1.5rem;
            margin-bottom: 1.5rem;
            align-items: center;
            padding: 1.5rem;
            background: var(--light);
            border-radius: var(--radius-sm);
        }
        
        .theater-icon {
            font-size: 2rem;
            color: var(--primary);
        }
        
        .theater-name {
            font-weight: 600;
            font-size: 1.1rem;
        }
        
        .theater-address {
            font-size: 0.9rem;
            color: var(--text-light);
        }
        
        .showtime-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
            gap: 1rem;
        }
        
        .showtime-item {
            background: var(--light);
            border-radius: var(--radius-sm);
            padding: 1.2rem;
            text-align: center;
            transition: var(--transition);
            border: 1px solid #e0e0e0;
            cursor: pointer;
        }
        
        .showtime-item:hover {
            border-color: var(--primary);
            transform: translateY(-3px);
            box-shadow: 0 6px 12px rgba(0,0,0,0.08);
        }
        
        .showtime-time {
            font-weight: 700;
            font-size: 1.2rem;
            color: var(--primary);
            margin-bottom: 0.5rem;
        }
        
        .showtime-type {
            font-size: 0.8rem;
            color: var(--text-light);
            margin-bottom: 0.5rem;
            background: var(--primary-light);
            padding: 0.25rem 0.5rem;
            border-radius: 4px;
            display: inline-block;
        }
        
        .showtime-seats {
            font-size: 0.75rem;
            color: var(--accent);
            font-weight: 600;
        }
        
        .btn-book {
            background: var(--accent);
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 0.85rem;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
            margin-top: 0.75rem;
            transition: var(--transition);
            border: none;
            cursor: pointer;
            width: 100%;
        }
        
        .btn-book:hover {
            background: #ff5252;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(255, 107, 107, 0.3);
        }
        
        .loading {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 3rem;
        }
        
        .spinner {
            width: 50px;
            height: 50px;
            border: 4px solid rgba(110, 69, 226, 0.1);
            border-radius: 50%;
            border-top-color: var(--primary);
            animation: spin 1s ease-in-out infinite;
        }
        
        @keyframes spin {
            to { transform: rotate(360deg); }
        }
        
        .empty-state {
            text-align: center;
            padding: 3rem;
            color: var(--text-light);
        }
        
        .empty-state i {
            font-size: 3rem;
            color: #ddd;
            margin-bottom: 1rem;
        }
        
        .empty-state h3 {
            font-weight: 600;
            margin-bottom: 0.5rem;
            color: var(--dark);
        }
        
        /* Modern 2025 elements */
        .glass-card {
            background: var(--glass);
            backdrop-filter: blur(10px);
            border: 1px solid var(--glass-border);
        }
        
        .neumorphic {
            background: var(--light);
            border-radius: var(--radius);
            box-shadow: 8px 8px 16px #d1d9e6, 
                        -8px -8px 16px #ffffff;
        }
        
        .hover-grow {
            transition: transform 0.3s ease;
        }
        
        .hover-grow:hover {
            transform: scale(1.02);
        }
        
        .gradient-text {
            background: linear-gradient(90deg, var(--primary), var(--accent));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }
        
        @media (max-width: 992px) {
            .booking-section {
                flex-direction: column;
            }
            
            .movie-poster-container {
                flex: 1;
                height: 400px;
                width: 100%;
            }
        }
        
        @media (max-width: 768px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
            
            .showtime-grid {
                grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
            }
            
            .headerForWeb {
                flex-direction: column;
                height: auto;
                padding: 1rem;
                gap: 1rem;
            }
            
            .logo {
                font-size: 1.5rem;
            }
        }
        
        .footer-namespace {
            all: initial;
            font-family: 'Montserrat', sans-serif;
            display: block;
        }
    </style>
</head>
<body>
   <%@ include file="/components/header.jsp" %>
    <div class="container">
        <div class="headerForWeb">
            <div class="logo">
                <i class="fas fa-film"></i>
                <span>${selectedMovie.movieName}</span>
            </div>
            <div class="date-display">
                <i class="far fa-calendar-alt"></i>
                <%= new java.text.SimpleDateFormat("EEEE, dd/MM/yyyy").format(new java.util.Date()) %>
            </div>
        </div>
        
        <main>
            <div class="booking-section">
                <!-- Movie poster on the left -->
                <div class="movie-poster-container hover-grow">
                    <img src="${selectedMovie.image}" alt="${selectedMovie.movieName}">
                    <div class="movie-poster-overlay">
                        <h3 class="movie-poster-title">${selectedMovie.movieName}</h3>
                        <div class="movie-poster-meta">
                            <span><i class="fas fa-star"></i> 9.2/10</span>
                            <span><i class="fas fa-clock"></i> 120 phút</span>
                        </div>
                    </div>
                </div>
                
                <!-- Booking form on the right -->
                <div class="booking-card neumorphic">
                    <h2 class="booking-title">
                        <i class="fas fa-rocket"></i>
                        <span class="gradient-text">Đặt vé siêu tốc</span>
                    </h2>
                    
                    <div class="form-grid">
                        <div class="select-wrapper">
                            <i class="fas fa-city"></i>
                            <select id="heThongRapSelect">
                                <option value="">Hệ thống rạp</option>
                                <c:forEach items="${listHeThongRap}" var="htr">
                                    <option value="${htr.maHeThongRap}">${htr.tenHeThongRap}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="select-wrapper">
                            <i class="fas fa-map-marker-alt"></i>
                            <select id="cumRapSelect" disabled>
                                <option value="">Cụm rạp</option>
                            </select>
                        </div>
                        
                        <div class="select-wrapper">
                            <i class="fas fa-theater-masks"></i>
                            <select id="rapPhimSelect" disabled>
                                <option value="">Rạp phim</option>
                            </select>
                        </div>
                        
                        <div class="select-wrapper">
                            <i class="far fa-calendar"></i>
                            <input type="date" id="ngayChieuInput" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                        </div>
                        
                        <button id="btnXemLichChieu" disabled>
                            <i class="fas fa-bolt"></i> Tìm lịch chiếu nhanh
                        </button>
                    </div>
                </div>
            </div>
            
            <div class="showtimes-container" id="lichChieuResult">
                <div class="empty-state">
                    <i class="far fa-clock"></i>
                    <h3>Chưa có lịch chiếu</h3>
                    <p>Vui lòng chọn rạp và ngày chiếu để xem lịch chiếu</p>
                </div>
            </div>
        </main>
    </div>
   
    <div class="footer-namespace">
        <%@ include file="/components/footer.jsp" %>
    </div>
    
    <script>
    $(document).ready(function() {
        // Reset các trạng thái ban đầu
        function resetSelects() {
            $('#cumRapSelect').prop('disabled', true)
                .html('<option value="">Cụm rạp</option>');
            $('#rapPhimSelect').prop('disabled', true)
                .html('<option value="">Rạp phim</option>');
            $('#btnXemLichChieu').prop('disabled', true);
            $('#lichChieuResult').html(`
                <div class="empty-state">
                    <i class="far fa-clock"></i>
                    <h3>Chưa có lịch chiếu</h3>
                    <p>Vui lòng chọn rạp và ngày chiếu để xem lịch chiếu</p>
                </div>
            `);
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
                    beforeSend: function() {
                        $('#cumRapSelect').html('<option value="">Đang tải...</option>');
                    },
                    success: function(data) {
                        $('#cumRapSelect').empty().append('<option value="">Cụm rạp</option>');
                        $.each(data, function(index, cumRap) {
                            $('#cumRapSelect').append(
                                '<option value="' + cumRap.maCumRap + '">' + cumRap.tenCumRap + '</option>'
                            );
                        });
                        $('#cumRapSelect').prop('disabled', false);
                    },
                    error: function() {
                        $('#cumRapSelect').html('<option value="">Lỗi khi tải cụm rạp</option>');
                    }
                });
            }
        });

        // Khi chọn Cụm Rạp
        $('#cumRapSelect').change(function() {
            // Reset các select phía dưới
            $('#rapPhimSelect').prop('disabled', true)
                .html('<option value="">Rạp phim</option>');
            $('#btnXemLichChieu').prop('disabled', true);
            $('#lichChieuResult').html(`
                <div class="empty-state">
                    <i class="far fa-clock"></i>
                    <h3>Chưa có lịch chiếu</h3>
                    <p>Vui lòng chọn rạp và ngày chiếu để xem lịch chiếu</p>
                </div>
            `);

            var cumRapId = $(this).val();
            if (cumRapId) {
                $.ajax({
                    url: 'SelectCalendar', 
                    type: 'GET',
                    data: {
                        action: 'getRapPhim',
                        cumRapId: cumRapId
                    },
                    beforeSend: function() {
                        $('#rapPhimSelect').html('<option value="">Đang tải...</option>');
                    },
                    success: function(data) {
                        $('#rapPhimSelect').empty().append('<option value="">Rạp phim</option>');
                        $.each(data, function(index, rapPhim) {
                            $('#rapPhimSelect').append(
                                '<option value="' + rapPhim.maRap + '">' + rapPhim.tenRap + '</option>'
                            );
                        });
                        $('#rapPhimSelect').prop('disabled', false);
                    },
                    error: function() {
                        $('#rapPhimSelect').html('<option value="">Lỗi khi tải rạp phim</option>');
                    }
                });
            }
        });

        // Kiểm tra điều kiện enable nút "Tìm lịch chiếu"
        $('#rapPhimSelect, #ngayChieuInput').change(function() {
            var rapPhimId = $('#rapPhimSelect').val();
            var ngayChieu = $('#ngayChieuInput').val();
            
            if (rapPhimId && ngayChieu) {
                $('#btnXemLichChieu').prop('disabled', false);
            } else {
                $('#btnXemLichChieu').prop('disabled', true);
            }
        });

        // Khi click nút "Tìm lịch chiếu"
        $('#btnXemLichChieu').click(function() {
            var rapPhimId = $('#rapPhimSelect').val();
            var ngayChieu = $('#ngayChieuInput').val();

            if(!rapPhimId || !ngayChieu) {
                alert("Vui lòng chọn đầy đủ thông tin!");
                return;
            }
            
            // Hiển thị loading
            $('#lichChieuResult').html(`
                <div class="loading">
                    <div class="spinner"></div>
                </div>
            `);

            $.ajax({
                url: 'SelectCalendar',
                type: 'GET',
                data: {
                    action: 'getLichChieu',
                    rapPhimId: rapPhimId,
                    maPhim: ${selectedMovie.id},
                    ngayChieu: ngayChieu
                },
                success: function(response) {
                    // Giả sử response là HTML được trả về từ server
                    if(response.trim() === '') {
                        $('#lichChieuResult').html(`
                            <div class="empty-state">
                                <i class="far fa-calendar-times"></i>
                                <h3>Không có suất chiếu</h3>
                                <p>Không tìm thấy suất chiếu nào cho phim này vào ngày đã chọn</p>
                            </div>
                        `);
                    } else {
                        $('#lichChieuResult').html(response);
                    }
                },
                error: function() {
                    $('#lichChieuResult').html(`
                        <div class="empty-state">
                            <i class="fas fa-exclamation-triangle"></i>
                            <h3>Lỗi khi tải lịch chiếu</h3>
                            <p>Đã xảy ra lỗi khi tải lịch chiếu. Vui lòng thử lại sau.</p>
                        </div>
                    `);
                }
            });
        });
    });
    </script>
</body>
</html>