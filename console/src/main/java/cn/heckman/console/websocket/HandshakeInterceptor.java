//package cn.heckman.console.websocket;
//
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
//
//import java.util.Map;
//
///**
// * @author liqiang
// * @date 2018/2/5
// */
//public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> attributes) throws Exception {
//        return super.beforeHandshake(request, response, handler, attributes);
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
//        super.afterHandshake(request, response, wsHandler, ex);
//    }
//
//}
