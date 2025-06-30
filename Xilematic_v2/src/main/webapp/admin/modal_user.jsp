

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="modal fade" id="modal_user" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><%= request.getParameter("title")%></h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!--bat dau form-->
                        <form action="<%=request.getContextPath()%>/intermediate.jsp" method="POST" id="myUserForm">
                            <!--thuoc tinh an gui kem-->
                            <input type="hidden" name="id"/>
                            <input type="hidden" name="action" value="add_user"/>

                            <!--thuoc tinh hien de nguoi dung nhap-->
                            <!--username-->
                            <div class="mb-3">
                                <label for="user-name" class="col-form-label">Username:</label>
                                <input type="text" name="username" class="form-control" id="user-name" required>
                            </div>
                            <!--fullname-->
                            <div class="mb-3">
                                <label for="fullname" class="col-form-label">Fullname:</label>
                                <input type="text" name="fullname"  class="form-control" id="fullname" required>
                            </div>
                            <!--email-->
                            <div class="mb-3">
                                <label for="email" class="col-form-label">Email:</label>
                                <input type="email" name="email"  class="form-control" id="email" required>
                            </div>
                            <!--phoneNumber-->
                            <div class="mb-3">
                                <label for="phoneNumber" class="col-form-label">Phone number:</label>
                                <input type="tel" name="phoneNumber"  class="form-control" id="phoneNumber" required>
                            </div>
                            <!--typeOfUser-->
                            <div class="mb-3">
                                <label or="typeOfUser" class="col-form-label">Role:</label>
                                <select name="typeOfUser" required class="form-control" id="status">
                                    <option value="admin">Admin</option>
                                    <option value="user">User</option>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" form="myUserForm" class="btn btn-primary"><%= request.getParameter("action")%></button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
