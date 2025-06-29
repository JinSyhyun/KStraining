<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
Boolean isCorrect = (Boolean) session.getAttribute("isCorrect");
Integer earnedPoint = (Integer) session.getAttribute("earnedPoint");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>数当てゲーム</title>
</head>
<body>
	<form method="post" action="MemberController">
		<table border="1" style="border-collapse: collapse;">
			<tr>
				<th colspan="2"
					style="text-align: center; background-color: #FFE598; font-weight: normal">結果&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="closeBtn" onclick="window.close()">X</button>
				</th>
			</tr>
			<tr>
				<td style="padding: 32px;">
					<table style="border-collapse: collapse; border: none;">
						<tr>
							<td>
								<%
								if (isCorrect != null && isCorrect) {
								%> 🎉 成功しました 🎉 <%
								} else if (isCorrect != null && isCorrect == false){
								%> 失敗しました...☹️ <%
								} else {
								%>今日挑戦可能回数を超過しました。<%} %>
							</td>
						</tr>
						<tr>
							<td>
								<%
								if (isCorrect != null && isCorrect) {
								%> <%=earnedPoint%>を獲得しました。<%} %>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>