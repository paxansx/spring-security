package web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class UserController {
	private final UserService service ;

	public UserController(UserService service) {
		this.service = service;

	}


	@GetMapping("/user")
	public String getUserInfo(Model model1, Authentication authentication) {
		model1.addAttribute("user", service.loadUserByUsername(authentication.getName()));
		return "user";
	}

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String usersPage(Model model){
		model.addAttribute("users", service.getAllUser());
		return "users";
	}

	@GetMapping("/admin/userAdd")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String  usersAdd(Model model, @ModelAttribute("user") User user){
		model.addAttribute("newUser", new User());
		model.addAttribute("allRoles", service.getAllRole());
		return "/userAdd";
	}

	@PostMapping("/admin/add")
	public String  usersAdd(@ModelAttribute("user") User user, @RequestParam("role") String[] roles) {

		user.setRoles(service.getSetRole(roles));
		service.addUser(user);
		return "redirect:/admin";
	}


	@GetMapping("/admin/userupdate/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String updateUser(Model model, @PathVariable("id") long id){
		model.addAttribute("allRoles", service.getAllRole());
		model.addAttribute("user", service.getUserById(id));
		return "/userUpdate";
	}
	@PostMapping("/admin/update")
	public String  update (@ModelAttribute("user") User user, @RequestParam("role") String[] roles) {
		user.setRoles(service.getSetRole(roles));
		service.updateUser(user);
		return "redirect:/admin";
	}

	@PostMapping("/admin/remove/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String removeUser(@PathVariable("id")long id){
		service.removeUser(id);
		return "redirect:/admin";
	}


}