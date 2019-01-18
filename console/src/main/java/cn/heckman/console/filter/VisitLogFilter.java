package cn.heckman.console.filter;

import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.moduleservice.entity.VisitLog;
import cn.heckman.moduleservice.service.VisitLogService;
import com.auth0.jwt.interfaces.Claim;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 用户访问日志收集过滤器
 */
public class VisitLogFilter implements Filter {

//    @Autowired
//    private VisitLogService visitLogService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        System.out.println(SpringContextUtils.getApplicationContext());
//        VisitLogService visitLogService = (VisitLogService) sh.getBean("visitLogService");
//        ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
//        VisitLogService visitLogService = (VisitLogService) ac.getBean("visitLogService");

        VisitLogService visitLogService = (VisitLogService) SpringContextUtils.getBean("visitLogService");
        System.out.println(visitLogService);

        Map<String, Claim> map = (Map<String, Claim>) request.getAttribute("map");
        visitLogService.save(new VisitLog(request.getRequestURI(),
                map.get("username").toString(),
                map.get("tenantId").toString(),1));

//        visitLogService.save(new VisitLog(request.getRequestURI(), "admin"));
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
