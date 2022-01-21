package com.obo.oborestfulapp.bootstrap;

import com.obo.oborestfulapp.domain.Role;
import com.obo.oborestfulapp.domain.User;
import com.obo.oborestfulapp.repositories.RoleRepository;
import com.obo.oborestfulapp.repositories.UserRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // Create user roles
        var userRole = createRoleIfNotFound(Role.ROLE_USER);
        var adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);

        // Create users
        createUserIfNotFound("user", userRole);
        createUserIfNotFound("admin", adminRole);

        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    User createUserIfNotFound(final String name, final Role role) {
        Optional<User> optionalUser = userRepository.findByUsername(name);

        User user = null;
        if (optionalUser == null) {
            user = new User(name, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            user = userRepository.save(user);
        }
        return user;
    }
}
