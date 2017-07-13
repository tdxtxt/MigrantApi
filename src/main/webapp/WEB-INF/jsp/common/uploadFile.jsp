<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>
	<p>单文件上传</p>
	<form method="POST" action="comm/uploadFile"
		enctype="multipart/form-data">
		<label>选择图片</label>
		<input type="file" name="file"> 
		<input type="submit" value="提  交">
	</form>
	<p>多文件上传</p>
	<form method="POST" action="comm/uploadFiles" 
		enctype="multipart/form-data">
			<label>图片1</label><input type="file" name="file" />
			<br /> 
			<label>图片2</label><input type="file" name="file" />
			<br /> 
			<input type="submit" value="提  交" />
	</form>
</body>
</html>