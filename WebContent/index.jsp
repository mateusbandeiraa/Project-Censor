<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.0.0.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="./css/style.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>CARDS LOGIN</title>
</head>
<body>
	<div class="container">
		<div class="col-sm-offset-3 col-sm-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-title">Login</div>
				</div>
				<div class="panel-body">
					<form class="form-horizontal col-sm-12" method="post"
						action="./Controller?cmd=login">
						<div class="form-group">
							<input class="form-control" type="text" id="username"
								name="username" placeholder="Usuário" required>
						</div>
						<div class="form-group">
							<input class="form-control" type="password" id="password"
								name="password" placeholder="Senha" required>
						</div>
						<button class="btn btn-primary btn-block" type="submit">Entrar</button>
					</form>
					${param.msg}
				</div>
			</div>
		</div>
</body>
</html>