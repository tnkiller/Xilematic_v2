function playTrailer(trailer) {
        if (trailer) {
          window.open(trailer, '_blank');
        } else {
          alert('Trailer chưa được cập nhật');
        }
      }

      

function toggleCommentSection() {
    const commentsContainer = document.getElementById('comments-container');
    const toggleButton = document.getElementById('toggle-comments');
    
    // Sửa logic ẩn/hiện comments
    if (commentsContainer.classList.contains('hidden')) {
        commentsContainer.classList.remove('hidden');
        toggleButton.textContent = 'Ẩn bình luận';
    } else {
        commentsContainer.classList.add('hidden');
        toggleButton.textContent = 'Hiện bình luận';
    }
}
document.getElementById("playTrailerBtn").addEventListener("click", function() {
  // Chỉ định URL của video YouTube (thay thế bằng link video của bạn)
  var videoURL = "https://www.youtube.com/embed/dQw4w9WgXcQ"; // Thay đổi URL này

  // Hiển thị overlay và iframe
  document.getElementById("overlay").style.display = "flex";
  document.getElementById("trailerIframe").src = videoURL;
});

document.getElementById("closeBtn").addEventListener("click", function() {
  // Ẩn overlay khi đóng
  document.getElementById("overlay").style.display = "none";
  document.getElementById("trailerIframe").src = ""; // Dừng video khi đóng
});
