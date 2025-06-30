<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="modal fade" id="modal_rapPhim" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><%= request.getParameter("title")%></h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!--bat dau form-->
                        <form action="<%=request.getContextPath()%>/intermediate.jsp" method="POST" id="rapPhimForm">
                            <input type="hidden" name="action" value="add_rapPhim"/>
                            <div class="mb-3">
                                <label for="tenRap" class="col-form-label">Tên rạp</label>
                                <input type="text" name="tenRap" class="form-control" id="tenRap" required>
                            </div>
                            <select class="form-select" id="cumRap" name="maCumRap" required>
                                <option value="" disabled selected>Chọn cụm rạp</option>
                                <c:forEach var="cr" items="${listCumRap}">
                                    <option value="${cr.maCumRap}">${cr.tenCumRap}</option>
                                </c:forEach>
                            </select>

                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" form="rapPhimForm" class="btn btn-primary"><%= request.getParameter("action")%></button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
