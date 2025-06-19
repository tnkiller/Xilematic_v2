

// Carousel slider script
const carousel = document.getElementById('upcomingCarousel');
const slides = carousel.children;
const totalSlides = slides.length;
const slideWidth = slides[0].clientWidth;

let currentIndex = 1; // vì clone đầu ở vị trí 0
carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;

function moveSlide(direction) {
    // Đảm bảo transition được áp dụng
    carousel.style.transition = carousel.style.transition === "none" 
        ? "transform 0.4s ease-in-out" 
        : carousel.style.transition;

    currentIndex += direction;
    carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;

    // Đợi animation chạy xong
    setTimeout(() => {
        if (currentIndex === 0) {
            // Nếu tới clone cuối → nhảy về ảnh thật cuối
            carousel.style.transition = "none";
            currentIndex = totalSlides - 2;
            carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;
        } else if (
            currentIndex === totalSlides - 1 ||
            currentIndex === totalSlides - 2 ||
            currentIndex === totalSlides - 3
        ) {
            // Nếu tới clone đầu → nhảy về ảnh thật đầu
            carousel.style.transition = "none";
            currentIndex = 1;
            carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;
        }
    }, 400); // khớp với thời gian transition
}