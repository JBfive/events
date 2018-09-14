<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Display Event Info</title>
	<link rel="stylesheet" type="text/css" href="/main.css">
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
</head>
<body>
	<h1><c:out value="${event.name}"/></h1>
	<a href="/events">Back</a>
	<div class = "box2">
		<p>Host: <c:out value="${event.host.firstName}"/></p>
		<p>Date: <fmt:formatDate type = "date" value="${event.date}"/></p>
		<p>Location: <c:out value="${event.city}, ${event.state}" /></p>
		<p>People who are attending this event: <c:out value="${event.attendees.size()}"/></p>
	</div>
	
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Location</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${event.attendees}" var="u">
			<tr>
					<td>${u.firstName} ${u.lastName}</td>
					<td>${u.city}</td>
			</tr>
				</c:forEach>
		</tbody>
	</table>
	
	<div class="comments">
		<ul>
			<c:forEach items="${event.comments}" var="c">
			<li>${c.comment}</li>
			</c:forEach>
		</ul>
	</div>
	<form:form action="/comment/${event.id}" method="POST" modelAttribute="comments">
		
			<p>
	            <form:label path="comment">Add Comment:</form:label>
	            <form:input type="text" path="comment"/>
	        </p>
	        <form:hidden path="event" value="${event.id}"/>
		<input type="submit" value="Submit"/>
	</form:form>
</body>
</html>