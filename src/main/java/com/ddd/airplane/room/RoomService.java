package com.ddd.airplane.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public Room createRoom(String name) {
        return roomRepository.save(name);
    }

    Room getRoom(String roomId) {
        return roomRepository.findById(roomId);
    }
}
