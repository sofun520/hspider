package cn.heckman.backend.websocket;//package cn.heckman.console.websocket;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
///**
// * @author liqiang
// * @date 2018/2/5
// */
//@Configuration
//@EnableWebMvc
//@EnableWebSocket
//public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(systemWebSocketHandler(), "/fileUploadServer").setAllowedOrigins("*");
//        registry.addHandler(systemWebSocketHandler(), "/fileUploadServer/sockjs").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Bean
//    public MyWebSocketHandler systemWebSocketHandler() {
//        return new MyWebSocketHandler();
//    }
//
//}
