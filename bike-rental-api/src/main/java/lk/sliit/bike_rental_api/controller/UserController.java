package lk.sliit.bike_rental_api.controller;


import lk.sliit.bike_rental_api.models.AdminUser;
import lk.sliit.bike_rental_api.models.User;
import lk.sliit.bike_rental_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        user.setUserID(UUID.randomUUID().toString());
        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        return ResponseEntity.ok(userService.login(username, password));
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

    @PostMapping("/set-role")
    @ResponseBody
    public ResponseEntity<String> setUserRole(@RequestParam String userId, @RequestParam String userType) {
        User user = userService.findByUsernameOrId(userId);
        if (user != null) {
            user.setUserType(userType);
            userService.updateUser(user);
            return ResponseEntity.ok("Role updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
