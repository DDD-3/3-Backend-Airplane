package com.ddd.airplane.chat;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.room.Room;
import com.ddd.airplane.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final RoomService roomService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/room/{roomId}/join")
    public void join(
            @DestinationVariable Long roomId,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            throw new RoomNotFoundException(String.valueOf(roomId));
        }

        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        sessionAttributes.put("room", room);

        Account account = (Account) sessionAttributes.get("account");

        messagingTemplate.convertAndSend(
                MessageFormat.format("/topic/room/{0}", roomId),
                new ChatMessage(
                        ChatMessageType.JOIN,
                        roomId,
                        account.getEmail())
        );
    }

    @MessageMapping("/room/{roomId}/chat")
    public void chat(
            @DestinationVariable Long roomId,
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        Room room = (Room) sessionAttributes.get("room");
        if (room == null || !roomId.equals(room.getRoomId())) {
            throw new RoomInvalidException(String.valueOf(roomId));
        }

        Account account = (Account) sessionAttributes.get("account");

        messagingTemplate.convertAndSend(
                MessageFormat.format("/topic/room/{0}", roomId),
                new ChatMessage(
                        ChatMessageType.CHAT,
                        roomId,
                        account.getEmail(),
                        chatMessage.getContent())
        );
    }

    // TODO : send error message to a connected user currently
    @MessageExceptionHandler
    @SendToUser(value = "/queue/errors", broadcast = false)
    public ChatError roomNotFoundException(RoomNotFoundException e) {
        log.error(e.getMessage());
        return new ChatError(e.getMessage());
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/errors", broadcast = false)
    public ChatError roomInvalidException(RoomInvalidException e) {
        log.error(e.getMessage());
        return new ChatError(e.getMessage());
    }
}
