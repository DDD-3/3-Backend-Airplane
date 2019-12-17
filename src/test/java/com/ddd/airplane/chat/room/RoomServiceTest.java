package com.ddd.airplane.chat.room;

import com.ddd.airplane.common.BaseServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomServiceTest extends BaseServiceTest {
    @Autowired
    private RoomService roomService;

    @Test
    public void getRoom() {
        // given
        Room given = roomService.createRoom("Hello, Room!");
        
        // when
        Room actual = roomService.getRoom(given.getRoomId());

        // then
        Assert.assertEquals(given.getRoomId(), actual.getRoomId());
        Assert.assertEquals(given.getName(), actual.getName());
    }
}