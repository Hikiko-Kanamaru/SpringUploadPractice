<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>画像のアップロード</title>
</head>
<body>
<h1>画像のアップロード</h1>
<form action="" method="post" enctype="multipart/form-data">
<p>画像ファイル:
<input type="file" name="upfile">
<input type="submit">
</p>
</form>

<h2>画像一覧</h2>
<c:forEach items="${fileList}" var="file">
<img src="images/${file.name}" alt="画像ファイルです。" width="300">


</c:forEach>


</body>
</html>