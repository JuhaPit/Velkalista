<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Velkalista</title>
</head>
<body>

	<form action="ostokset" method="post">
		<table>
			<caption>OSTOKSET</caption>
			<thead>
				<tr>
					<td>ID</td>
					<td>Ostaja</td>
					<td>Päivämäärä</td>
					<td>Summa</td>
					<td>Toiminto</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ostokset}" var="ost">
					<tr>
						<td><c:out value="${ost.id}" /></td>
						<td><c:out value="${ost.ostaja}" /></td>
						<td><c:out value="${ost.pvm}" /></td>
						<td><c:out value="${ost.summa}" /></td>
						
						<td><a href="ControllerServlet?param=${ost.id}">Tarkastele</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td>LISÄÄ</td>
					<td><select name="ostaja">
  					<option value="Juha">Juha</option>
  					<option value="Joni">Joni</option>
  					<option value="Sami">Sami</option>
 					 <option value="Niko">Niko</option>
					</select></td>
					<td><input type="text" name="summa" placeholder="Summa (muoto 0.00)"/></td>
					<td><button type="submit"><b>Lisää ostos</b></button></td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
</html>