package hoang.luan.store.service;




import hoang.luan.store.model.Role;
import hoang.luan.store.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role getDefaultRole() {
        String roleName = "ROLE_STAFF";
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
    }
    public Role geRoleById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
    }
    public List<Role> getAll(){
        return roleRepository.findAll();
    }
}
