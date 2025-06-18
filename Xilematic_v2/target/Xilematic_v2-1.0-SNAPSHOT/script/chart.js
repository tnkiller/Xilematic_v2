// Khởi tạo biểu đồ
const ctx = document.getElementById('myChart').getContext('2d');

// Cấu hình màu sắc
const lineColor = 'rgba(75, 192, 192, 1)';
const fillColor = 'rgba(75, 192, 192, 0.2)';

// Số điểm dữ liệu ban đầu
const initialDataPoints = 20;
let labels = Array.from({length: initialDataPoints}, (_, i) => i + 1);

// Tạo dữ liệu ngẫu nhiên ban đầu
function generateRandomData() {
    return Array.from({length: initialDataPoints}, () => Math.floor(Math.random() * 99));
}

// Tạo biểu đồ
const chart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: labels,
        datasets: [{
                label: 'Giá trị ngẫu nhiên',
                data: generateRandomData(),
                borderColor: lineColor,
                backgroundColor: fillColor,
                borderWidth: 2,
                tension: 0.3,
                fill: true,
                pointRadius: 0 // Ẩn các điểm để tập trung vào đường
            }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: true,
        animation: {
            duration: 1000,
            easing: 'easeOutQuad'
        },
        plugins: {
            legend: {
                display: false
            },
            tooltip: {
                mode: 'index',
                intersect: false
            }
        },
        scales: {
            x: {
                grid: {
                    display: false
                },
                title: {
                    display: true,
                    text: 'Thời gian'
                }
            },
            y: {
                min: 0,
                max: 100,
                grid: {
                    color: 'rgba(0, 0, 0, 0.05)'
                },
                title: {
                    display: true,
                    text: 'Giá trị'
                }
            }
        },
        interaction: {
            intersect: false,
            mode: 'nearest'
        }
    }
});

// Hàm cập nhật dữ liệu với hiệu ứng dịch chuyển mượt mà
function updateChart() {
    // Lấy dữ liệu hiện tại
    const oldData = chart.data.datasets[0].data;

    // Dịch chuyển dữ liệu sang trái (bỏ giá trị đầu tiên)
    const newData = oldData.slice(1);

    // Thêm giá trị ngẫu nhiên mới vào cuối
    newData.push(Math.floor(Math.random() * 100));

    // Cập nhật dữ liệu
    chart.data.datasets[0].data = newData;

    // Cập nhật biểu đồ với animation
    chart.update();
}

// Cập nhật dữ liệu mỗi 1 giây (1000ms)
const updateInterval = setInterval(updateChart, 5000);

// Dừng cập nhật khi trang bị đóng
window.addEventListener('beforeunload', () => {
    clearInterval(updateInterval);
});