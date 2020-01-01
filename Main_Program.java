import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main_Program {
	private static int ERROR_FLAG = 0;
	private static final String[] query_Options = { "(1) Enter a new customer", "(2) Enter a new department",
			"(3) Enter a new assembly with its customer-name, assembly-details, assembly-id, and date-ordered",
			"(4) Enter a new process-id and its department together with its type and information relevant to the type",
			"(5) Create a new account and associate it with the process, assembly, or department to which it is applicable",
			"(6) Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced",
			"(7) At the completion of a job, enter the date it completed and the information relevant to the type of job",
			"(8) Enter a transaction-no and its sup-cost and update all the costs (details) of the affected accounts by adding sup-cost to their current values of details",
			"(9) Retrieve the cost incurred on an assembly-id",
			"(10) Retrieve the total labor time within a department for jobs completed in the department during a given date",
			"(11) Retrieve the processes through which a given assembly-id has passed so far (in date-commenced order) and the department responsible for each process",
			"(12) Retrieve the jobs (together with their type information and assembly-id) completed during a given date in a given department",
			"(13) Retrieve the customers (in name order) whose category is in a given range",
			"(14) Delete all cut-jobs whose job-no is in a given range", "(15) Change the color of a given paint job",
			"(16) Import new customers from file", "(17) Export customers to file based on category range",
			"(18) Quit" };

	// Print the query options
	public static void print_queries() {

		for (int i = 0; i < query_Options.length; ++i) {
			System.out.println(query_Options[i]);
		}
		System.out.println();
		System.out.print("Please select the number corresponding to the query: ");
	}

	// Print all customer information
	public static void print_table(Connection connection, int table) throws SQLException {

		String query = "";

		switch (table) {
		case 1: // Print Customer table
			query = "SELECT * FROM Customer";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Customer table:");
				System.out.println("--Customer Name--|--Customer Address--|--Customer Category--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s | %s | %s", resultSet.getString(1), resultSet.getString(2),
							resultSet.getString(3)));
				}
			}
			break;

		case 2: // Print Department table
			query = "SELECT * FROM Department";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Department table:");
				System.out.println("--Department ID--|--Department Data--|--Account ID--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s | %s | %s", resultSet.getString(1), resultSet.getString(2),
							resultSet.getString(3)));
				}
			}
			break;

		case 3: // Print Process table
			query = "SELECT * FROM Process";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Process table:");
				System.out.println("--Process ID--|--Process Data--|--Department ID--|--Account ID--");
				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s | %s | %s | %s", resultSet.getString(1),
							resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
				}
			}
			break;

		case 4: // Print Assembly table
			query = "SELECT * FROM Assembly";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Assembly table:");
				System.out.println(
						"--Assembly ID--|--Date Ordered--|--Assembly Details--|--Customer Name--|--Account ID--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(
							String.format("----%s | %s | %s | %s | %s", resultSet.getString(1), resultSet.getString(2),
									resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
				}
			}
			break;

		case 5: // Print the Job table
			query = "SELECT * FROM Job";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Job table:");
				System.out.println("--Job ID--|--Date Commenced--|--Date Completed--|--Assembly ID--|--Process ID--|");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(

							String.format("----%s | %s | %s | %s | %s", resultSet.getString(1), resultSet.getString(2),
									resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
				}
			}
			break;

		case 6: // Print the Account table
			query = "SELECT * FROM Account";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Account table:");
				System.out.println("--Account ID--|--Date Established--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s | %s ", resultSet.getString(1), resultSet.getString(2)));
				}
			}
			break;

		case 7: // Print the Department_Account table
			query = "SELECT * FROM Department_Account";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Department_Account table:");
				System.out.println("--Account ID--|--Details 2--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s | %s ", resultSet.getString(1), resultSet.getString(2)));
				}
			}
			break;

		case 8: // Print the Assembly_Account table
			query = "SELECT * FROM Assembly_Account";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Assembly_Account table:");
				System.out.println("--Account ID--|--Details 1--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s | %s ", resultSet.getString(1), resultSet.getString(2)));
				}
			}
			break;

		case 9: // Print the Process_Account table
			query = "SELECT * FROM Process_Account";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Process_Account table:");
				System.out.println("--Account ID--|--Details 3--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s | %s ", resultSet.getString(1), resultSet.getString(2)));
				}
			}
			break;

		case 10: // Print jobs where date_completed in not null
			query = "SELECT * FROM Job WHERE date_completed IS NOT NULL";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Job table where date_completed is not null:");
				System.out.println("--Job ID--|--Date Completed--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(

							String.format("----%s | %s ", resultSet.getString(1), resultSet.getString(3)));
				}
			}
			break;

		case 11: // Print Cut Jobs
			query = "SELECT * FROM Cut_Job";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {
				System.out.println();
				System.out.println("Contents of Cut Job table:");
				System.out.println("--Job IDs of Cut Jobs--");

				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s ", resultSet.getString(1)));
				}
			}
			break;
		default:
			System.out.println("Print Table");
			break;
		}
	}

	// Check if primary key exists
	public static boolean check_key(Connection connection, String ID, String table, String where_condition,
			int column_number) throws SQLException {
		boolean check = false;

		String query = "SELECT * FROM " + table + " " + where_condition;
		try (final Statement statement = connection.createStatement();
				final ResultSet resultSet = statement.executeQuery(query)) {

			while (resultSet.next()) {
				if (ID.equalsIgnoreCase(resultSet.getString(column_number))) {
					check = true;
				}
			}
		}

		return check;
	}

	// Selectively print a column of a table based on a condition
	public static void selective_print(Connection connection, String table, String where_condition, int column_number,
			String label) throws SQLException {

		String query = "SELECT * FROM " + table + " " + where_condition;
		try (final Statement statement = connection.createStatement();
				final ResultSet resultSet = statement.executeQuery(query)) {

			// Printing label
			System.out.println("Existing " + table + " entries");

			System.out.println(label);
			while (resultSet.next()) {
				System.out.println(String.format("----%s ", resultSet.getString(column_number)));
			}
		}
	}

	/*------------------------------------------QUERY FUNCTIONS------------------------------------------*/

	// COMPLETELY DONE: Function enters new customer: Query 1 and Query 3
	public static String enter_new_customer(Connection connection, Scanner scanner, int query_flag)
			throws SQLException {
		// Print existing customer names
		System.out.println();
		selective_print(connection, "Customer", "", 1, "--Customer Name");
		// Asking for customer name
		System.out.println();
		System.out.print("New Customer Name: ");
		String customer_name = scanner.nextLine();

		// If customer name already exists
		if (check_key(connection, customer_name, "Customer", "", 1)) {
			System.out.println();
			System.out.println("Customer Name " + customer_name + " already exists, please enter a new name");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		} else {
			// Asking for customer address
			System.out.println();
			System.out.print("Customer's address: ");
			String customer_address = scanner.nextLine();

			// Asking for customer category
			System.out.println();
			System.out.print("Customer's category (1-10): ");
			int customer_category = scanner.nextInt();
			scanner.nextLine();

			// Adding information from user into sql statement
			try (final Statement statement = connection.createStatement();) {
				statement.executeUpdate(
						"INSERT INTO Customer(customer_name, customer_address, customer_category)\n" + "VALUES ('"
								+ customer_name + "', '" + customer_address + "', '" + customer_category + "')\n");

				System.out.println();
				System.out.println("Successfully added new customer\n");

				// Print queries if flag is set
				if (query_flag == 1) {
					print_queries();
				}
			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage() + "\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1; // cycles to query 3 if customer input is incorrect
			}

		}
		return customer_name;
	}

	// COMPLETELY DONE: Function adds a new department: also part of Query 4 and 5
	public static int enter_new_department(Connection connection, Scanner scanner, int query_flag) throws SQLException {

		// Print existing department ID
		System.out.println();
		selective_print(connection, "Department", "", 1, "--Department ID");
		// Enter a department ID
		System.out.println();
		System.out.print("New Department ID: ");
		int department_ID = scanner.nextInt();
		scanner.nextLine();

		// If department name already exists
		if (check_key(connection, Integer.toString(department_ID), "Department", "", 1)) {
			System.out.println();
			System.out.println("Department ID " + department_ID + " already exists, please enter a new ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		} else {
			// Enter department data
			System.out.println();
			System.out.print("Department Data: ");
			String department_data = scanner.nextLine();

			// Adding new department into database
			try (final Statement statement = connection.createStatement();) {
				statement.executeUpdate("INSERT INTO Department(dept_ID, dept_data)\n" + "VALUES ('" + department_ID
						+ "', '" + department_data + "')\n");

				System.out.println();
				System.out.println("Successfully added new department\n");

				// Print queries if flag is set
				if (query_flag == 1) {
					print_queries();
				}

			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage() + "\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1; // cycles to query 4
			}
		}

		return department_ID;
	}

	// COMPLETELY DONE: Function adds a new assembly: Part of Query 3
	public static int enter_new_assembly(Connection connection, Scanner scanner, String customer_name, int query_flag)
			throws SQLException {

		// Print existing Assembly ID
		System.out.println();
		selective_print(connection, "Assembly", "", 1, "--Assembly ID");
		// Asking for assembly_ID
		System.out.println();
		System.out.print("New Assembly ID: ");
		int assembly_ID = scanner.nextInt();
		scanner.nextLine();

		// If department name already exists
		if (check_key(connection, Integer.toString(assembly_ID), "Assembly", "", 1)) {
			System.out.println();
			System.out.println("Department ID " + assembly_ID + " already exists, please enter a new ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		} else {
			// Asking for date_ordered
			System.out.println();
			System.out.print("Assembly Ordered (YYYY-MM-DD): ");
			String assembly_date_ordered = scanner.nextLine();

			// Asking for assembly_details
			System.out.println();
			System.out.print("Assembly Details: ");
			String assembly_details = scanner.nextLine();

			// Adding new assembly to database
			try (final Statement statement = connection.createStatement();) {
				statement.executeUpdate(
						"INSERT INTO Assembly(assembly_ID, date_ordered, assembly_details, customer_name)\n"
								+ "VALUES ('" + assembly_ID + "', '" + assembly_date_ordered + "', '" + assembly_details
								+ "', '" + customer_name + "')");
				System.out.println();
				System.out.println("Successfully added new assembly\n");

				// Print queries if flag is set
				if (query_flag == 1) {
					print_queries();
				}
			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage() + "\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1;
			}
		}

		return assembly_ID;
	}

	// COMPLETELY DONE: Function enters a new assembly: Part of Query 5
	public static int query_3(Connection connection, Scanner scanner) throws SQLException {
		int assembly_ID = 0;
		String customer_name = "";

		// Query for new or current customer for new assembly
		System.out.println();
		System.out.print("Is customer for this assembly new or currently in database (new/current): ");
		String customer_query = scanner.nextLine();

		// If customer is new, enter new customer, if current, ask for name
		if (customer_query.equalsIgnoreCase("new") || customer_query.equalsIgnoreCase("new ")) {
			// Entering new customer and getting customer_name
			customer_name = enter_new_customer(connection, scanner, 0);
		} else if (customer_query.equalsIgnoreCase("current") || customer_query.equalsIgnoreCase("customer ")) {

			// Print existing customer names
			System.out.println();
			selective_print(connection, "Customer", "", 1, "--Customer Name");

			// Asking for customer name
			System.out.println();
			System.out.print("Existing Customer's Name: ");
			customer_name = scanner.nextLine();

			// If customer name already exists
			if (check_key(connection, customer_name, "Customer", "", 1)) {

			} else {
				System.out.println();
				System.out.println("ERROR: Customer name does not exists, please pick an existing customer");

				System.out.println();
				print_queries();

				ERROR_FLAG = 1;
			}

		} else {
			System.out.println();
			System.out.println("Please enter 'new' for new customer or 'current' for current customer");

			System.out.println();
			print_queries();
			ERROR_FLAG = 1;
		}

		if (ERROR_FLAG == 0) {
			// Enter new assembly with customer name
			assembly_ID = enter_new_assembly(connection, scanner, customer_name, 1);
		}

		return assembly_ID;
	}

	// COMPLETELY DONE: Function adds new process: Part of Query 4
	public static int enter_new_process(Connection connection, Scanner scanner, int department_ID, int query_flag)
			throws SQLException {

		// Print existing Assembly ID
		System.out.println();
		selective_print(connection, "Process", "", 1, "--Process ID");
		// Asking for process ID
		System.out.println();
		System.out.print("Process ID: ");
		int process_ID = scanner.nextInt();
		scanner.nextLine();

		// If department name already exists
		if (check_key(connection, Integer.toString(process_ID), "Process", "", 1)) {
			System.out.println();
			System.out.println("Process ID " + process_ID + " already exists, please enter a new ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		} else {
			// Asking for process data
			System.out.println();
			System.out.print("Process Data: ");
			String process_data = scanner.nextLine();

			// Adding new assembly to database
			try (final Statement statement = connection.createStatement();) {
				statement.executeUpdate("INSERT INTO Process(proc_ID, process_data, dept_ID)\n" + "VALUES ('"
						+ process_ID + "', '" + process_data + "', '" + department_ID + "')");
				System.out.println();
				System.out.println("Successfully added new process\n");

				// Print queries if flag is set
				if (query_flag == 1) {
					print_queries();
				}
			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage() + "\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1;
			}
		}
		return process_ID;
	}

	// COMPLETELY DONE: Function enters a new process: Part of Query 5
	public static int query_4(Connection connection, Scanner scanner) throws SQLException {
		int process_ID = 0;
		int department_ID = 0;
		String department_query = "";
		// Query for new or current department
		System.out.println();
		System.out.print("Is department for this process new or currently in database (new/current): ");
		department_query = scanner.nextLine();

		// If department is new, enter new department, if current, ask for dept_ID
		if (department_query.equalsIgnoreCase("new") || department_query.equalsIgnoreCase("new ")) {
			department_ID = enter_new_department(connection, scanner, 0);
		} else if (department_query.equalsIgnoreCase("current") || department_query.equalsIgnoreCase("current ")) {
			// Print existing department ID
			System.out.println();
			selective_print(connection, "Department", "", 1, "--Department ID");

			// Asking for department
			System.out.println();
			System.out.print("Existing Department ID: ");
			department_ID = scanner.nextInt();
			scanner.nextLine();

			// If department ID already exists
			if (check_key(connection, Integer.toString(department_ID), "Department", "", 1)) {
				// Do nothing
			} else {
				System.out.println();
				System.out.println("ERROR: Department ID does not exists, please pick an existing department ID");

				System.out.println();
				print_queries();

				ERROR_FLAG = 1;
			}

		} else {
			System.out.println();
			System.out.println("Please enter 'new' for new department or 'current' for current department");

			System.out.println();
			print_queries();
			ERROR_FLAG = 1;
		}

		if (ERROR_FLAG == 0) {
			// Entering new process with department ID
			process_ID = enter_new_process(connection, scanner, department_ID, 1);
		}
		return process_ID;
	}

	// COMPLETELY DONE: Function adds a new account: Query 5
	public static int enter_new_account(Connection connection, Scanner scanner, String type, int type_ID,
			int query_flag) throws SQLException {
		int account_ID = 0;

		// Print existing Account ID
		System.out.println();
		selective_print(connection, "Account", "", 1, "--Account ID");
		// Asking for account ID
		System.out.println();
		System.out.print("New Account ID: ");
		account_ID = scanner.nextInt();
		scanner.nextLine();

		// If account ID already exists
		if (check_key(connection, Integer.toString(account_ID), "Account", "", 1)) {
			System.out.println();
			System.out.println("Account ID " + account_ID + " already exists, please enter a new ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		} else {
			// Asking for account date established
			System.out.println();
			System.out.print("Account Date Established (YYYY-MM-DD): ");
			String date_established = scanner.nextLine();

			if (type.equalsIgnoreCase("Department") || type.equalsIgnoreCase("Department ")) {
				// Asking for details 2
				System.out.println();
				System.out.print("Details-2: ");
				String details_2 = scanner.nextLine();

				// Adding new account to database
				try (final Statement statement = connection.createStatement();) {
					statement.executeUpdate("INSERT INTO Account(account_ID, date_established)\n" + "VALUES ('"
							+ account_ID + "', '" + date_established + "')");
					System.out.println();
					System.out.println("Successfully added new account\n");

					statement.executeUpdate("INSERT INTO Department_Account(account_ID, details_2)\n" + "VALUES ('"
							+ account_ID + "', '" + details_2 + "')");
					System.out.println("Successfully added new department account\n");

					statement.executeUpdate("UPDATE Department\n"
							+ "SET account_ID = (SELECT account_ID FROM Department_Account WHERE account_ID = "
							+ account_ID + ")\n" + "WHERE dept_ID = " + type_ID);

					System.out.println("Successfully updated department with new account\n");

					// Print queries if flag is set
					if (query_flag == 1) {
						print_queries();
					}
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage() + "\n");
					System.out.print("Please select a number corresponding to the query: ");
				}
			} else if (type.equalsIgnoreCase("Assembly") || type.equalsIgnoreCase("Assembly ")) {
				// Asking for details 1
				System.out.println();
				System.out.print("Details-1: ");
				String details_1 = scanner.nextLine();

				// Adding new account to database
				try (final Statement statement = connection.createStatement();) {
					statement.executeUpdate("INSERT INTO Account(account_ID, date_established)\n" + "VALUES ('"
							+ account_ID + "', '" + date_established + "')");
					System.out.println();
					System.out.println("Successfully added new account\n");

					statement.executeUpdate("INSERT INTO Assembly_Account(account_ID, details_1)\n" + "VALUES ('"
							+ account_ID + "', '" + details_1 + "')");
					System.out.println("Successfully added new assembly account\n");

					statement.executeUpdate("UPDATE Assembly\n"
							+ "SET account_ID = (SELECT account_ID FROM Assembly_Account WHERE account_ID = "
							+ account_ID + ")\n" + "WHERE assembly_ID = " + type_ID);

					System.out.println("Successfully updated assembly with new account\n");

					// Print queries if flag is set
					if (query_flag == 1) {
						print_queries();
					}
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage() + "\n");
					System.out.print("Please select a number corresponding to the query: ");
				}
			} else if (type.equalsIgnoreCase("Process") || type.equalsIgnoreCase("Process ")) {
				// Asking for details 3
				System.out.println();
				System.out.print("Details-3: ");
				String details_3 = scanner.nextLine();

				// Adding new account to database
				try (final Statement statement = connection.createStatement();) {
					statement.executeUpdate("INSERT INTO Account(account_ID, date_established)\n" + "VALUES ('"
							+ account_ID + "', '" + date_established + "')");
					System.out.println();
					System.out.println("Successfully added new account\n");

					statement.executeUpdate("INSERT INTO Process_Account(account_ID, details_3)\n" + "VALUES ('"
							+ account_ID + "', '" + details_3 + "')");
					System.out.println("Successfully added new process account\n");

					statement.executeUpdate("UPDATE Process\n"
							+ "SET account_ID = (SELECT account_ID FROM Process_Account WHERE account_ID = "
							+ account_ID + ")\n" + "WHERE proc_ID = " + type_ID);

					System.out.println("Successfully updated process with new account\n");

					// Print queries if flag is set
					if (query_flag == 1) {
						print_queries();
					}
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage() + "\n");
					System.out.print("Please select a number corresponding to the query: ");
				}
			}
		}

		return account_ID;
	}

	// COMPLETELY DONE: Function enters a new account
	public static int query_5(Connection connection, Scanner scanner) throws SQLException {
		int account_ID = 0;
		// Query for account type
		System.out.println();
		System.out.print("What type of account would you like (Department, Assembly, Process): ");
		String account_type = scanner.nextLine();

		if (account_type.equalsIgnoreCase("Department") || account_type.equalsIgnoreCase("Department ")) {
			String department_query = "";
			int department_ID = 0;
			// Query for new or current department
			System.out.println();
			System.out.print("Is department for this account new or currently in database (new/current): ");
			department_query = scanner.nextLine();

			// If department is new, enter new department, if current, ask for dept_ID
			if (department_query.equalsIgnoreCase("new") || department_query.equalsIgnoreCase("new ")) {
				department_ID = enter_new_department(connection, scanner, 0);
			} else if (department_query.equalsIgnoreCase("current") || department_query.equalsIgnoreCase("current ")) {
				// Print existing department ID
				System.out.println();
				selective_print(connection, "Department", "", 1, "--Department ID");

				// Asking for department id
				System.out.println();
				System.out.print("Existing Department ID: ");
				department_ID = scanner.nextInt();
				scanner.nextLine();

				// If department ID already exists
				if (check_key(connection, Integer.toString(department_ID), "Department", "", 1)) {
					// Do nothing
				} else {
					System.out.println();
					System.out.println("ERROR: Department ID does not exists, please pick an existing department ID");

					System.out.println();
					print_queries();

					ERROR_FLAG = 1;
				}

			} else {
				System.out.println();
				System.out.println("Please enter 'new' for new department or 'current' for current department");

				System.out.println();
				print_queries();
				ERROR_FLAG = 1;
			}

			if (ERROR_FLAG == 0) {
				// Entering new process with department ID
				account_ID = enter_new_account(connection, scanner, account_type, department_ID, 1);
			}

		} else if (account_type.equalsIgnoreCase("Assembly") || account_type.equalsIgnoreCase("Assembly ")) {
			String assembly_query = "";
			int assembly_ID = 0;
			// Query for new or current assembly
			System.out.println();
			System.out.print("Is assembly for this account new or currently in database (new/current): ");
			assembly_query = scanner.nextLine();

			// If assembly is new, enter new assembly, if current, ask for dept_ID
			if (assembly_query.equalsIgnoreCase("new") || assembly_query.equalsIgnoreCase("new ")) {
				// Entering in new assembly so go to query 3
				assembly_ID = query_3(connection, scanner);
			} else if (assembly_query.equalsIgnoreCase("current") || assembly_query.equalsIgnoreCase("current ")) {
				// Print existing Assembly ID
				System.out.println();
				selective_print(connection, "Assembly", "", 1, "--Assembly ID");

				// Asking for Assembly id
				System.out.println();
				System.out.print("Existing Assembly ID: ");
				assembly_ID = scanner.nextInt();
				scanner.nextLine();

				// If assembly ID already exists
				if (check_key(connection, Integer.toString(assembly_ID), "Assembly", "", 1)) {
					// Do nothing
				} else {
					System.out.println();
					System.out.println("ERROR: Assembly ID does not exists, please pick an existing assembly ID");

					System.out.println();
					print_queries();

					ERROR_FLAG = 1;
				}
			} else {
				System.out.println();
				System.out.println("Please enter 'new' for new assembly or 'current' for current assembly");

				System.out.println();
				print_queries();
				ERROR_FLAG = 1;
			}

			if (ERROR_FLAG == 0) {
				// Entering new process with department ID
				account_ID = enter_new_account(connection, scanner, account_type, assembly_ID, 1);
			}

		} else if (account_type.equalsIgnoreCase("Process") || account_type.equalsIgnoreCase("Process ")) {
			String process_query = "";
			int process_ID = 0;
			// Query for new or current process
			System.out.println();
			System.out.print("Is process for this account new or currently in database (new/current): ");
			process_query = scanner.nextLine();

			// If assembly is new, enter new assembly, if current, ask for dept_ID
			if (process_query.equalsIgnoreCase("new") || process_query.equalsIgnoreCase("new ")) {
				// Entering in new process so go to query 4
				process_ID = query_4(connection, scanner);
			} else if (process_query.equalsIgnoreCase("current") || process_query.equalsIgnoreCase("current ")) {
				// Print existing Process ID
				System.out.println();
				selective_print(connection, "Process", "", 1, "--Process ID");

				// Asking for Assembly id
				System.out.println();
				System.out.print("Existing Process ID: ");
				process_ID = scanner.nextInt();
				scanner.nextLine();

				// If prcoess ID already exists
				if (check_key(connection, Integer.toString(process_ID), "Process", "", 1)) {
					// Do nothing
				} else {
					System.out.println();
					System.out.println("ERROR: Process ID does not exists, please pick an existing process ID");

					System.out.println();
					print_queries();

					ERROR_FLAG = 1;
				}
			} else {
				System.out.println();
				System.out.println("Please enter 'new' for new process or 'current' for current process");

				System.out.println();
				print_queries();
				ERROR_FLAG = 1;
			}

			if (ERROR_FLAG == 0) {
				// Entering new process with department ID
				account_ID = enter_new_account(connection, scanner, account_type, process_ID, 1);
			}
		} else {
			System.out.println();
			System.out.println(
					"Please enter account type: 'Department' for department, 'Assembly' for Assmebly, or 'Process' for Process");

			System.out.println();
			print_queries();
		}

		return account_ID;
	}

	// COMPLETELY DONE: Function adds a new job: Query 6
	public static int enter_new_job(Connection connection, Scanner scanner, int query_flag) throws SQLException {
		int job_ID = 0;
		// Print existing job ID
		System.out.println();
		selective_print(connection, "Job", "", 1, "--Job ID");
		// Query a job_ID
		System.out.println();
		System.out.print("New Job ID: ");
		job_ID = scanner.nextInt();
		scanner.nextLine();

		// If job ID already exists
		if (check_key(connection, Integer.toString(job_ID), "Job", "", 1)) {
			System.out.println();
			System.out.println("Job ID " + job_ID + " already exists, please enter a new ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		} else {
			// Query date_commenced
			System.out.println();
			System.out.print("Date Commenced (YYYY-MM-DD): ");
			String date_commenced = scanner.nextLine();

			// Printing assembly table
			print_table(connection, 4);
			// Query assembly_ID
			System.out.println();
			System.out.print("Assembly ID: ");
			int assembly_ID = scanner.nextInt();
			scanner.nextLine();

			// Printing process table
			print_table(connection, 3);
			// Query process_ID
			System.out.println();
			System.out.print("Process ID: ");
			int process_ID = scanner.nextInt();
			scanner.nextLine();

			// Adding new job to database
			try (final Statement statement = connection.createStatement();) {
				statement.executeUpdate("INSERT INTO Job(job_ID, date_commenced, assembly_ID, proc_ID)\n" + "VALUES ('"
						+ job_ID + "', '" + date_commenced + "', '" + assembly_ID + "', '" + process_ID + "')");
				System.out.println();
				System.out.println("Successfully added new job\n");

				// Print queries if flag is set
				if (query_flag == 1) {
					print_queries();
				}
			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage() + "\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1;
			}
		}

		return job_ID;
	}

	// COMPLETELY DONE: Function adds new job type and updates job: Query 7
	public static int enter_new_job_type(Connection connection, Scanner scanner, int query_flag) throws SQLException {
		int job_ID = 0;
		int job_type_ID = 0;

		// Printing job table
		print_table(connection, 5);
		// Query a job_ID
		System.out.println();
		System.out.print("Existing Job ID of completed job: ");
		job_ID = scanner.nextInt();
		scanner.nextLine();

		// If job ID already exists
		if (check_key(connection, Integer.toString(job_ID), "Job", "", 1)) {
			// Query a date_completed of job_ID
			System.out.println();
			System.out.print("Date Completed of Job (YYYY-MM-DD): ");
			String date_completed = scanner.nextLine();

			String assembly_ID = "";
			String process_ID = "";

			// Get assembly_ID and process_ID from job_ID
			String query = "SELECT * FROM Job WHERE job_ID = " + job_ID;
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(query)) {

				resultSet.next();
				assembly_ID = resultSet.getString(4);
				process_ID = resultSet.getString(5);
			}

			// Query type of Job
			System.out.println();
			System.out.print("What is the type of job Cut, Paint, or Fit (Cut, Paint, Fit): ");
			String job_type = scanner.nextLine();

			// Query cut job information
			if (job_type.equalsIgnoreCase("Cut") || job_type.equalsIgnoreCase("Cut ")) {
				// Query machine_type
				System.out.println();
				System.out.print("Machine Type: ");
				String machine_type = scanner.nextLine();

				// Query machine_time
				System.out.println();
				System.out.print("Machine Time: ");
				Double machine_time = scanner.nextDouble();
				scanner.nextLine();

				// Query material
				System.out.println();
				System.out.print("Material: ");
				String material = scanner.nextLine();

				// Query labor_time
				System.out.println();
				System.out.print("Labor Time: ");
				Double labor_time = scanner.nextDouble();
				scanner.nextLine();

				// Adding new cut job to database
				try (final Statement statement = connection.createStatement();) {
					statement.executeUpdate("UPDATE Job\n" + "SET date_completed = '" + date_completed + "'\n"
							+ "WHERE job_ID = " + job_ID);
					System.out.println();
					System.out.println("Successfully updated job with new information\n");

					statement.executeUpdate(
							"INSERT INTO Cut_Job(job_ID, machine_type, machine_time, material, labor_time, assembly_ID, proc_ID)\n"
									+ "VALUES ('" + job_ID + "', '" + machine_type + "', '" + machine_time + "', '"
									+ material + "', '" + labor_time + "', '" + assembly_ID + "', '" + process_ID
									+ "')");
					System.out.println("Successfully added new cut-job account\n");

					// Print queries if flag is set
					if (query_flag == 1) {
						print_queries();
					}
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage() + "\n");
					System.out.print("Please select a number corresponding to the query: ");
				}

			} else if (job_type.equalsIgnoreCase("Paint") || job_type.equalsIgnoreCase("Paint ")) {
				// Query color
				System.out.println();
				System.out.print("Color: ");
				String color = scanner.nextLine();

				// Query volume
				System.out.println();
				System.out.print("Volume: ");
				Double volume = scanner.nextDouble();
				scanner.nextLine();

				// Query labor_time
				System.out.println();
				System.out.print("Labor Time: ");
				Double labor_time = scanner.nextDouble();
				scanner.nextLine();

				// Adding new cut job to database
				try (final Statement statement = connection.createStatement();) {
					statement.executeUpdate("UPDATE Job\n" + "SET date_completed = '" + date_completed + "'\n"
							+ "WHERE job_ID = " + job_ID);
					System.out.println();
					System.out.println("Successfully updated job with new information\n");

					statement.executeUpdate(
							"INSERT INTO Paint_Job(job_ID, color, volume, labor_time, assembly_ID, proc_ID)\n"
									+ "VALUES ('" + job_ID + "', '" + color + "', '" + volume + "', '" + labor_time
									+ "', '" + assembly_ID + "', '" + process_ID + "')");
					System.out.println("Successfully added new paint-job account\n");

					// Print queries if flag is set
					if (query_flag == 1) {
						print_queries();
					}
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage() + "\n");
					System.out.print("Please select a number corresponding to the query: ");
				}

			} else if (job_type.equalsIgnoreCase("Fit") || job_type.equalsIgnoreCase("Fit ")) {
				// Query labor_time
				System.out.println();
				System.out.print("Labor Time: ");
				Double labor_time = scanner.nextDouble();
				scanner.nextLine();

				// Adding new cut job to database
				try (final Statement statement = connection.createStatement();) {
					statement.executeUpdate("UPDATE Job\n" + "SET date_completed = '" + date_completed + "'\n"
							+ "WHERE job_ID = " + job_ID);
					System.out.println();
					System.out.println("Successfully updated job with new information\n");

					statement.executeUpdate(
							"INSERT INTO Fit_Job(job_ID, labor_time, assembly_ID, proc_ID)\n" + "VALUES ('" + job_ID
									+ "', '" + labor_time + "', '" + assembly_ID + "', '" + process_ID + "')");
					System.out.println("Successfully added new paint-job account\n");

					// Print queries if flag is set
					if (query_flag == 1) {
						print_queries();
					}
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage() + "\n");
					System.out.print("Please select a number corresponding to the query: ");
				}

			} else {
				System.out.println();
				System.out.println("Please enter job type: 'Cut' for Cut, 'Paint' for Paint, or 'Fit' for Fit");
				System.out.println();
				print_queries();
			}
		} else {
			System.out.println();
			System.out.println("Job ID " + job_ID + " does not exist, please enter an existing ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		}

		return job_type_ID;
	}

	// COMPLETELY DONE: Function enters new transaction: Query 8
	public static int enter_new_transaction(Connection connection, Scanner scanner, int query_flag)
			throws SQLException {

		// Print existing job ID
		System.out.println();
		selective_print(connection, "Transactions", "", 1, "--Transaction ID");
		// Query a job_ID
		System.out.println();
		System.out.print("New Transaction ID: ");
		int transaction_ID = scanner.nextInt();
		scanner.nextLine();

		// If transaction ID already exists
		if (check_key(connection, Integer.toString(transaction_ID), "Transactions", "", 1)) {
			System.out.println();
			System.out.println("Transaction ID " + transaction_ID + " does not exists, please enter an existing ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		} else {

			// Query for sup-cost
			System.out.println();
			System.out.print("Sup Cost: ");
			int sup_cost = scanner.nextInt();
			scanner.nextLine();

			// Print existing job ID
			System.out.println();
			selective_print(connection, "Job", "", 1, "--Job ID");
			// Query for job_ID
			System.out.println();
			System.out.print("Existing Job ID for transaction: ");
			int job_ID = scanner.nextInt();
			scanner.nextLine();

			// If transaction ID already exists
			if (check_key(connection, Integer.toString(transaction_ID), "Transactions", "", 1)) {
				System.out.println();
				System.out.println("Transaction ID " + transaction_ID + " exists, please enter an existing ID");

				System.out.println();
				print_queries();

				ERROR_FLAG = 1;
			} else {

				// Adding new job to database
				try (final Statement statement = connection.createStatement();) {
					statement.executeUpdate("INSERT INTO Transactions(trans_ID, sup_cost, job_ID)\n" + "VALUES ('"
							+ transaction_ID + "', '" + sup_cost + "', '" + job_ID + "')");
					System.out.println();
					System.out.println("Successfully added new transaction\n");

				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage() + "\n");
					System.out.print("Please select a number corresponding to the query: ");
					ERROR_FLAG = 1;
				}

				// Starting looping to query for accounts that are affected by transaction
				if (ERROR_FLAG == 0) {

					// Start querying for account types and IDs to update
					System.out.print(
							"What type of account(s) would you like to update (Department, Assembly, Process): ");
					String type_query = scanner.nextLine();

					while (!type_query.equalsIgnoreCase("Quit")) {

						if (type_query.equalsIgnoreCase("Department") || type_query.equalsIgnoreCase("Department ")) {
							// Print existing Department Account ID
							System.out.println();
							selective_print(connection, "Department_Account", "", 1, "--Department Account ID");

							// Query for department ID
							System.out.println();
							System.out.print("Existing Department_Account ID: ");
							int department_account_ID = scanner.nextInt();
							scanner.nextLine();

							// Update department_account
							try (final Statement statement = connection.createStatement();) {
								statement.executeUpdate("INSERT INTO Updates(account_ID, trans_ID)\n" + "VALUES ('"
										+ department_account_ID + "', '" + transaction_ID + "')");

								statement.executeUpdate("UPDATE Department_Account\n"
										+ "SET details_2 += (SELECT sup_cost FROM Transactions WHERE trans_ID = "
										+ transaction_ID + ") WHERE account_ID = " + department_account_ID);

								System.out.println();
								System.out.println("Successfully updated department account\n");

							} catch (SQLException e) {
								System.out.println("\nERROR: " + e.getMessage() + "\n");
								System.out.print("Update failed");
								break;
							}

							System.out.print(
									"What type of account would you like to update (Department, Assembly, Process, Quit): ");
							// Re-query for next account or quit
							type_query = scanner.nextLine();
						} else if (type_query.equalsIgnoreCase("Assembly")
								|| type_query.equalsIgnoreCase("Assembly ")) {
							// Print existing Assembly Account ID
							System.out.println();
							selective_print(connection, "Assembly_Account", "", 1, "--Assembly Account ID");

							// Query for assembly ID
							System.out.println();
							System.out.print("Existing Assembly_Account ID: ");
							int assembly_account_ID = scanner.nextInt();
							scanner.nextLine();

							// Update assembly_account
							try (final Statement statement = connection.createStatement();) {
								statement.executeUpdate("INSERT INTO Updates(account_ID, trans_ID)\n" + "VALUES ('"
										+ assembly_account_ID + "', '" + transaction_ID + "')");

								statement.executeUpdate("UPDATE Assembly_Account\n"
										+ "SET details_1 += (SELECT sup_cost FROM Transactions WHERE trans_ID = "
										+ transaction_ID + ") WHERE account_ID = " + assembly_account_ID);

								System.out.println();
								System.out.println("Successfully updated assembly account\n");

							} catch (SQLException e) {
								System.out.println("\nERROR: " + e.getMessage() + "\n");
								System.out.print("Update failed");
								break;
							}

							System.out.print(
									"What type of account would you like to update (Department, Assembly, Process, Quit): ");
							// Re-query for next account or quit
							type_query = scanner.nextLine();
						} else if (type_query.equalsIgnoreCase("Process") || type_query.equalsIgnoreCase("Process ")) {
							// Print existing Process Account ID
							System.out.println();
							selective_print(connection, "Process_Account", "", 1, "--Process Account ID");

							// Query for process ID
							System.out.println();
							System.out.print("Existing Process_Account ID: ");
							int process_account_ID = scanner.nextInt();
							scanner.nextLine();

							// Update process_account
							try (final Statement statement = connection.createStatement();) {
								statement.executeUpdate("INSERT INTO Updates(account_ID, trans_ID)\n" + "VALUES ('"
										+ process_account_ID + "', '" + transaction_ID + "')");

								statement.executeUpdate("UPDATE Process_Account\n"
										+ "SET details_3 += (SELECT sup_cost FROM Transactions WHERE trans_ID = "
										+ transaction_ID + ") WHERE account_ID = " + process_account_ID);

								System.out.println();
								System.out.println("Successfully updated process account\n");

							} catch (SQLException e) {
								System.out.println("\nERROR: " + e.getMessage() + "\n");
								System.out.print("Update failed");
								break;
							}

							System.out.print(
									"What type of account would you like to update (Department, Assembly, Process, Quit): ");
							// Re-query for next account or quit
							type_query = scanner.nextLine();
						} else {
							System.out.println();
							System.out.print(
									"Please 'Department' for department account, 'Assembly' for assembly account, 'Process' for process account, or 'Quit' to stop adding affected accounts");
							break;
						}

					}

					System.out.println();
					print_queries();
				}
			}
		}

		return transaction_ID;
	}

	// COMPLETELY DONE: Function retrieves cost incurred on assembly ID
	public static int query_9(Connection connection, Scanner scanner, int query_flag) throws SQLException {
		int account_ID = 0;

		// Print assembly table
		print_table(connection, 4);
		// Query for assembly ID
		System.out.println();
		System.out.print("Existing Assembly ID: ");
		int assembly_ID = scanner.nextInt();
		scanner.nextLine();

		// If Assembly ID already exists
		if (check_key(connection, Integer.toString(assembly_ID), "Assembly", "", 1)) {

			// Query for total cost on assembly ID
			try (final Statement statement = connection.createStatement();) {
				final ResultSet resultSet = statement.executeQuery("SELECT AA.details_1\n"
						+ "FROM Assembly AS A JOIN Assembly_Account AS AA ON A.account_ID = AA.account_ID\n"
						+ "WHERE A.assembly_ID = " + assembly_ID);
				resultSet.next();
				// Getting cost-incurred
				System.out.println();
				System.out.println("Cost incurred on Assembly ID " + assembly_ID + ": " + resultSet.getString(1));

				// Print queries if flag is set
				if (query_flag == 1) {
					System.out.println();
					print_queries();
				}
			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage()
						+ ", either because there is no account associated with this assembly, or there are no costs on this assembly yet\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1;
			}
		} else {
			System.out.println();
			System.out.println("Assembly ID " + assembly_ID + " does not exist, please enter an existing ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		}

		return account_ID;
	}

	// COMPLETELY DONE: Function returns total labor time:
	public static void query_10(Connection connection, Scanner scanner, int query_flag) throws SQLException {

		// Print table where date_completed is not null
		print_table(connection, 10);
		// Query for job_ID
		System.out.println();
		System.out.print("Existing Date Completed: ");
		String date_completed = scanner.nextLine();

		// Check to make sure that the date completed exists exists
		if (check_key(connection, date_completed, "Job", "WHERE date_completed IS NOT NULL", 3)) {
			// Job_ID exists in table, now check dept_ID
			// Print table where date_completed is not null
			print_table(connection, 2);
			// Query for dept_ID
			System.out.println();
			System.out.print("Department ID: ");
			String dept_ID = scanner.nextLine();

			// Check to make sure that department ID exists
			if (check_key(connection, dept_ID, "Department", "", 1)) {

				// Query for total labor time in a department
				try (final Statement statement = connection.createStatement();) {
					final ResultSet resultSet = statement.executeQuery(
							"SELECT SUM(ISNULL(C.labor_time, 0)) + SUM(ISNULL(PJ.labor_time, 0)) + SUM(ISNULL(F.labor_time, 0)) AS total_labor_time\n"
									+ "FROM Job J LEFT OUTER JOIN Cut_Job C \n"
									+ "ON J.job_ID = C.job_ID LEFT OUTER JOIN Paint_Job PJ \n"
									+ "ON J.job_ID = PJ.job_ID LEFT OUTER JOIN Fit_Job F \n"
									+ "ON J.job_ID = F.job_ID JOIN Process P \n"
									+ "ON J.proc_ID = P.proc_ID JOIN Department D \n" + "ON P.dept_ID = D.dept_ID \n"
									+ "WHERE J.date_completed = '" + date_completed + "' AND D.dept_ID = " + dept_ID);
					resultSet.next();
					// Getting cost-incurred
					System.out.println();
					System.out.println("Total labor time on Department ID " + dept_ID + ": " + resultSet.getString(1));

				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage() + "\n");
					System.out.print("Please select a number corresponding to the query: ");
					ERROR_FLAG = 1;
				}

			} else {
				// Key does not exist in table
				System.out.println();
				System.out.println(
						"Department ID chosen does not exist, please enter query 10 again and enter Department ID from table above");
			}

		} else {
			// Key does not exist in table
			System.out.println();
			System.out.println(
					"Date completed does not exist, please enter query 10 again and enter Job ID from table above");
		}

		System.out.println();
		print_queries();
	}

	// COMPLETELY DONE: Function retrieves processes through an assembly-id and
	// department responsible
	public static void query_11(Connection connection, Scanner scanner, int query_flag) throws SQLException {
		// Print existing assembly ID
		System.out.println();
		selective_print(connection, "Assembly", "", 1, "--Assembly ID");
		// Query for assembly ID
		System.out.println();
		System.out.print("Existing Assembly ID: ");
		int assembly_ID = scanner.nextInt();
		scanner.nextLine();

		// Check to make sure that the date completed exists exists
		if (check_key(connection, Integer.toString(assembly_ID), "Assembly", "", 1)) {
			// Query for total cost on assembly ID
			try (final Statement statement = connection.createStatement();) {
				final ResultSet resultSet = statement.executeQuery("SELECT J.proc_ID, J.date_commenced, D.dept_ID "
						+ "FROM Job J JOIN Process P ON J.proc_id = P.proc_ID JOIN Department D ON P.dept_ID = D.dept_ID "
						+ "WHERE J.assembly_ID = " + assembly_ID + "\n" + "ORDER BY J.date_commenced");

				System.out.println("--Process ID--|--Date Commenced--|--Department ID--");
				// Printing out table
				while (resultSet.next()) {
					System.out.println(String.format("----%s | %s | %s", resultSet.getString(1), resultSet.getString(2),
							resultSet.getString(3)));
				}

				// Print queries if flag is set
				if (query_flag == 1) {
					System.out.println();
					print_queries();
				}
			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage() + "\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1;
			}

		} else {
			System.out.println();
			System.out.println("Assembly ID " + assembly_ID + " does not exist, please enter an existing ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		}
	}

	// COMPLETELY DONE: Retrieve the jobs completed during a given date in a given
	// department
	public static void query_12(Connection connection, Scanner scanner, int query_flag) throws SQLException {
		// Print existing department ID
		System.out.println();
		selective_print(connection, "Department", "", 1, "--Department ID");
		// Enter a department ID
		System.out.println();
		System.out.print("Existing Department ID: ");
		int department_ID = scanner.nextInt();
		scanner.nextLine();

		// If department name already exists
		if (check_key(connection, Integer.toString(department_ID), "Department", "", 1)) {

			// Printing only dates
			System.out.println();
			System.out.println("Dates of completed jobs");
			selective_print(connection, "Job", "WHERE date_completed IS NOT NULL", 3, "--Date Completed");

			// Enter a date completed
			System.out.println();
			System.out.print("Existing Date Completed: ");
			String date_completed = scanner.nextLine();

			// Query for total cost on assembly ID
			try (final Statement statement = connection.createStatement();) {
				final ResultSet resultSet1 = statement.executeQuery(
						"SELECT CJ.job_ID, machine_type, machine_time, material, labor_time, CJ.assembly_ID\n"
								+ "FROM Cut_Job CJ JOIN Job J ON CJ.job_ID = J.job_ID\n"
								+ "JOIN Process P ON J.proc_id = P.proc_ID\n"
								+ "JOIN Department D ON P.dept_ID = D.dept_ID\n" + "WHERE D.dept_ID = " + department_ID
								+ " AND J.date_completed = '" + date_completed + "'");

				System.out.println();
				System.out.println("Cut_Jobs:");
				System.out.println(
						"--Job ID--|--Machine Type--|--Machine Time--|--Material--|--Labor Time--|--Assembly ID--");
				// Printing out table
				while (resultSet1.next()) {
					System.out.println(String.format("----%s | %s | %s | %s | %s | %s", resultSet1.getString(1),
							resultSet1.getString(2), resultSet1.getString(3), resultSet1.getString(4),
							resultSet1.getString(5), resultSet1.getString(6)));
				}

				final ResultSet resultSet2 = statement
						.executeQuery("SELECT PJ.job_ID, color, volume, labor_time, PJ.assembly_ID\n"
								+ "FROM Paint_Job AS PJ JOIN Job AS J ON PJ.job_ID = J.job_ID\n"
								+ "JOIN Process P ON J.proc_id = P.proc_ID\n"
								+ "JOIN Department D ON P.dept_ID = D.dept_ID\n" + "WHERE D.dept_ID = " + department_ID
								+ " AND J.date_completed =  '" + date_completed + "'");

				System.out.println();
				System.out.println("Paint_Jobs: ");
				System.out.println("--Job ID--|--Color--|--Volume--|--Labor Time--|--Assembly ID--");
				// Printing out table
				while (resultSet2.next()) {
					System.out.println(String.format("----%s | %s | %s | %s | %s", resultSet2.getString(1),
							resultSet2.getString(2), resultSet2.getString(3), resultSet2.getString(4),
							resultSet2.getString(5)));
				}

				final ResultSet resultSet3 = statement.executeQuery("SELECT FJ.job_ID, labor_time ,FJ.assembly_ID "
						+ "FROM Fit_Job FJ JOIN Job J ON FJ.job_ID = J.job_ID JOIN Process P "
						+ "ON J.proc_id = P.proc_ID JOIN Department D ON P.dept_ID = D.dept_ID " + "WHERE D.dept_ID = "
						+ department_ID + " AND J.date_completed = '" + date_completed + "'");

				System.out.println();
				System.out.println("Fit_Jobs: ");
				System.out.println("--Job ID--|--Labor Time--|--Assembly ID--");
				// Printing out table
				while (resultSet3.next()) {
					System.out.println(String.format("----%s | %s | %s", resultSet3.getString(1),
							resultSet3.getString(2), resultSet3.getString(3)));
				}

				// Print queries if flag is set
				if (query_flag == 1) {
					System.out.println();
					print_queries();
				}
			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage() + "\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1;
			}
		} else {
			System.out.println();
			System.out.println("Department ID " + department_ID + " does not exist, please enter an existing ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		}
	}

	// COMPELTELY DONE: Retrieve customer whose category is in a given range
	public static void query_13(Connection connection, Scanner scanner, int query_flag) throws SQLException {
		// Query for category number
		System.out.println();
		System.out.print("First Category Number: ");
		int category_1 = scanner.nextInt();
		scanner.nextLine();
		System.out.println();
		System.out.print("Second Category Number: ");
		int category_2 = scanner.nextInt();
		scanner.nextLine();

		// Query for total cost on assembly ID
		try (final Statement statement = connection.createStatement();) {
			final ResultSet resultSet = statement
					.executeQuery("SELECT customer_name, customer_address, customer_category\n" + "FROM Customer\n"
							+ "WHERE customer_category BETWEEN " + category_1 + " AND " + category_2 + "\n"
							+ "ORDER BY (customer_name)");

			System.out.println("--Customer Name--|--Customer Address--|--Category Number--");
			// Printing out table
			while (resultSet.next()) {
				System.out.println(String.format("----%s | %s | %s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3)));
			}

			// Print queries if flag is set
			if (query_flag == 1) {
				System.out.println();
				print_queries();
			}
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage() + "\n");
			System.out.print("Please select a number corresponding to the query: ");
			ERROR_FLAG = 1;
		}
	}

	// COMPLETELY DONE: Delete all cut jobs whose job no is in a given range
	public static void query_14(Connection connection, Scanner scanner, int query_flag) throws SQLException {
		// Print all cut jobs
		print_table(connection, 11);

		// Query for range
		System.out.println();
		System.out.print("First Range Number: ");
		int range_1 = scanner.nextInt();
		scanner.nextLine();
		System.out.println();
		System.out.print("Second Range Number: ");
		int range_2 = scanner.nextInt();
		scanner.nextLine();

		// Deleting Cut jobs in range
		try (final Statement statement = connection.createStatement();) {
			statement.executeUpdate("DELETE Cut_Job\n" + "WHERE Cut_Job.job_ID BETWEEN " + range_1 + " AND " + range_2);

			System.out.println("Deleted Cut Jobs in range " + range_1 + " and " + range_2);

			// Print queries if flag is set
			if (query_flag == 1) {
				System.out.println();
				print_queries();
			}
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage() + "\n");
			System.out.print("Please select a number corresponding to the query: ");
			ERROR_FLAG = 1;
		}
	}

	// COMPLETELY DONE Change color of a given paint job
	public static void query_15(Connection connection, Scanner scanner, int query_flag) throws SQLException {

		// Print existing Paint_Job ID
		System.out.println();
		selective_print(connection, "Paint_Job", "", 1, "--Paint_Job ID");
		// Asking for Paint_Job ID
		System.out.println();
		System.out.print("New Paint_Job ID: ");
		int job_ID = scanner.nextInt();
		scanner.nextLine();

		// If job ID already exists
		if (check_key(connection, Integer.toString(job_ID), "Paint_Job", "", 1)) {
			// Asking for new Paint_Job color
			System.out.println();
			System.out.print("New Color: ");
			String color = scanner.nextLine();

			// Deleting Cut jobs in range
			try (final Statement statement = connection.createStatement();) {
				statement.executeUpdate(
						"UPDATE Paint_Job\n" + "SET color = '" + color + "'\n" + "WHERE Paint_Job.job_ID = " + job_ID);

				System.out.println();
				System.out.println("Updated Paint_Job " + job_ID + " to new color");

				// Print queries if flag is set
				if (query_flag == 1) {
					System.out.println();
					print_queries();
				}
			} catch (SQLException e) {
				System.out.println("\nERROR: " + e.getMessage() + "\n");
				System.out.print("Please select a number corresponding to the query: ");
				ERROR_FLAG = 1;
			}
		} else {
			System.out.println();
			System.out.println("Paint_Job ID " + job_ID + " does not exist, please enter an existing ID");

			System.out.println();
			print_queries();

			ERROR_FLAG = 1;
		}

	}

	// COMPLETELY DONE: Import new customers
	public static void query_16(Connection connection, Scanner scanner, int query_flag)
			throws SQLException, IOException {

		// Query for file path
		System.out.println();
		System.out.print("Please enter the file path: ");
		String file_path = scanner.nextLine();

		// Attaching src to file name
		file_path = "src/" + file_path;

		// Executing statements when reading in file lines
		try (final Statement statement = connection.createStatement();) {
			// File line
			try {
				// Declaring variable
				String file_line = "";
				File f = new File(file_path);
				FileReader fr = new FileReader(f);
				// Reading in file lines
				BufferedReader buffer = new BufferedReader(fr);

				try {
					// Reading in line by line
					while ((file_line = buffer.readLine()) != null) {
						// Split string on commas
						String[] customer = file_line.split(",");

						statement.executeUpdate(
								"INSERT INTO Customer(customer_name, customer_address, customer_category)\n"
										+ "VALUES ('" + customer[0] + "','" + customer[1] + "','" + customer[2] + "')");
					}

					System.out.println("Successfully added new customers");

				} catch (SQLException e) {
					System.out.println();
					System.out.println("ERROR: " + e.getMessage() + ", 1");
					System.out.println();
					System.out.print("Please select a number corresponding to the query: ");
				} finally {
					try {
						// Close buffer
						buffer.close();
					} catch (IOException e1) {
						System.out.println();
						System.out.println("ERROR: " + e1.getMessage());
						System.out.println();
						System.out.print("Please select a number corresponding to the query: ");
					}
				}

				// Print out queries
				if (query_flag == 1) {
					System.out.println();
					print_queries();
				}
			} catch (FileNotFoundException e) {
				System.out.println();
				System.out.println("ERROR: " + e.getMessage());
				System.out.println();
				System.out.print("Please select a number corresponding to the query: ");
			}
		}

	}

	// COMPLETELY DONE: Export all customers to new file
	public static void query_17(Connection connection, Scanner scanner, int query_flag)
			throws SQLException, IOException {

		// Query for range 1
		System.out.println();
		System.out.print("Enter range start: ");
		int range_1 = scanner.nextInt();
		scanner.nextLine();

		// Query for range 2
		System.out.println();
		System.out.print("Enter range end: ");
		int range_2 = scanner.nextInt();

		scanner.nextLine();
		// Query for output file
		System.out.println();
		System.out.print("Enter output file name: ");
		String file_output = scanner.nextLine();

		try (final Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement
					.executeQuery("SELECT * " + "FROM Customer\n" + "WHERE customer_category BETWEEN " + range_1
							+ " AND " + range_2 + "\n" + "ORDER BY customer_name");

			FileWriter wrt = new FileWriter(file_output + ".csv");
			StringBuilder builder = new StringBuilder();
			File output = new File(file_output + ".csv");

			while (resultSet.next()) {
				if (resultSet.isBeforeFirst()) {
					builder.append(resultSet.getString(1));
					builder.append(",");
					builder.append(resultSet.getString(2));
					builder.append(",");
					builder.append(resultSet.getString(3));
					builder.append("\n");
				} else {
					builder.append(resultSet.getString(1));
					builder.append(",");
					builder.append(resultSet.getString(2));
					builder.append(",");
					builder.append(resultSet.getString(3));
					builder.append("\n");
				}
			}

			wrt.append(builder);
			wrt.flush();
			wrt.close();

			System.out.println();
			System.out
					.println("Successfully added all customers in category range to file: " + output.getAbsolutePath());

			// Print out queries
			if (query_flag == 1) {
				System.out.println();
				print_queries();
			}

		} catch (SQLException e) {
			System.out.println();
			System.out.println("ERROR: " + e.getMessage() + ", 1");
			System.out.println();
			System.out.print("Please select a number corresponding to the query: ");
		}
	}

	// All of the SQL and querying for each option
	public static void queries(String url) throws SQLException, IOException {

		try (final Connection connection = DriverManager.getConnection(url)) {
			// Successful connection printout
			final String schema = connection.getSchema();
			System.out.println("Successful connection - Schema:" + schema);

			// Printing out header
			System.out.println("WELCOME TO THE JOB-SHOP ACCOUNTING DATABASE SYSTEM");
			System.out.println("==================================================");
			// Print out query options
			print_queries();
			// Scanner to take in input
			Scanner scanner = new Scanner(System.in);

			int usr_input = 0;
			// Loop until user has quit the program
			while (scanner.hasNext()) {

				// Take in user input for option
				usr_input = scanner.nextInt();
				scanner.nextLine();

				// Using switch for different query options
				switch (usr_input) {

				case 1: // Query 1
					enter_new_customer(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 2: // Query 2
					enter_new_department(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 3: // Query 3
					query_3(connection, scanner);
					ERROR_FLAG = 0;
					break;
				case 4: // Query 4
					query_4(connection, scanner);
					ERROR_FLAG = 0;
					break;
				case 5: // Query 5
					query_5(connection, scanner);
					ERROR_FLAG = 0;
					break;
				case 6: // Query 6
					enter_new_job(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 7: // Query 7
					enter_new_job_type(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 8: // Query 8
					enter_new_transaction(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 9: // Query 9
					query_9(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 10: // Query 10
					query_10(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 11: // Query 11
					query_11(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 12:
					query_12(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 13:
					query_13(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 14:
					query_14(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 15:
					query_15(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 16:
					query_16(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 17:
					query_17(connection, scanner, 1);
					ERROR_FLAG = 0;
					break;
				case 18:
					System.out.println("You have exited the program");
					System.exit(0);

				default: // if the user did not input a valid number

					System.out.println();
					System.out.println("Please enter a number from 1 to 18\n");
					print_queries();
					System.out.println();
					System.out.print("Please select the number corresponding to the query: ");
					// Print out query options
					continue;

				}
			}
		}
	}

	public static void signin() throws SQLException, IOException {
		// Connect to database
		final String hostName = "113380402-sql-server.database.windows.net";
		final String dbName = "cs-dsa-4513-sql-db";
		final String user = "ZAKI-113380402";
		final String password = "Sims31997^";
		final String url = String.format(
				"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;host NameInCertificate=*.database.windows.net;loginTimeout=30;",
				hostName, dbName, user, password);

		// Handle queries
		queries(url);
	}

	public static void main(String[] args) throws SQLException, IOException {
		signin();
	}
}