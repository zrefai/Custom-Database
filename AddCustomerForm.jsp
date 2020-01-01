<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Add Customer</title>
  </head>
  <body>
    <h2>Add Customer</h2>

    <!-- Form for adding customer -->
    <form action="add_customer.jsp">
      <table border="1">
        <tr>
          <th colspan="2">Enter Customer Data:</th>
        </tr>

        <!-- getting customer name -->
        <tr>
          <td>Customer Name:</td>
          <td>
            <div style="text-align: center;">
              <input type="text" name="customer_name" />
            </div>
          </td>
        </tr>

        <!-- getting customer address-->
        <tr>
          <td>Customer Address:</td>
          <td>
            <div style="text-align: center;">
              <input type="text" name="customer_address" />
            </div>
          </td>
        </tr>

        <!-- getting customer category -->
        <tr>
          <td>Customer Category:</td>
          <td>
            <div style="text-align: center;">
              <input type="text" name="customer_category" />
            </div>
          </td>
        </tr>

        <!-- Buttons for clearing form and executing -->
        <tr>
          <td>
            <div style="text-align: center;">
              <input type="reset" value="Clear" />
            </div>
          </td>
          <td>
            <div style="text-align: center;">
              <input type="submit" value="Insert" />
            </div>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>
