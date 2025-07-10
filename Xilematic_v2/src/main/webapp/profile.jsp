<%-- 
    Document   : profile
    Created on : Jul 1, 2025, 5:08:29 PM
    Author     : ADMIN
--%>
<%@page import="constant.PageLink"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Your Profile</title>
        <link rel="stylesheet" href="<c:url value='/style/profile.css' />">
    </head>
    <body>
        <!--include header-->
        <header>
            <jsp:include page="${request.getContextPath()}/components/header.jsp">
                <jsp:param name="page" value="profile"/>
            </jsp:include>
        </header>
        <div class="container">
            <div class="profile-card">
                <div class="username">${sessionScope.userInfor.username}</div>
                <div class="role" style='color: ${sessionScope.userInfor.typeOfUser eq "admin" ? "#74b9ff" : "#55efc4"}'>${sessionScope.userInfor.typeOfUser}</div>
                <div class="avatar" style="background-color: ${sessionScope.colorCode};">${sessionScope.userInfor.username.charAt(0).toString().toUpperCase()}</div>
            </div>

            <div class="info-panel">
                <div class="online-status">
                    <span>online</span>
                    <div class="status-dot"></div>
                </div>

                <div class="info-header">Information details</div>

                <form id="profileForm" action='<%=request.getContextPath() + "/" + PageLink.INTERMEDIATE_PAGE%>' method="POST">
                    <input type="hidden" name="action" value="update_user">
                    <input type="hidden" name="id" value="${sessionScope.userInfor.id}">
                    <input type="hidden" name="typeOfUser" value="${sessionScope.userInfor.typeOfUser}">
                    <input type="hidden" name="password" value="${sessionScope.userInfor.password}">

                    <div class="info-grid">

                        <div class="info-item">
                            <div class="info-title">Username</div>
                            <div class="info-content">${sessionScope.userInfor.username}</div>
                            <input type="text" name="username" class="edit-input" value="${sessionScope.userInfor.username}">
                        </div>

                        <div class="info-item">
                            <div class="info-title">Họ và tên</div>
                            <div class="info-content">${sessionScope.userInfor.fullname}</div>
                            <input type="text" name="fullname" class="edit-input" value="${sessionScope.userInfor.fullname}">
                        </div>

                        <div class="info-item">
                            <div class="info-title">Email</div>
                            <div class="info-content">${sessionScope.userInfor.email}</div>
                            <input type="email" name="email" class="edit-input" value="${sessionScope.userInfor.email}">
                        </div>

                        <div class="info-item">
                            <div class="info-title">Số điện thoại</div>
                            <div class="info-content">${sessionScope.userInfor.phoneNumber}</div>
                            <input type="tel" name="phoneNumber" class="edit-input" value="${sessionScope.userInfor.phoneNumber}">
                        </div>

                    </div>

                    <div class="edit-controls">
                        <button type="button" class="edit-btn" onclick="toggleEdit()">Chỉnh sửa</button>
                        <button type="button" class="edit-btn cancel" onclick="cancelEdit()" style="display: none;">Hủy</button>
                        <button type="submit" class="edit-btn save" style="display: none;">Lưu thay đổi</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            let isEditing = false;
            let originalValues = {};

            function toggleEdit() {
                const form = document.getElementById('profileForm');
                const infoItems = document.querySelectorAll('.info-item');
                const editBtn = document.querySelector('.edit-btn');
                const cancelBtn = document.querySelector('.edit-btn.cancel');
                const saveBtn = document.querySelector('.edit-btn.save');

                if (!isEditing) {
                    // Chuyển sang chế độ chỉnh sửa
                    isEditing = true;

                    // Lưu giá trị gốc
                    infoItems.forEach((item, index) => {
                        const content = item.querySelector('.info-content');
                        const input = item.querySelector('.edit-input, select');
                        if (content && input) {
                            originalValues[index] = {
                                content: content.textContent,
                                value: input.value
                            };

                            // Cập nhật giá trị input từ content hiển thị
                            if (input.type === 'date') {
                                // Chuyển đổi định dạng ngày từ dd/mm/yyyy sang yyyy-mm-dd
                                const dateStr = content.textContent;
                                if (dateStr.includes('/')) {
                                    const [day, month, year] = dateStr.split('/');
                                    input.value = `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
                                                            }
                                                        } else {
                                                            input.value = content.textContent;
                                                        }

                                                        item.classList.add('editing');
                                                    }
                                                });

                                                editBtn.style.display = 'none';
                                                cancelBtn.style.display = 'inline-block';
                                                saveBtn.style.display = 'inline-block';

                                            } else {
                                                // Thoát chế độ chỉnh sửa mà không lưu
                                                exitEditMode();
                                            }
                                        }

                                        function cancelEdit() {
                                            const infoItems = document.querySelectorAll('.info-item');

                                            // Khôi phục giá trị gốc
                                            infoItems.forEach((item, index) => {
                                                const content = item.querySelector('.info-content');
                                                const input = item.querySelector('.edit-input, select');
                                                if (originalValues[index]) {
                                                    content.textContent = originalValues[index].content;
                                                    input.value = originalValues[index].value;
                                                }
                                                item.classList.remove('editing');
                                            });

                                            exitEditMode();
                                        }

                                        function exitEditMode() {
                                            const editBtn = document.querySelector('.edit-btn');
                                            const cancelBtn = document.querySelector('.edit-btn.cancel');
                                            const saveBtn = document.querySelector('.edit-btn.save');

                                            isEditing = false;
                                            editBtn.style.display = 'inline-block';
                                            cancelBtn.style.display = 'none';
                                            saveBtn.style.display = 'none';
                                            originalValues = {};
                                        }

                                        // Cho phép hủy bằng Escape
                                        document.addEventListener('keydown', function (event) {
                                            if (event.key === 'Escape' && isEditing) {
                                                cancelEdit();
                                            }
                                        });
        </script>
    </body>
</html>