
//----------------------------------------------------------------------------------------------------------------------------
// Lấy phần tử canvas
var ctx = document.getElementById('myChart').getContext('2d');

// Dữ liệu cho biểu đồ thu nhập theo từng sản phẩm trong 4 quý
var chart = new Chart(ctx, {
    type: 'line', // Loại biểu đồ (line, bar, radar, ...)
    data: {
        labels: ['Q1', 'Q2', 'Q3', 'Q4'], // Các nhãn cho trục X (Quý 1 đến Quý 4)
        datasets: [
            {
                label: 'Average Revenue',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                data: [120000, 150000, 130000, 160000],
                fill: false,
                tension: 0.4, // Điều chỉnh độ căng của đường (0.4 tạo độ mềm mại)
            },
            {
                label: 'Customer',
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                borderColor: 'rgba(153, 102, 255, 1)',
                data: [90000, 110000, 105000, 120000],
                fill: false,
                tension: 0.4, // Điều chỉnh độ căng của đường
            },
            {
                label: 'Movie',
                backgroundColor: 'rgba(255, 159, 64, 0.2)',
                borderColor: 'rgba(255, 159, 64, 1)',
                data: [80000, 95000, 100000, 115000],
                fill: false,
                tension: 0.4, // Điều chỉnh độ căng của đường
            }
        ]

    },
    options: {
        responsive: true,
        responsiveAnimationDuration: 0, // Không có độ trễ khi thay đổi kích thước
        animation: {
            duration: 1500,
            easing: 'easeOutQuart',
        },
        scales: {
            y: {
                beginAtZero: true,
                title: {
                    display: true,
                    text: 'Thu nhập (VNĐ)',
                },
            },
            x: {
                title: {
                    display: true,
                    text: 'Quý',
                },
            },
        },
        plugins: {
            legend: {
                position: 'top',
            },
        },
        interaction: {
            mode: 'nearest',
            intersect: false,
        },
    }

});

// Cập nhật dữ liệu động mỗi 3 giây
setInterval(() => {
    // Thêm dữ liệu mới vào biểu đồ (dữ liệu giả định cho ví dụ)
    chart.data.datasets[0].data.push(Math.floor(Math.random() * 200000));  // Thêm thu nhập ngẫu nhiên cho Sản phẩm A
    chart.data.datasets[1].data.push(Math.floor(Math.random() * 150000));  // Thêm thu nhập ngẫu nhiên cho Sản phẩm B
    chart.data.datasets[2].data.push(Math.floor(Math.random() * 120000));  // Thêm thu nhập ngẫu nhiên cho Sản phẩm C

    // Xóa dữ liệu cũ nếu số lượng điểm vượt quá 4
    if (chart.data.datasets[0].data.length > 6) {
        chart.data.datasets[0].data.shift();
        chart.data.datasets[1].data.shift();
        chart.data.datasets[2].data.shift();
        chart.data.labels.push('Q5');  // Thêm nhãn cho quý mới
    }

    // Cập nhật biểu đồ
    chart.update();
}, 5000);  // Mỗi 3 giây sẽ cập nhật dữ liệu mới
//----------------------------------------------------------------------------------------------------------------------------

