package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Task;
import java.time.LocalDate;
public class TaskDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "password";

	private static final String INSERT_TASK_SQL = "INSERT INTO todolist.task" + "  (name, duedate, taskstatus) VALUES "
			+ " (?, ?, ?);";

	private static final String SELECT_TASK_BY_ID = "select id,name,duedate,taskstatus from todolist.task where id =?";
	private static final String SELECT_ALL_TASK = "select * from todolist.task";
	private static final String DELETE_TASK_SQL = "delete from todolist.task where id = ?;";
	private static final String UPDATE_TASK_SQL = "update todolist.task set name = ?,duedate= ?, taskstatus =? where id = ?;";

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public void insertTask(Task task) throws SQLException {
		System.out.println(INSERT_TASK_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_SQL)) {
			preparedStatement.setString(1, task.getName());
			preparedStatement.setDate(2, getSQLDate(task.getDuedate()));
			preparedStatement.setString(3, task.getTaskstatus());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	public boolean updateTask(Task task) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_TASK_SQL);) {
			statement.setString(1, task.getName());
			statement.setDate(2, getSQLDate(task.getDuedate()));
			statement.setString(3, task.getTaskstatus());
			statement.setInt(4, task.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	public boolean deleteTask(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_TASK_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	public Task selectTask(int id) {
		Task task = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				LocalDate duedate = rs.getDate("duedate").toLocalDate();
				String taskstatus = rs.getString("taskstatus");
				task = new Task(id, name, duedate, taskstatus);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return task;
	}

	public List<Task> selectAllTask() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Task> tasks = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASK);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				LocalDate duedate = rs.getDate("duedate").toLocalDate();
				String taskstatus = rs.getString("taskstatus");
				tasks.add(new Task(id, name, duedate, taskstatus));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return tasks;
	}
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
	
	public Date getSQLDate(LocalDate date)
	{
		return java.sql.Date.valueOf(date);
	}
	
	public LocalDate getUtilDate(Date sqlDate) {
		
		return sqlDate.toLocalDate();
	}
}
