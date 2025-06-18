const carousel = document.getElementById("carousel");
const slides = carousel.children;
const totalSlides = slides.length;
const slideWidth = slides[0].clientWidth;

let currentIndex = 1; // vì clone đầu ở vị trí 0
carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;

function moveSlide(direction) {
  if (carousel.style.transition === "") {
    carousel.style.transition = "transform 0.4s ease-in-out";
  }
  else if (carousel.style.transition === "none") {
    carousel.style.transition = "transform 0.4s ease-in-out";
  }

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
