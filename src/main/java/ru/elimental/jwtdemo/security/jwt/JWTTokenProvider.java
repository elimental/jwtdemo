package ru.elimental.jwtdemo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.elimental.jwtdemo.model.Role;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JWTTokenProvider {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${jwt.token.expired}")
    private long validityTime;
    
    private final SecretKey secretKey;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JWTTokenProvider(@Qualifier(value = "JWT") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.secretKey = Keys.secretKeyFor(SIGNATURE_ALGORITHM);
    }

    public String createToken(String userName, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", getRoleNames(roles));
        Date now = new Date();
        Date validityDate = new Date(now.getTime() + validityTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validityDate)
                .signWith(secretKey, SIGNATURE_ALGORITHM)
                .compact();

    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JWTAuthenticationException("JWT token is expired or invalid");
        }
    }

    private List<String> getRoleNames(List<Role> userRoles) {
        List<String> roles = new ArrayList<>();
        userRoles.forEach(role -> {
            roles.add(role.getName());
        });
        return roles;
    }
}
