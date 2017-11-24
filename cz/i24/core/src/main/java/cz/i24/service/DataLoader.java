package cz.i24.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import cz.i24.entity.Privilege;
import cz.i24.entity.Role;
import cz.i24.entity.User;

@Component
public class DataLoader {

    public DataLoader(UserService userService) {

        List<Privilege> privileges = Arrays.asList(new Privilege("readProduct"), new Privilege("createCustomer"), new Privilege("readCustomer"),
                new Privilege("updateCustomer"), new Privilege("deleteCustomer"));

        Role role = new Role(1L, "ROLE_ADMIN");
        role.setPrivileges(privileges);
        userService.save(new User(0L, "test", "test@i24.cz", "test", role));
    }
}
