package cn.heckman.console.filter;

import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.modulecommon.utils.JWTUtils;
import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.moduleservice.entity.MQTopic;
import cn.heckman.moduleservice.entity.VisitLog;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@SuppressWarnings("all")
/*@Component
@WebFilter(filterName = "authFilter", urlPatterns = "/console-api/*")*/
public class AuthFilter implements Filter {

//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private VisitLogService visitLogService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            Map<String, Claim> map = JWTUtils.verifyToken(token);
            String timestamp = map.get("timestamp").asString();
            String tokenTimestamp = request.getHeader("timestamp");
            if (timestamp.equals(tokenTimestamp)) {
                request.setAttribute("map", map);
                /*if (!redisService.exists("user:" + map.get("userId").asString())) {
                    User user = userService.findById(map.get("userId").asString());
                    redisService.set("USER:" + map.get("userId").asString(), user, 24 * 60 * 60l);
                }*/

//                VisitLogService visitLogService = SpringContextUtils.getBean(VisitLogService.class);
//                visitLogService.save(new VisitLog(request.getRequestURI(), map.get("username").asString()));


                MQProducer producerService = SpringContextUtils.getBean(MQProducer.class);
//                Destination destination = new ActiveMQQueue(MQTopic.VISIT_LOG);
                producerService.sendMessage(MQTopic.VISIT_LOG, JSON.toJSONString(
                        new VisitLog(request.getRequestURI(),
                                map.get("username").asString(),
                                map.get("tenantId").asString(), 1)));

                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect("/console/error/token_error");
            }
        } else {
            response.sendRedirect("/console/error/token_null");
        }
    }

    @Override
    public void destroy() {

    }
}
