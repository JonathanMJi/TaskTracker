<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>To-Do List Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body style="background-color: white">

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: teal">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand">Task Management System</a>
			</div>

			<%--<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list" class="nav-link">Tasks</a></li>
			</ul>--%>
		</nav>
	</header>
	<br>

	<div class="row";>
		<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

		<div class="container">
			<h3 class="text-center" style="color:teal">List of Tasks</h3>
			<hr>
			<div class="container text-left">

				<a href="<%=request.getContextPath()%>/new" class="btn btn-success" style="background-color:teal">Add
					New Task</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th style="background-color:lightgray">ID</th>
						<th style="background-color:lightgray">Task</th>
						<th style="background-color:lightgray">Due Date</th>
						<th style="background-color:lightgray">Status</th>
						<th style="background-color:lightgray"></th>
					</tr>
				</thead>
				<tbody>
					<!--   for (Todo todo: todos) {  -->
					<c:forEach var="task" items="${listTask}">

						<tr>
							<td><c:out value="${task.id}" /></td>
							<td><c:out value="${task.name}" /></td>
							<td><c:out value="${task.duedate}" /></td>
							<td><c:out value="${task.taskstatus}" /></td>
							<td><a href="edit?id=<c:out value='${task.id}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="delete?id=<c:out value='${task.id}' />">Delete</a></td>
						</tr>
					</c:forEach>
					<!-- } -->
				</tbody>

			</table>
		</div>
	</div>
</body>
</html>