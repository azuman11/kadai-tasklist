<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- new, edit用--%>
<%-- 入力画面, 編集画面--%>

<%--未入力時 --%>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<%--all入力時 --%>

<%-- value="${task.title}"で、リクエストスコープ(controller)でセットされたtaskから参照 --%>
<%--valueに中身が入っている場合は、既に入ってる状態で表示される --%>
<label for="content">タスク</label><br />
<input type="text" name="content" value="${task.content}" />
<br /><br />


<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>