package gr9.eventmarket.database.model;

import java.util.Optional;

public enum ERole {
    ROLE_USER("user"),
    ROLE_MOD("mod");
    //ROLE_ADMIN("admin");

    private final String name;

    private ERole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<ERole> getRoleByName(String name) {
        name = name.toLowerCase();
        for (var role : values()) {
            if (role.name.equals(name)) {
                return Optional.of(role);
            }
        }
        return Optional.empty();
    }

}
