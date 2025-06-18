document.addEventListener('DOMContentLoaded', function () {
    const autoPopup = document.getElementById('autoPopup');
    const closeBtn = document.querySelector('.close-btn');
    const countdownProgress = document.getElementById('countdownProgress');

    let animationStartTime;
    let animationFrame;
    let popupTimeout;
    const totalTime = 2; // 3 giây

    // Tự động hiển thị popup khi trang tải xong
    function showPopup() {
        autoPopup.style.display = 'flex';

        // Reset thanh đếm ngược
        countdownProgress.style.transition = 'none';
        countdownProgress.style.transform = 'scaleX(1)';

        // Kích hoạt animation
        setTimeout(() => {
            countdownProgress.style.transition = `transform ${totalTime}s linear`;
            countdownProgress.style.transform = 'scaleX(0)';

            // Bắt đầu đếm ngược mượt mà
            animationStartTime = performance.now();
            animateCountdown();

            // Tự động đóng sau totalTime giây
            popupTimeout = setTimeout(hidePopup, totalTime * 1000);
        }, 10);
    }

    function animateCountdown() {
        animationFrame = requestAnimationFrame(function (timestamp) {
            const elapsed = (timestamp - animationStartTime) / 1000;
            const remaining = Math.max(0, totalTime - elapsed);


            if (remaining > 0) {
                animateCountdown();
            }
        });
    }

    function hidePopup() {
        // Hủy các animation và timeout
        cancelAnimationFrame(animationFrame);
        clearTimeout(popupTimeout);

        // Thêm hiệu ứng đóng
        autoPopup.classList.add('popup-closing');

        // Ẩn popup sau khi animation kết thúc
        setTimeout(() => {
            autoPopup.style.display = 'none';
        }, 300);
    }

    // Đóng popup khi click nút đóng
    closeBtn.addEventListener('click', hidePopup);

    // Đóng popup khi click bên ngoài
    autoPopup.addEventListener('click', function (e) {
        if (e.target === autoPopup)
            hidePopup();
    });

    // Hiển thị popup sau khi trang tải xong
    setTimeout(showPopup, 100);
});