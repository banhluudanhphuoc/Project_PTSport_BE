package online.ptsports.PTSports.Service.IMPL;



import online.ptsports.PTSports.Entity.Role;
import online.ptsports.PTSports.Repository.RoleRepo;
import online.ptsports.PTSports.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;
    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
