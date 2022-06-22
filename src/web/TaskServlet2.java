package web;

import java.io.IOException;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Task;
import dao.TaskDAO;
import java.time.LocalDate;

/**
 * Servlet implementation class TodolistServlet
 */
//@WebServlet("/TodolistServlet")
@WebServlet("/")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    public TaskServlet() {
        this.taskDAO = new TaskDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	        doGet(request, response);
    	    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Hello world: ").append(request.getContextPath());
	    String action = request.getServletPath();

	    try {
	    	switch (action) {
	    		
	    	     		case "/new":
		                    showNewForm(request, response);
		                    break;	    	     	
	    	        	case "/insert":
	    	                	insertTask(request, response);	    	        		
	    	                    break;
	    	                case "/delete":
	    	                    deleteTask(request, response);
	    	                    break;
	    	                case "/edit":
	    	                    showEditForm(request, response);
	    	                    break;
	    	                case "/update":
	    	                    updateTask(request, response);
	    	                    break;
	    	                default:
	    	                    listTask(request, response);
	    	                    break;
	    	            }
	    	        } catch (SQLException ex) {
	    	            throw new ServletException(ex);
	    	        }
	    }
		private void listTask(HttpServletRequest request, HttpServletResponse response)
    	    throws SQLException, IOException, ServletException {
    	        List < Task > listTask = taskDAO.selectAllTask();
    	        request.setAttribute("listTask", listTask);
    	        RequestDispatcher dispatcher = request.getRequestDispatcher("task-list.jsp");
    	        dispatcher.forward(request, response);
    	        
    	        
    	    }

    	    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {    	    	
    	        RequestDispatcher dispatcher = request.getRequestDispatcher("task-form.jsp");
    	        dispatcher.forward(request, response);
    	    }
    	    
    	    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    	    	    throws SQLException, ServletException, IOException {
    	    	        int id = Integer.parseInt(request.getParameter("id"));
    	    	        Task existingTask = taskDAO.selectTask(id);
    	    	        RequestDispatcher dispatcher = request.getRequestDispatcher("task-form.jsp");
    	    	        request.setAttribute("task", existingTask);
    	    	        dispatcher.forward(request, response);

    	    	    }
    	    private void insertTask(HttpServletRequest request, HttpServletResponse response)
    	    	    throws SQLException, IOException {
    	    	        String name = request.getParameter("name");
    	    	        LocalDate duedate = LocalDate.parse(request.getParameter("duedate"));
    	    	        String taskstatus = request.getParameter("taskstatus");
    	    	        Task newTask = new Task(name, duedate, taskstatus);
    	    	        taskDAO.insertTask(newTask);
    	    	        response.sendRedirect("list");
    	    	    }
    	    private void updateTask(HttpServletRequest request, HttpServletResponse response)
    	    	    throws SQLException, IOException {
    	    	        int id = Integer.parseInt(request.getParameter("id"));
    	    	        String name = request.getParameter("name");
    	    	        LocalDate duedate = LocalDate.parse(request.getParameter("duedate"));
    	    	        String taskstatus = request.getParameter("taskstatus");

    	    	        Task task = new Task(id, name, duedate, taskstatus);
    	    	        taskDAO.updateTask(task);
    	    	        response.sendRedirect("list");
    	    	    }
    	    private void deleteTask(HttpServletRequest request, HttpServletResponse response)
    	    	    throws SQLException, IOException {
    	    	        int id = Integer.parseInt(request.getParameter("id"));
    	    	        taskDAO.deleteTask(id);
    	    	        response.sendRedirect("list");

    	    	    }
    	    	


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */



