package com.example.instaclone_9room.socket;

import com.example.instaclone_9room.controller.dto.ChatDTO;
import com.example.instaclone_9room.service.chatService.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final ChatService service;

    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // chatRoomId: {session1, session2}
    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 및 세션 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessions.add(session);
    }

    // 메시지 수신, 메시지를 받아 들이는 메서드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
       // JSON 형태로 받아오기
        String payload = message.getPayload();
        log.info("payload {}", payload);

        // 페이로드 -> MessageDTO로 변환
        ChatDTO.MessageDTO messageDTO = mapper.readValue(payload, ChatDTO.MessageDTO.class);
        log.info("session {}", messageDTO.toString());

        Long chatRoomId = messageDTO.getChatRoomId();

        // 메모리 상에 채팅방에 대한 세션 없으면 생성
        if (!chatRoomSessionMap.containsKey(chatRoomId)) {
            chatRoomSessionMap.put(chatRoomId, new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        switch (messageDTO.getMessageType()) {
            case ENTER:
                chatRoomSession.add(session);
                break;
            case CHAT:
                service.saveMessage(messageDTO);
                sendMessageToChatRoom(messageDTO, chatRoomSession);
                break;
            default:
                log.warn("알 수 없는 메시지 타입: {}", messageDTO.getMessageType());
        }
    }

    // 소켓 종료 확인, 세션 정리 작업
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);

        for (Set<WebSocketSession> chatRoomSessions : chatRoomSessionMap.values()) {
            chatRoomSessions.remove(session);
        }
    }

    // 메시지 송신, 메시지를 보내는 역할
    public <T> void sendMessage(WebSocketSession session, T message) {
        if (!session.isOpen()) {
            log.warn("세션이 닫혀 있습니다: {}", session.getId());
            return;
        }
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error("메시지 전송 실패: {}, 세션 ID: {}", e.getMessage(), session.getId());
        }
    }


    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    public void sendMessageToChatRoom(ChatDTO.MessageDTO messageDTO, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, messageDTO));//2
    }

    // 모든 세션에 메시지 브로드캐스트 (Optional)
    public <T> void broadcastMessageToAll(T message) {
        sessions.parallelStream().forEach(sess -> sendMessage(sess, message));
    }

}
