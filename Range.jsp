<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movie Nights</title>
</head>
<body>
	<%@page import="jsp_azure.DataHandler"%>
	<%@page import="java.sql.ResultSet"%>
	<%
		final DataHandler handler = new DataHandler();

		//Get data from form
		String range1 = request.getParameter("range_1");
		String range2 = request.getParameter("range_2");


		if (range1.equals("") || range2.equals("")) {
			response.sendRedirect("get_customer_range_form.jsp");
		} else {
			int range_1 = Integer.parseInt(range1);
			int range_2 = Integer.parseInt(range2);

			//Send to function to add to database
			final ResultSet customers = handler.getCustomersInRange(range_1, range_2);
	%>
	 <!-- Print table -->
	<table cellspacing="2" cellpadding="2" border="1">
		<tr>
			<!-- The table headers row -->
			<td align="center">
				<h4>Time</h4>
			</td>
			<td align="center">
				<h4>Movie Name</h4>
			</td>
			<td align="center">
				<h4>Duration</h4>
			</td>
		</tr>
		<%
			while (customers.next()) { // For each customer record returned... 
					// Extract the attribute values for every row returned
					final String customer_name = customers.getString("customer_name");
					final String customer_address = customers.getString("customer_address");
					final String customer_category = customers.getString("customer_category");

					out.println("<tr>");
					out.println("<td align=\"center\">" + customer_name + "</td><td align=\"center\"> "
							+ customer_address + "</td><td align=\"center\"> " + customer_category + "</td>");
					out.println("</tr>");
				}

			}
			//final ResultSet movies = handler.getAllMovies();
		%>
	</table>
</body>
</html>