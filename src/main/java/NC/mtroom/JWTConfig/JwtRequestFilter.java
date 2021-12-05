package NC.mtroom.JWTConfig;

import NC.mtroom.JWTConfig.Service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String login = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer_")) {
            jwtToken = requestTokenHeader.substring(7);

            try{
                login = jwtTokenUtil.getLoginFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Не удаётся получить JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token истёк");
            }
        }

        if(login != null && SecurityContextHolder.getContext().getAuthentication() == null){

            CustomUserDetails customUserDetails= this.jwtUserDetailsService.loadUserByUsername(login);

            if (jwtTokenUtil.validateToken(jwtToken,customUserDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        customUserDetails,null,customUserDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}
