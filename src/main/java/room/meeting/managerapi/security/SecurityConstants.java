package room.meeting.managerapi.security;

public class SecurityConstants {
    public static final String API_URL       = "/api/v2";
    public static final String SECRET        = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX  = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String REGISTER_URL  = API_URL.concat("/register");
    public static final String SIGN_UP_URL   = API_URL.concat("/login");
    public static final String PUBLIC_URL    = API_URL.concat("/public");
    public static final String STATUS_URL    = API_URL.concat("/status");
    public static final long EXPIRATION_TIME = 86_400_000;
}
