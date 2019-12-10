package com.ddd.airplane.chat;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Component
public class ChatChannelInterceptor implements ChannelInterceptor {
    private final TokenStore tokenStore;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {

            // get principal using access token
            String accessToken = accessor.getFirstNativeHeader("access-token");
            Authentication authentication = tokenStore.readAuthentication(accessToken);
            AccountAdapter accountAdapter = (AccountAdapter) authentication.getPrincipal();
            Account account = accountAdapter.getAccount();

            // set email to session attributes
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            sessionAttributes.put("email", account.getEmail());

        }

        return message;
    }
}
