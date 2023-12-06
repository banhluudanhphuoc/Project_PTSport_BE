package online.ptsports.PTSports.Controller.AdminApi;




import online.ptsports.PTSports.Entity.Role;
import online.ptsports.PTSports.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@CrossOrigin
public class RoleAdminController {
    @Autowired

    private RoleService roleService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("roles")
    public List<Role> getAllRolesController() {

        return roleService.getAllRoles();
    }
}
