<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="DataClass.GamePoint"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>数当てゲーム</title>
<c:if test="${disableInput}">
  <script>
    window.onload = function() {
      window.open('popup.jsp', 'popup', 'width=200,height=200');
    };
  </script>
</c:if>
</head>
<body>
	<table border="1" style="border-collapse: collapse;">
		<tr style="height: 19px;">
			<th colspan="12"
				style="text-align: center; background-color: #FFE598; font-weight: normal;">
				数当てゲーム&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="closeBtn" onclick="location.href='LogoutController'">X</button>
			</th>


		</tr>

		<tr>
			<td colspan="12" style="padding: 16px;">
				<!-- 내부 내용 전체 감싸는 테이블 -->
				<table style="border-collapse: collapse;">
					<tr>
						<td style="text-align: right;">保有ポイント</td>
						<td style="width: 48px;">${point}</td>
					</tr>

					<tr>
						<td colspan="2" style="height: 10px;"></td>
					</tr>

					<tr>
						<td colspan="2">隠れ数字: * * *</td>
					</tr>

					<tr>
						<td colspan="2">
							<!-- 입력 폼 -->
							<form action="GameController" method="post"
								onsubmit="return OnSubmit()">
								<table style="border: none; margin-top: 10px;">
									<tr>
										<td style="width: 64px;">入力</td>
										<td><input name="answer_100" maxlength="1"
											oninput="this.value = this.value.replace(/[^0-9]/g, '')"
											autocomplete="off" style="width: 16px; text-align: center;"
											<c:if test="${disableInput}">disabled</c:if> /> <input
											name="answer_10" maxlength="1"
											oninput="this.value = this.value.replace(/[^0-9]/g, '')"
											autocomplete="off" style="width: 16px; text-align: center;"
											<c:if test="${disableInput}">disabled</c:if> /> <input
											name="answer_1" maxlength="1"
											oninput="this.value = this.value.replace(/[^0-9]/g, '')"
											autocomplete="off" style="width: 16px; text-align: center;"
											<c:if test="${disableInput}">disabled</c:if> /></td>
										<td colspan="2"></td>
										<td><input type="submit" value="確認"
											style="background-color: #DEEAF6;"
											<c:if test="${disableInput}">disabled</c:if> /></td>
									</tr>
								</table>
							</form>
						</td>
					</tr>

					<tr>
						<td colspan="2" style="height: 20px;"></td>
					</tr>

					<tr>
						<td colspan="2">
							<!-- 결과 출력 테이블 -->
							<table style="border-collapse: collapse;">
								<tr>
									<th
										style="width: 64px; background-color: #0070C0; color: white; border: 1px solid black;">入力回数</th>
									<th
										style="width: 64px; background-color: #0070C0; color: white; border: 1px solid black;">入力情報</th>
									<th
										style="width: 64px; background-color: #0070C0; color: white; border: 1px solid black;">判定結果</th>
								</tr>
								<c:forEach var="i" begin="1" end="10">
									<tr>
										<td style="border: 1px solid black;">${i}回目</td>
										<td style="border: 1px solid black; text-align: center;">
											<c:if test="${i <= fn:length(resultList)}">
												${resultList[i-1].answerNum}
											</c:if>
										</td>
										<td style="border: 1px solid black; text-align: center;">
											<c:if test="${i <= fn:length(resultList)}">
												${resultList[i-1].resultContent}
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>

					<tr>
						<td colspan="2" style="height: 16px;"></td>
					</tr>

					<tr>
						<td colspan="2" style="color: red; text-align: center;"><c:if
								test="${not empty errorMsg}">
								${errorMsg}
							</c:if></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>