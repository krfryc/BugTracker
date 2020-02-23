package pl.kfryc.bugtracker.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pl.kfryc.bugtracker.entity.AuthUserDetails;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserChangeInterceptor implements HandlerInterceptor {

    private UserService userService;

    public static Set<String> users_to_update = new HashSet<>();


    @Autowired
    public UserChangeInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            if (!users_to_update.isEmpty()) {
                AuthUserDetails user = (AuthUserDetails) auth.getPrincipal();

                String username = user.getUser().getEmail();
                if (users_to_update.contains(username)) {
                    // Gets the updated roleID saved (if somebody else has changed)
                    User updatedUser = userService.findByEmail(username);
                    updateRoles(auth, updatedUser);
                    users_to_update.remove(username);
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            // if something will ever go wrong
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void updateRoles(Authentication auth, User user) {
        AuthUserDetails authUserDetails = (AuthUserDetails) auth.getPrincipal();
        authUserDetails.setUser(user);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(authUserDetails,
                auth.getCredentials(), auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

    }


}
