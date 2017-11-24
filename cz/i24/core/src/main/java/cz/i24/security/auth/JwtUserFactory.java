package cz.i24.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cz.i24.entity.Role;
import cz.i24.entity.User;

public final class JwtUserFactory {

    private JwtUserFactory() {}

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                getAuthoritiess(user.getRole())
                );
    }

    private static Collection<? extends GrantedAuthority> getAuthoritiess(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getLabel()));
        role.getPrivileges().stream()
        .map(p -> new SimpleGrantedAuthority(p.getLabel()))
        .forEach(authorities::add);
        return authorities;
    }

}
