<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Image</title>
    <link rel="stylesheet" href="upload.css">
</head>
<body>
    <main>
        <div class="upload">
            <form action="uploadServlet" method="post" enctype="multipart/form-data" class="upload-form">
            <div class="img-container">
                <label for="file-upload" class="choose">Choose an Image</label>
                <input type="file" id="file-upload" name="image" accept="image/*" />
                <div class="image-preview">
                    <img id="img-preview" src="#" alt="Image Preview" style="display: none;"/>
                </div>
            </div>
                <div class="info">
                    <label for="name" class="label">Name:</label>
                    <input type="text" name="name" value="" required>
                    <input type="hidden" name="id">
                    <label for="price" class="label">Price:</label>
                    <input type="text" name="price" value="" required>
                    <label for="price" class="label">Category:</label>
                    <input type="text" name="category" value="" required>
                
                <input type="submit" value="Upload" />
                </div>
            </form>
        </div>
    </main>
    <script src="upload.js"></script>
</body>
</html>
