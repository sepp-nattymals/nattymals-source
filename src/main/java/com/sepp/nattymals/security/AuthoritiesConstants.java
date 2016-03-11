package com.sepp.nattymals.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMINISTRATOR";

    public static final String USER = "ROLE_PETOWNER";
    
    public static final String VETERINARIAN = "ROLE_VETERINARIAN";
    
    public static final String PETCOMPANY = "ROLE_PETCOMPANY";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
