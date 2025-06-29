<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					style="text-align: center; background-color: #FFE598; font-weight: normal">会員認証</th>
			</tr>
			<tr>
				<td style="padding: 32px;">
					<table style="border-collapse: collapse; border: none;">
						<tr>
							<td>会員ID</td>
							<td><input name="id" autocomplete="off"></td>
						</tr>
						<tr>
							<td>パスワード</td>
							<td><input type="password" name="pwd"></td>
						</tr>
						<tr style="height: 19px;">
							<td colspan="2" style="border: none;"></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;"><input
								type="submit" value="ログイン"
								style="background-color: #DEEAF6; border: 1px solid black; width: 100%;">
							</td>
						</tr>
						<tr style="height: 19px;">
							<td colspan="2" style="border: none;"></td>
						</tr>
						<tr style="height: 19px;">
							<td colspan="2" style="border: none; color: red; text-align: center;">
								<c:if
									test="${not empty errorMsg}">
								${errorMsg}
							</c:if></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>