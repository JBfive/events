<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Show All Events Page</title>
	<link rel="stylesheet" type="text/css" href="/main.css">
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
</head>
<body>
	<h1>Welcome, <c:out value="${user.getFirstName()}"/></h1>
	<a href="/logout">Logout</a>
	
	<h3>Here are some of the events in your state:</h3>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Location</th>
				<th>Host</th>
				<th>Action/Status</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${stateEvent}" var="e">
			<tr>
					<td><a href="events/${e.id}">${e.name}</a></td>
					<td><fmt:formatDate type = "date" value = "${e.date}"/></td>
					<td>${e.city}</td>
					<td>${e.host.firstName}</td>
					<c:choose>
						<c:when test="${e.host.id == uId }">
							<td><a href="/events/${e.id}/edit">Edit </a>   <a href="/delete/${e.id}"> Delete</a></td>
						</c:when>
						
						 <c:when test="${e.attendees.contains(user) }">
							<td>Joining.... <a href="/cancel/${e.id}">Cancel</a>
						</c:when>
						
						<c:otherwise>
							<td><a href="join/${e.id}"> JOIN </a></td>
						</c:otherwise>
					</c:choose>
			</tr>
				</c:forEach>
		</tbody>
	</table>
	
	
	<h3>Here are some of the events in other states:</h3>
	
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Location</th>
				<th>State</th>
				<th>Host</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${osEvent}" var="o">
			<tr>
					<td><a href="events/${o.id}">${o.name}</a></td>
					<td><fmt:formatDate type = "date" value = "${o.date}"/></td>
					<td>${o.city}</td>
					<td>${o.state}</td>
					<td>${o.host.firstName}</td>
					<c:choose>
						<c:when test="${o.host.id == uId}">
							<td><a href="/events/${o.id}/edit">Edit  </a>   <a href="/delete/${o.id}">  Delete</a></td>
						</c:when>
						
						
						<c:when test="${o.attendees.contains(user) }">
							<td>Joining.... <a href="/cancel/${o.id}">Cancel</a>
						</c:when>
						<c:otherwise>			
							<td><a href="join/${o.id}"> JOIN </a></td>
						
						</c:otherwise>
					</c:choose>
			</tr>
				</c:forEach>
		</tbody>
	</table>
	
	<h2>Create an Event</h2>
	<p class="text text-danger"><form:errors path="events.*"/></p>
	<form:form class="form-group " method="POST" action="/add_events" modelAttribute="events">
			
			<p><form:errors path="events.*"/></p>
			<p>
	            <form:label path="name">Name:</form:label>
	            <form:input type="text" path="name"/>
	        </p>
	        <p>
	            <form:label path="date">Date:</form:label>
	            <form:input type="date" path="date"/>
	        </p>
	        <p>
	            <form:label path="city">Location:</form:label>
	            <form:input type="text" path="city"/>
	        </p>
	        <p>
	           
	            <form:select path="state">
	            	<form:option value="AL">AL</form:option>
					<form:option value="AK">AK</form:option>
					<form:option value="AR">AR</form:option>
					<form:option value="AZ">AZ</form:option>
					<form:option value="CA">CA</form:option>
					<form:option value="CO">CO</form:option>
					<form:option value="CT">CT</form:option>
					<form:option value="DC">DC</form:option>
					<form:option value="DE">DE</form:option>
					<form:option value="FL">FL</form:option>
					<form:option value="GA">GA</form:option>
					<form:option value="HI">HI</form:option>
					<form:option value="IA">IA</form:option>
					<form:option value="ID">ID</form:option>
					<form:option value="IL">IL</form:option>
					<form:option value="IN">IN</form:option>
					<form:option value="KS">KS</form:option>
					<form:option value="KY">KY</form:option>
					<form:option value="LA">LA</form:option>
					<form:option value="MA">MA</form:option>
					<form:option value="MD">MD</form:option>
					<form:option value="ME">ME</form:option>
					<form:option value="MI">MI</form:option>
					<form:option value="MN">MN</form:option>
					<form:option value="MO">MO</form:option>
					<form:option value="MS">MS</form:option>
					<form:option value="MT">MT</form:option>
					<form:option value="NC">NC</form:option>
					<form:option value="NE">NE</form:option>
					<form:option value="NH">NH</form:option>
					<form:option value="NJ">NJ</form:option>
					<form:option value="NM">NM</form:option>
					<form:option value="NV">NV</form:option>
					<form:option value="NY">NY</form:option>
					<form:option value="ND">ND</form:option>
					<form:option value="OH">OH</form:option>
					<form:option value="OK">OK</form:option>
					<form:option value="OR">OR</form:option>
					<form:option value="PA">PA</form:option>
					<form:option value="RI">RI</form:option>
					<form:option value="SC">SC</form:option>
					<form:option value="SD">SD</form:option>
					<form:option value="TN">TN</form:option>
					<form:option value="TX">TX</form:option>
					<form:option value="UT">UT</form:option>
					<form:option value="VT">VT</form:option>
					<form:option value="VA">VA</form:option>
					<form:option value="WA">WA</form:option>
					<form:option value="WI">WI</form:option>
					<form:option value="WV">WV</form:option>
					<form:option value="WY">WY</form:option>
				</form:select>
	        </p>
	        <input type="hidden" name="uId" value="uId"/>
	        <input type="submit" value="Create Event"/>
	</form:form>
</body>
</html>