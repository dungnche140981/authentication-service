package vn.edu.fpt.authentication.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * vn.edu.fpt.authentication.filter
 *
 * @author : Portgas.D.Ace
 * @created : 05/03/2023
 * @contact : 0339850697- congdung2510@gmail.com
 **/
@Order
@Component
public class TraceFilter extends OncePerRequestFilter {

    @Autowired
    Tracer tracer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = request.getHeader("traceId");
        if(Objects.isNull(traceId)){
           response.setHeader("x-trace-id",tracer.currentSpan().context().traceId());
        }
        filterChain.doFilter(request, response);
    }
}
