package com.ddd.airplane.chat.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public Room createRoom(String name) {
        return roomRepository.save(name);
    }

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId);
    }
}
