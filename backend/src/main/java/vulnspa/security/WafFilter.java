package vulnspa.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WafFilter implements Filter {

    @Value("${waf.level:BASIC}")
    private String wafLevelStr;

    private WafStrategy strategy;

    @Override
    public void init(FilterConfig filterConfig) {
        WafLevel level;
        try {
            level = WafLevel.valueOf(wafLevelStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            level = WafLevel.BASIC;
        }

        switch (level) {
            case NONE -> strategy = new NoneWafStrategy();
            case BASIC -> strategy = new BasicWafStrategy();
            case ADVANCED -> strategy = new AdvancedWafStrategy();
        }

        System.out.println("[WAF] Initialized with level: " + level);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String uri = httpReq.getRequestURI();

        if (uri != null && uri.startsWith("/static/waf_blocked.html")) {
            chain.doFilter(request, response);
            return;
        }

        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpReq);

        System.out.println("[WAF] Incoming request method: " + httpReq.getMethod());
        System.out.println("[WAF] Incoming request URI: " + httpReq.getRequestURI());

        if (strategy.isMalicious(cachedRequest)) {
            System.out.println("[WAF] Заблокирован подозрительный запрос: " + uri);

            if (uri.startsWith("/api/")) {
                httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResp.setContentType("application/json");
                httpResp.setCharacterEncoding("UTF-8");
                httpResp.getWriter().write("{\"error\": \"WAF: Blocked for security reason\"}");
                httpResp.flushBuffer();
                return;
            } else {
                httpResp.sendRedirect("/static/waf_blocked.html");
            }
            return;
        }

        chain.doFilter(cachedRequest, response);
    }

    @Override
    public void destroy() {
        // no-op
    }
}
