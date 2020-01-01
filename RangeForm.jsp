<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Get Customer Category Range</title>
  </head>
  <body>
    <h2>Get Customer Category Range</h2>

    <!-- Form for getting ranges on customer category -->
    <form action="get_customer_range.jsp">
      <table border="1">
        <tr>
          <th colspan="2">Enter Customer Category Ranges:</th>
        </tr>

        <!-- getting start of range-->
        <tr>
          <td>Customer Range Start:</td>
          <td>
            <div style="text-align: center;">
              <input type="text" name="range_1" />
            </div>
          </td>
        </tr>

        <!-- getting end of range -->
        <tr>
          <td>Customer Range End:</td>
          <td>
            <div style="text-align: center;">
              <input type="text" name="range_2" />
            </div>
          </td>
        </tr>

        <!-- Buttons to clear form or execute -->
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
