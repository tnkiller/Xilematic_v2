<%-- 
    Document   : error
    Created on : Jun 9, 2025, 11:42:16 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xin lỗi - Máy chủ đang bận</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f5f5f5;
                margin: 0;
                flex-direction: column;
            }

            .character {
                position: relative;
                width: 200px;
                height: 200px;
                margin-bottom: 30px;
            }

            .face {
                width: 150px;
                height: 150px;
                background-color: #FFD700;
                border-radius: 50%;
                position: relative;
                margin: 0 auto;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }

            .eyes {
                display: flex;
                justify-content: space-around;
                padding-top: 40px;
            }

            .eye {
                width: 30px;
                height: 40px;
                background-color: #333;
                border-radius: 50%;
                position: relative;
            }

            .eye::after {
                content: '';
                position: absolute;
                width: 10px;
                height: 10px;
                background-color: white;
                border-radius: 50%;
                top: 8px;
                left: 5px;
            }

            .mouth {
                width: 60px;
                height: 20px;
                background-color: #333;
                border-radius: 0 0 30px 30px;
                position: absolute;
                bottom: 30px;
                left: 50%;
                transform: translateX(-50%);
                animation: sadMouth 2s infinite alternate;
            }

            @keyframes sadMouth {
                0% {
                    height: 20px;
                    border-radius: 0 0 30px 30px;
                }
                100% {
                    height: 30px;
                    border-radius: 0 0 60px 60px;
                }
            }

            .message {
                background-color: white;
                padding: 20px 30px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                text-align: center;
                max-width: 300px;
            }

            h1 {
                color: #e74c3c;
                margin-bottom: 10px;
            }

            p {
                color: #555;
                margin-top: 0;
            }

            .blush {
                width: 20px;
                height: 10px;
                background-color: #FFB6C1;
                border-radius: 50%;
                position: absolute;
                top: 70px;
                opacity: 0.7;
            }

            .blush.left {
                left: 20px;
            }

            .blush.right {
                right: 20px;
            }

            .sweat {
                width: 10px;
                height: 10px;
                background-color: #ADD8E6;
                border-radius: 50%;
                position: absolute;
                top: 20px;
                right: 30px;
                animation: sweatDrop 2s infinite;
            }

            @keyframes sweatDrop {
                0% {
                    transform: translateY(0);
                    opacity: 1;
                }
                100% {
                    transform: translateY(30px);
                    opacity: 0;
                }
            }
        </style>
    </head>
    <body>
        <div class="character">
            <div class="face">
                <div class="eyes">
                    <div class="eye"></div>
                    <div class="eye"></div>
                </div>
                <div class="mouth"></div>
                <div class="blush left"></div>
                <div class="blush right"></div>
                <div class="sweat"></div>
            </div>
        </div>

        <div class="message">
            <h1>Xin lỗi!</h1>
            <p>Máy chủ đang bận, vui lòng thử lại sau.</p>
        </div>

        <script>
            // Thêm hiệu ứng nhấp nháy mắt
            setInterval(() => {
                const eyes = document.querySelectorAll('.eye');
                eyes.forEach(eye => {
                    eye.style.height = '5px';
                    eye.style.borderRadius = '5px';
                    setTimeout(() => {
                        eye.style.height = '40px';
                        eye.style.borderRadius = '50%';
                    }, 200);
                });
            }, 3000);
        </script>
    </body>
</html>
