package model;
import java.time.LocalDate;

public class Task {
	private int id;
	private String name;
	private LocalDate duedate;
	private String taskstatus;
	
	
	public Task(String name, LocalDate duedate, String taskstatus) {
		super();
		this.name = name;
		this.duedate = duedate;
		this.taskstatus = taskstatus;
	}
	public Task(int id, String name, LocalDate duedate, String taskstatus) {
		super();
		this.id = id;
		this.name = name;
		this.duedate = duedate;
		this.taskstatus = taskstatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void name(String name) {
		this.name =name;
	}
	public LocalDate getDuedate() {
		return duedate;
	}
	public void setDuedate(LocalDate duedate) {
		this.duedate = duedate;
	}
	public String getTaskstatus() {
		return taskstatus;
	}
	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}
	
}
