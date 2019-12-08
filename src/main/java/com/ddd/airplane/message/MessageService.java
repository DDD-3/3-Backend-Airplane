package com.ddd.airplane.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}
