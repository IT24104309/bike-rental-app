package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.AdminUser;
import lk.sliit.bike_rental_api.service.AdminService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AdminUser> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        return ResponseEntity.ok(adminService.login(email, password));
    }

    @GetMapping
    public ResponseEntity<List<AdminUser>> getAll() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getOne(@PathVariable String id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AdminUser admin) {
        adminService.createAdmin(admin);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody AdminUser admin) {
        adminService.updateAdmin(admin);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }
}
