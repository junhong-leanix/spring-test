package net.leanix.springtest.rest;

import java.io.IOException;
import java.security.Principal;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;

@Component
public class TestFilter implements Filter {

    @Override
    public void doFilter(
        ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {
        System.out.println("doing filter!");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        filterChain.doFilter(new UserRoleRequestWrapper("ABC", req), servletResponse);
    }

    public static class UserRoleRequestWrapper extends HttpServletRequestWrapper {

        String user;
        HttpServletRequest realRequest;

        public UserRoleRequestWrapper(String user, HttpServletRequest request) {
            super(request);
            this.user = user;
            this.realRequest = request;
        }

        @Override
        public Principal getUserPrincipal() {
            if (this.user == null) {
                return realRequest.getUserPrincipal();
            }

            // make an anonymous implementation to just return our user
            return new CustomPrincipal(user);
        }
    }

    public static class CustomPrincipal implements Principal {

        private final String name;

        public CustomPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
