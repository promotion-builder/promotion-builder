package kr.njw.promotiondisplay.common.security;

public enum Role {
    GUEST("GUEST"),
    USER("USER"),
    ADMIN("ADMIN");

    public static final String AUTHORITY_PREFIX = "ROLE_";

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toAuthority() {
        return AUTHORITY_PREFIX + this.value;
    }
}
