<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="modal fade" id="modal_ghe" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><%= request.getParameter("title")%></h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!--bat dau form-->
                        <form action="<%=request.getContextPath()%>/ghe" method="POST" id="gheForm">
                            <input type="hidden" name="action" value="add_ghe"/>
                            <input type="hidden" name="maRap" value="${maRap}"/>
                            <div class="mb-3">
                                <label for="soLuongGhe" class="col-form-label">Số lượng ghế</label>
                                <input type="number" name="soLuongGhe" class="form-control" id="soLuongGhe" required>
                            </div>
                            <div class="mb-3">
                                <label for="gheVIP" class="col-form-label">Ghế VIP</label>
                                <input type="text" name="gheVIP" class="form-control" id="gheVIP">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" form="gheForm" class="btn btn-primary"><%= request.getParameter("action")%></button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
