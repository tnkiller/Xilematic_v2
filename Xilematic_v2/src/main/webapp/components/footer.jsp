<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Footer</title>
        <link rel="stylesheet" href="https://ionic.io/ionicons">
        <link rel="stylesheet"
              href="<c:url value='/style/footer_style.css' />" />
    </head>
    <body>
        <footer>
            <div class="footer-content">
                <div class="footer-information">
                    <div class="footer-desc">
                        <img src="https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=150" alt="">
                        <p class="text-muted">Your ultimate destination for movie tickets and entertainment.</p>
                    </div>
                    <div class="footer-about">
                        <h5 class="">Quick Links</h5>
                        <ul class="">
                            <li><a href="#" class="footer-link">Movie Categories</a></li>
                            <li><a href="#" class="footer-link">Now Showing</a></li>
                            <li><a href="#" class="footer-link">Upcoming Films</a></li>
                            <li><a href="#" class="footer-link">Theater Locations</a></li>
                            <li><a href="#" class="footer-link">Ticket Booking</a></li>
                        </ul>
                    </div>
                    <div class="footer-about">
                        <h5>Customer Support</h5>
                        <ul>
                            <li><a href="#" class="footer-link">Help Center</a></li>
                            <li><a href="#" class="footer-link">Contact Us</a></li>
                            <li><a href="#" class="footer-link">FAQ</a></li>
                            <li><a href="#" class="footer-link">Refund Policy</a></li>
                            <li><a href="#" class="footer-link">+1 (555) 123-4567</a></li>
                        </ul>
                    </div>
                    <div class="footer-social">
                        <h5>Newsletter</h5>
                        <p>Subscribe for exclusive movie updates and offers!</p>
                        <div>
                            <input type="email" placeholder="Your email">
                            <button class="btn-subcribe" type="button">Subscribe</button>
                        </div>
                        <div class="social-icons">
                            <a href="#" class="social-icon"><span><ion-icon name="logo-facebook"></ion-icon></span></a>
                            <a href="#" class="social-icon"><span><ion-icon name="logo-instagram"></ion-icon></span></a>
                            <a href="#" class="social-icon"><span><ion-icon name="logo-twitter"></ion-icon></span></a>
                            <a href="#" class="social-icon"><span><ion-icon name="logo-youtube"></ion-icon></span></a>
                            <a href="#" class="social-icon"><span><ion-icon name="logo-linkedin"></ion-icon></span></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
    </body>
</html>
