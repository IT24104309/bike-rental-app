package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.AdminUser;
import lk.sliit.bike_rental_api.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin-user-management")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public AdminUser login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        return adminService.login(email, password);
    }

    @GetMapping
    public List<AdminUser> getAll() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminUser getOne(@PathVariable String id) {
        return adminService.getAdminById(id);
    }

    @PostMapping
    public void create(@RequestBody AdminUser admin) {
        adminService.createAdmin(admin);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody AdminUser admin) {
        adminService.updateAdmin(admin);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        adminService.deleteAdmin(id);
    }
}
