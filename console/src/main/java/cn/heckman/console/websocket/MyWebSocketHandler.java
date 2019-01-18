//package cn.heckman.console.websocket;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.socket.BinaryMessage;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.AbstractWebSocketHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MyWebSocketHandler extends AbstractWebSocketHandler {
//
//    private Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);
//
//    private Map<String, Object> map = new HashMap<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        super.afterConnectionEstablished(session);
//        map.put(session.getId(), session);
//        session.sendMessage(new TextMessage("connect successfully!!!"));
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        //super.handleTextMessage(session, message);
//        logger.info("get message is {}", message.getPayload());
//        session.sendMessage(new TextMessage("connect successfully!!!" + System.currentTimeMillis()));
//    }
//
//    @Override
//    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
////        super.handleBinaryMessage(session, message);
//    }
//}
