<%@ include file="/WEB-INF/jsp/include.jsp"%>

<html>
<head>
<title>Log in</title>
</head>

<body>
	<h1>Log in</h1>
	Don't know what to write in the fields? Try "user" and "password".
	<c:if test="${not empty param.login_error}">
		<div>
			Your login attempt was not successful, try writing "user" and
			"password".<br />
			<br /> Reason:
			<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
			.
		</div>
	</c:if>

	<form name="f" action="<c:url value='j_spring_security_check'/>"
		method="POST">
		<table>
			<tr>
				<td>Username:</td>
				<td><input type='text' name='j_username'
					value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password'></td>
			</tr>
			<tr>
				<td colspan='2'><label><input type="checkbox" name="_spring_security_remember_me">Remember me</label></td>
			</tr>

			<tr>
				<td colspan='2'><input name="submit" type="submit" value="Log in"></td>
			</tr>
		</table>
	</form>
</body>
</html>
