package com.ddd.airplane.chat;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountAdapter;
import com.ddd.airplane.room.Room;
import com.ddd.airplane.room.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ChatChannelInterceptor implements ChannelInterceptor {
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RedisPublisher redisPublisher;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

        if (StompCommand.CONNECT == accessor.getCommand()) {
            // get authenticated account using access token
            String accessToken = accessor.getFirstNativeHeader("access-token");
            Authentication authentication = tokenStore.readAuthentication(accessToken);
            if (authentication == null) {
                throw new AuthNotFoundException(accessToken);
            }
            AccountAdapter accountAdapter = (AccountAdapter) authentication.getPrincipal();
            Account account = accountAdapter.getAccount();
            // set account to session attributes
            sessionAttributes.put("account", account);
        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            // extract room Id
            String simpDestination = (String) accessor.getHeader("simpDestination");
            Long roomId = Long.valueOf(simpDestination.replace("/topic/room/", ""));
            // validate room
            Room room = roomService.getRoom(roomId);
            if (room == null) {
                throw new RoomNotFoundException(roomId);
            }
            // add topic
            roomService.addTopic(room.getRoomId());
            // set room to session attributes
            sessionAttributes.put("room", room);
            // get account from session attributes
            Account account = (Account) sessionAttributes.get("account");
            // send message
            if (account != null) {
                log.info("User Joined : " + account.getEmail());
                redisPublisher.publish(
                        roomService.getTopic(room.getRoomId()),
                        ChatMessage.builder()
                                .type(ChatMessageType.JOIN)
                                .roomId(room.getRoomId())
                                .senderId(account.getEmail())
                                .senderNickName(account.getNickname())
                                .build());
            }
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            // get account, room from session attributes
            Account account = (Account) sessionAttributes.get("account");
            Room room = (Room) sessionAttributes.get("room");
            // send message
            if (account != null && room != null) {
                log.info("User Disconnected : " + account.getEmail());
                redisPublisher.publish(
                        roomService.getTopic(room.getRoomId()),
                        ChatMessage.builder()
                                .type(ChatMessageType.LEAVE)
                                .roomId(room.getRoomId())
                                .senderId(account.getEmail())
                                .senderNickName(account.getNickname())
                                .build());
            }
        }

        return message;
    }
}
