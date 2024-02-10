package com.jwtAuthLibrary.jwtBookAuthor.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jwtAuthLibrary.jwtBookAuthor.enumerate.Permission.*;

@RequiredArgsConstructor
public enum Roles {
    SUPER_ADMIN(
            Set.of(
                    SUPER_ADMIN_CREATE,
                    SUPER_ADMIN_READ,
                    SUPER_ADMIN_UPDATE,
                    SUPER_ADMIN_DELETE,
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE
            )
    ),
    USER(
            Set.of(
                    USER_READ
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthority(){
        var authority = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authority.add(new SimpleGrantedAuthority("ROLE_" +this.name()));
        return authority;
    }
}
