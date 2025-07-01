<%-- 
    Document   : profile
    Created on : Jul 1, 2025, 5:08:29 PM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Your Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(145deg, #1a1a1a 0%, #dc3545 100%);
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                margin: 0;
                font-family: 'Inter', sans-serif;
                overflow-x: hidden;
            }
            .profile-container {
                background: rgba(255, 255, 255, 0.1);
                padding: 3rem;
                border-radius: 20px;
                box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
                max-width: 550px;
                width: 100%;
                border: 1px solid rgba(255, 255, 255, 0.2);
                backdrop-filter: blur(15px);
                transition: transform 0.4s ease, box-shadow 0.4s ease;
                position: relative;
                overflow: hidden;
            }
            .profile-container:hover {
                transform: translateY(-10px);
                box-shadow: 0 15px 50px rgba(220, 53, 69, 0.4);
            }
            .profile-container::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: radial-gradient(circle at 30% 30%, rgba(220, 53, 69, 0.2), transparent);
                z-index: -1;
            }
            .avatar-container {
                display: flex;
                justify-content: center;
                margin-bottom: 2rem;
            }
            .avatar {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                object-fit: cover;
                border: 3px solid #dc3545;
                box-shadow: 0 0 15px rgba(220, 53, 69, 0.5), 0 0 30px rgba(220, 53, 69, 0.3);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .avatar:hover {
                transform: scale(1.1);
                box-shadow: 0 0 20px rgba(220, 53, 69, 0.7), 0 0 40px rgba(220, 53, 69, 0.4);
            }
            .profile-container h2 {
                color: #ffffff;
                text-align: center;
                margin-bottom: 2.5rem;
                font-weight: 700;
                font-size: 2.5rem;
                letter-spacing: -1px;
                text-transform: uppercase;
                background: linear-gradient(90deg, #dc3545, #ffffff);
                -webkit-background-clip: text;
                background-clip: text;
                -webkit-text-fill-color: transparent;
                animation: glow 2s ease-in-out infinite alternate;
            }
            .profile-field {
                margin-bottom: 1.8rem;
                position: relative;
                transition: transform 0.3s ease;
            }
            .profile-field label {
                color: #ffffff;
                font-weight: 500;
                font-size: 0.85rem;
                text-transform: uppercase;
                letter-spacing: 1.5px;
                margin-bottom: 0.5rem;
                display: block;
                text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            }
            .profile-field p {
                background: rgba(255, 255, 255, 0.15);
                color: #ffffff;
                padding: 14px 18px;
                border-radius: 12px;
                border: 1px solid #dc3545;
                margin: 0;
                font-size: 1.1rem;
                transition: all 0.3s ease;
                box-shadow: inset 0 2px 8px rgba(0, 0, 0, 0.2);
            }
            .profile-field p:hover {
                background: rgba(220, 53, 69, 0.3);
                transform: scale(1.03);
                border-color: #ffffff;
            }
            .profile-field input {
                background: rgba(255, 255, 255, 0.15);
                color: #ffffff;
                padding: 14px 18px;
                border-radius: 12px;
                border: 1px solid #dc3545;
                width: 100%;
                font-size: 1.1rem;
                transition: all 0.3s ease;
                box-shadow: inset 0 2px 8px rgba(0, 0, 0, 0.2);
            }
            .profile-field input:focus {
                outline: none;
                border-color: #ffffff;
                background: rgba(220, 53, 69, 0.3);
            }
            .btn-custom {
                background: #dc3545;
                color: #ffffff;
                border: none;
                padding: 10px 20px;
                border-radius: 8px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
                transition: all 0.3s ease;
                width: 100%;
                text-align: center;
            }
            .btn-custom:hover {
                background: #ffffff;
                color: #dc3545;
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(220, 53, 69, 0.5);
            }
            @keyframes glow {
                from {
                    text-shadow: 0 0 5px rgba(220, 53, 69, 0.5), 0 0 10px rgba(220, 53, 69, 0.3);
                }
                to {
                    text-shadow: 0 0 10px rgba(220, 53, 69, 0.7), 0 0 20px rgba(220, 53, 69, 0.5);
                }
            }
            @media (max-width: 576px) {
                .profile-container {
                    padding: 2rem;
                    margin: 1rem;
                    border-radius: 16px;
                }
                .profile-container h2 {
                    font-size: 1.8rem;
                }
                .profile-field p, .profile-field input {
                    font-size: 1rem;
                    padding: 12px 16px;
                }
                .avatar {
                    width: 100px;
                    height: 100px;
                }
            }
        </style>
    </head>
    <body>
        <div class="profile-container" id="profileCard">
            <div class="avatar-container">
                <img src="https://via.placeholder.com/120" alt="User Avatar" class="avatar">
            </div>
            <h2>User Profile</h2>
            <form id="profileForm" action="<%=request.getContextPath()%>/intermediate.jsp" method="POST">
                <div class="profile-field">
                    <label>Username</label>
                    <p id="usernameDisplay">${sessionScope.userInfor.username}</p>
                    <input type="text" name="username" id="usernameInput" value="${sessionScope.userInfor.username}" style="display: none;">
                </div>
                <div class="profile-field">
                    <label>Full Name</label>
                    <p id="fullnameDisplay">${sessionScope.userInfor.fullname}</p>
                    <input type="text" name="fullname" id="fullnameInput" value="${sessionScope.userInfor.fullname}" style="display: none;">
                </div>
                <div class="profile-field">
                    <label>Email</label>
                    <p id="emailDisplay">${sessionScope.userInfor.email}</p>
                    <input type="email" name="email" id="emailInput" value="${sessionScope.userInfor.email}" style="display: none;">
                </div>
                <div class="profile-field">
                    <label>Phone Number</label>
                    <p id="phoneDisplay">${sessionScope.userInfor.phoneNumber}</p>
                    <input type="tel" name="phoneNumber" id="phoneInput" value="${sessionScope.userInfor.phoneNumber}" style="display: none;">
                </div>
                <div class="profile-field">
                    <label>Type of User</label>
                    <p id="userTypeDisplay">${sessionScope.userInfor.typeOfUser}</p>
                    <input type="text" name="typeOfUser" id="userTypeInput" value="${sessionScope.userInfor.typeOfUser}" style="display: none;">
                </div>
                <!--gui kem thong tin an-->
                <input type="hidden" name="action" value="update_user"/>
                <input type="hidden" name="password" value="${sessionScope.userInfor.password}"/>
                <input type="hidden" name="id" value="${sessionScope.userInfor.id}"/>
                <div class="d-grid gap-2">
                    <button type="button" id="editButton" class="btn-custom">Edit</button>
                    <button type="submit" id="saveButton" class="btn-custom" style="display: none;">Save</button>
                </div>
            </form>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            const profileCard = document.getElementById('profileCard');
            const editButton = document.getElementById('editButton');
            const saveButton = document.getElementById('saveButton');
            const fields = [
                {display: 'usernameDisplay', input: 'usernameInput'},
                {display: 'fullnameDisplay', input: 'fullnameInput'},
                {display: 'emailDisplay', input: 'emailInput'},
                {display: 'phoneDisplay', input: 'phoneInput'},
                {display: 'userTypeDisplay', input: 'userTypeInput'}
            ];

            editButton.addEventListener('click', () => {
                fields.forEach(field => {
                    document.getElementById(field.display).style.display = 'none';
                    document.getElementById(field.input).style.display = 'block';
                });
                editButton.style.display = 'none';
                saveButton.style.display = 'block';
            });

            profileCard.addEventListener('mousemove', (e) => {
                const rect = profileCard.getBoundingClientRect();
                const x = e.clientX - rect.left;
                const y = e.clientY - rect.top;
                const centerX = rect.width / 2;
                const centerY = rect.height / 2;
                const rotateX = (y - centerY) / 20;
                const rotateY = (centerX - x) / 20;
                profileCard.style.transform = `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateY(-10px)`;
            });

            profileCard.addEventListener('mouseleave', () => {
                profileCard.style.transform = 'perspective(1000px) rotateX(0deg) rotateY(0deg) translateY(-10px)';
            });
        </script>
    </body>
</html>