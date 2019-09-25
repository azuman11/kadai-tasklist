<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%--雛型 css関係もここが雛型 --%>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>メッセージボード</title>
        <%-- CSS --%>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h1>タスク管理アプリケーション</h1>
            </div>
            <div id="content">
                ${param.content}
                <%--各ページのビューが入る <c:outは使わない index--%>
            </div>
            <div id="footer">
                by Kaname Higashi
            </div>

        </div>

    </body>
</html>