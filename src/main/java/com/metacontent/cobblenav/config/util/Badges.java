package com.metacontent.cobblenav.config.util;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Badges {
    private final Set<Badge> badges;

    public Badges(Set<Badge> badges) {
        this.badges = badges;
    }

    public Badges() {
        this.badges = new HashSet<>();
    }

    @Nullable
    public Badge getByType(String type) {
        return badges.stream().filter(badge -> badge.type().equals(type)).findFirst().orElse(null);
    }

    @Nullable
    public Badge getByPermission(String permission) {
        return badges.stream().filter(badge -> badge.permissionToGrant().equals(permission)).findFirst().orElse(null);
    }

    public Set<String> getTypes() {
        return badges.stream().map(Badge::type).collect(Collectors.toSet());
    }

    public Set<String> getPermissions() {
        return badges.stream().map(Badge::permissionToGrant).collect(Collectors.toSet());
    }
}
