<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Query Result</title>
</head>
<body>
	<%@page import="jsp_azure.DataHandler"%>
	<%@page import="java.sql.ResultSet"%>
	<%@page import="java.sql.Array"%>
	<%
		// The handler is the one in charge of establishing the connection. 
		DataHandler handler = new DataHandler();
		// Get the attribute values passed from the input form.
		String customer_name = request.getParameter("customer_name");
		String customer_address = request.getParameter("customer_address");
		String customer_category_string = request.getParameter("customer_category");

		System.out.println(customer_name + " " + customer_address + " " + customer_category_string);
		
		//Error Checking
		if (customer_name.equals("") || customer_address.equals("") || customer_category_string.equals("")) {
			response.sendRedirect("add_movie_form.jsp");
		} else {

			//Get int of category
			int customer_category = Integer.parseInt(customer_category_string);
			//Print to console as personal check
			System.out.println(customer_category);
			
			//Run function to add to data base
			boolean success = handler.addCustomer(customer_name, customer_address, customer_category);
			
			System.out.println(success);
			if (!success) {
	%>
	<h2>There was a problem inserting the course</h2>
	<%
		} else {
	%>
	
	 <!-- Showing success-->
	<h2>The Customer:</h2>
	<ul>
		<li>Start Time: <%=customer_name%></li>
		<li>Movie Name: <%=customer_address%></li>
		<li>Duration: <%=customer_category_string%></li>
	</ul>
	<h2>Was successfully inserted.</h2>

	<a href="add_customer_form.jsp">Add another customer</a>
	</br>
	<a href="get_customer_range_form.jsp">See all customers in category
		range</a>
	<%
		}
		}
	%>
</body>
</html>