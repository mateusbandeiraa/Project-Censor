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
<script type="text/javascript" src="../js/jquery.quickfit.js"></script>
<script type="text/javascript" src="../js/scripts.js"></script>
<script src="../js/bootstrap-toggle.js"></script>
<link
	href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
	rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>CARDS ADMIN TOOLS</title>
</head>
<body>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3>CARDS ADMIN TOOLS <button type="button" class="btn btn-danger" style="float: right;" id="btn-logout">Logout</button> </h3>
			</div>
			<div class="panel-body">
				<div class="col-md-6">
					<div>
						<ul class="nav nav-tabs" role="tablist" id="mainTabs">
							<li role="presentation" class="active"><a href="#create"
								role="tab" data-toggle="tab">Criar cards</a></li>
							<li role="presentation"><a href="#edit" role="tab"
								data-toggle="tab">Editar</a></li>
							<li role="presentation"><a href="#import" role="tab"
								data-toggle="tab">Importar JSON</a></li>
							<li role="presentation"><a href="#sql" role="tab"
								data-toggle="tab">SQL</a></li>
						</ul>
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="create">
								<div class="panel-body">
									<form class="form-horizontal" method="POST"
										action="../Controller">
										<input type="hidden" name="cmd" id="cmd" value="gravar">
										<div class="form-group">
											<label class="control-label col-sm-2" for="cardWord">Palavra</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" id="cardWord"
													name="cardWord" required>
											</div>
										</div>

										<label class="control-label">Palavas censuradas:</label>
										<div class="panel-body">
											<c:forEach var="i" begin="0" end="4">

												<div class="form-group">
													<label class="control-label col-sm-2"
														for="censuredWord${i }">${i}.</label>
													<div class="col-sm-10">
														<input type="text" class="form-control"
															id="censuredWord${i}" name="censuredWord${i}" required>
													</div>
												</div>
											</c:forEach>
										</div>
										<button type="submit" class="btn btn-primary btn-block">
											<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
										</button>
									</form>
									${param.writeMsg }
								</div>
							</div>
							<div role="tabpanel" class="tab-pane" id="edit">
								<form class="form-horizontal" method="POST"
									action="../Controller" style="margin-top: 7px;">
									<input type="hidden" name="cmd" id="cmd" value="editar">
									<div class="form-group">
										<label class="control-label col-sm-2" for="editCardId">ID</label>
										<div class="col-sm-10">
											<input type="number" class="form-control" id="editCardId"
												name="editCardId" required readonly="readonly">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2" for="editCardWord">Palavra</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="editCardWord"
												name="editCardWord" required>
										</div>
									</div>

									<label class="control-label">Palavas censuradas:</label>
									<div class="panel-body">
										<c:forEach var="i" begin="0" end="4">

											<div class="form-group">
												<label class="control-label col-sm-2"
													for="editCensuredWord${i }">${i + 1}.</label>
												<div class="col-sm-10">
													<input type="text" class="form-control"
														id="editCensuredWord${i}" name="editCensuredWord${i}"
														required>
												</div>
											</div>
										</c:forEach>
									</div>
									<button type="submit" class="btn btn-warning btn-block">
										<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
										edições
									</button>
								</form>
								${param.editMsg }
							</div>
							<div role="tabpanel" class="tab-pane" id="import">
								<div class="panel-body">
									<form class="form-horizontal" method="POST"
										action="../Controller">
										<input type="hidden" name="cmd" value="importarJson">
										<div class="form-group">
											<label for="jsonTxt">JSON</label>
											<textarea class="form-control" id="jsonTxt" name="jsonTxt"
												style="height: 311px" required="required"></textarea>
										</div>
										<div class="col-sm-4">
											<input type="checkbox" data-toggle="toggle" data-width="100%"
												data-on="Sobrescrever" data-off="Incrementar"
												class="json-toggle" id="jsonOverwrite" name="jsonOverwrite">
										</div>
										<div class="col-sm-8">
											<button type="submit" class="btn btn-primary btn-block">
												<span class="glyphicon glyphicon-cloud-upload"></span>
												Importar
											</button>
										</div>
									</form>
								</div>
							</div>
							<div role="tabpanel" class="tab-pane" id="sql">
								<form class="form-horizontal" method="POST"
									action="../Controller">
									<div class="form-group">
										<input type="hidden" name="cmd" value="sqlStatement">
										<label for="sqlTxt">SQL</label>
										<textarea class="form-control" id="sqlTxt" name="sqlTxt"></textarea>
									</div>
									<button type="submit" class="btn btn-primary btn-block">
										<span class="glyphicon glyphicon-tasks"></span> Commit SQL
									</button>
								</form>
								<div>
									<div class="col-sm-6">
										<form action="../Controller" method="POST">
											<input type="hidden" name="cmd" value="sqlCreateTable">
											<button type="submit" id="sqlCreateTable"
												class="btn btn-warning btn-block">
												<span class="glyphicon glyphicon-list-alt"></span> Create
												Table
											</button>
										</form>
									</div>
									<div class="col-sm-6">
										<form action="../Controller" method="POST">
											<input type="hidden" name="cmd" value="sqlDropTable">
											<button type="submit" id="sqlDropTable"
												class="btn btn-danger btn-block">
												<span class="glyphicon glyphicon glyphicon-remove-sign"></span>
												Drop Table
											</button>
										</form>
									</div>
								</div>
								${param.sqlMsg }
							</div>
						</div>
						<script>
							var tab = "${param.tab}";
							console.log(tab);
							$('.nav-tabs a[href="#' + tab + '"]').tab('show');
						</script>
					</div>
				</div>
				<div class="col-sm-6">
					<ul class="nav nav-tabs" role="tablist" id="secondTabs">
						<li role="presentation" class="active"><a href="#jsonExport"
							role="tab" data-toggle="tab">Exportar JSON</a></li>
					</ul>
					<div role="tabpanel" class="tab-pane active" id="jsonExport">
						<textarea id="jsonOutput" readonly="readonly"></textarea>
						<button type="button" class="btn btn-primary btn-block"
							id="refreshJsonButton">
							<span class="glyphicon glyphicon-refresh" id="refresh-icon"></span>
							Atualizar JSON
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title">Cards salvos</div>
			</div>
			<div class="panel-body">
				<div id="cards"></div>
			</div>
			<script>
				renderCards();
			</script>
		</div>
	</div>
</body>
</html>