package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	// display list of employees
	@GetMapping("/")
	public String viewProjectPage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewProjectForm")
	public String showNewProjectForm(Model model) {
		// create model attribute to bind form data
		Project project = new Project();
		model.addAttribute("project", project);
		return "new_project";
	}
	
	@PostMapping("/saveProject")
	public String saveProject(@ModelAttribute("project")  Project project) {
		// save employee to database
		projectService.saveProject(project);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get project from the service
		Project project = projectService.getProjectById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("project", project);
		return "update_project";
	}
	
	@GetMapping("/deleteProject/{id}")
	public String deleteProject(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.projectService.deleteProjectById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 10;
		
		Page<Project> page = projectService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Project> listProjects = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listProjects", listProjects);
		return "index";
	}
}
