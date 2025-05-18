package lk.sliit.bike_rental_api.controller;


import lk.sliit.bike_rental_api.models.User;
import lk.sliit.bike_rental_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setUserID(UUID.randomUUID().toString());
        userService.registerUser(user);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findByUsernameOrId(username);
        if (user != null && user.getPassword().equals(password)) {
            if ("Admin".equalsIgnoreCase(user.getUserType())) {
                return "redirect:/admin";
            } else {
                model.addAttribute("user", user);
                return "user-home";
            }
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "subscription";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
