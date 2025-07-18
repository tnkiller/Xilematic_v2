<%-- 
    Document   : change_password
    Created on : Jul 18, 2025, 2:35:33 AM
    Author     : ADMIN
--%>

<%@page import="constant.PageLink"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xilematic</title>
        <link href="<%=request.getContextPath()%>/style/change_password.css" rel="stylesheet">
    </head>
    <body>
        <div class="password-container">
            <div class="password-header">
                <h1>CHANGE PASSWORD</h1>
            </div>

            <form id="passwordForm" action='<%=request.getContextPath() + "/" + PageLink.USER_SERVLET%>' method="POST">
                <div class="input-group">
                    <label for="current-password">Current password</label>
                    <p style="color:red; margin-bottom: 5px;">${msg}</p>
                    <input type="password" id="current-password" required name="currentPassword">
                </div>

                <div class="input-group">
                    <label for="new-password">New password</label>
                    <input type="password" id="new-password" required name="newPassword">
                    <span class="show-password" onclick="togglePassword('new-password')">Show</span>
                    <div class="strength-meter">
                        <div class="strength-bar" id="strength-bar"></div>
                    </div>
                </div>

                <div class="input-group">
                    <label for="confirm-password">Confirm new password</label>
                    <input type="password" id="confirm-password" required>
                    <span class="show-password" onclick="togglePassword('confirm-password')">Show</span>
                </div>

                <div class="password-rules">
                    <h3>Requirement:</h3>
                    <ul class="rules-list">
                        <li>At least 6 characters</li>
                        <li>Contains at least 1 digit (0-9)</li>
                        <li>Contains at least 1 uppercase and 1 lowercase letter</li>
                    </ul>
                </div>

                <button type="submit" class="submit-btn" name="action" value="change_password">Change password</button>
            </form>
        </div>

        <script>
            // Hiển thị/ẩn mật khẩu
            function togglePassword(id) {
                const input = document.getElementById(id);
                const toggleText = input.nextElementSibling;

                if (input.type === 'password') {
                    input.type = 'text';
                    toggleText.textContent = 'Hide';
                } else {
                    input.type = 'password';
                    toggleText.textContent = 'Show';
                }
            }

            // Kiểm tra độ mạnh mật khẩu
            const newPassword = document.getElementById('new-password');
            const strengthBar = document.getElementById('strength-bar');

            newPassword.addEventListener('input', function () {
                const strength = calculatePasswordStrength(this.value);
                strengthBar.style.width = strength + '%';

                if (strength < 30) {
                    strengthBar.style.background = '#e00';
                } else if (strength < 70) {
                    strengthBar.style.background = '#e90';
                } else {
                    strengthBar.style.background = '#0a0';
                }
            });

            function calculatePasswordStrength(password) {
                let strength = 0;

                if (password.length > 5)
                    strength += 30;
                if (/[A-Z]/.test(password))
                    strength += 20;
                if (/[a-z]/.test(password))
                    strength += 20;
                if (/[0-9]/.test(password))
                    strength += 30;

                return Math.min(strength, 100);
            }
        </script>
    </body>
</html>
