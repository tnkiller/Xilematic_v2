<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Error Page</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container mt-5">
      <div class="row justify-content-center">
          <div class="col-md-6">
              <div class="card">
                  <div class="card-header bg-danger text-white">
                      <h3 class="text-center">Error</h3>
                  </div>
                  <div class="card-body">
                      <p class="text-center">
                          <%= request.getAttribute("errorMessage") != null 
                              ? request.getAttribute("errorMessage") 
                              : "An unexpected error occurred" %>
                      </p>
                      <div class="text-center">
                          <a href="home" class="btn btn-primary">Return to Home</a>
                      </div>
                  </div>
              </div>
          </div>
      </div>
  </div>
</body>
</html>