package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Employee;

public interface EmployeeService {
	List<Projects> getAllProjects();
	void saveProject(Project project);
	Project getProjectById(long id);
	void deleteProjectById(long id);
	Page<Project> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
