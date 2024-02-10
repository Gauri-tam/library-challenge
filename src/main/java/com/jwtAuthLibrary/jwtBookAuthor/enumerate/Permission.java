package com.jwtAuthLibrary.jwtBookAuthor.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    SUPER_ADMIN_CREATE("create"),
    SUPER_ADMIN_READ("read"),
    SUPER_ADMIN_UPDATE("update"),
    SUPER_ADMIN_DELETE("delete"),
    ADMIN_CREATE("create"),
    ADMIN_READ("read"),
    ADMIN_UPDATE("update"),
    ADMIN_DELETE("delete"),
    USER_READ("read");

    @Getter
    private final String permission;
}
