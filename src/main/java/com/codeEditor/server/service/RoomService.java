package com.codeEditor.server.service;

import com.codeEditor.server.entity.Room;
import com.codeEditor.server.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public Room createRoom(){
        Room room = new Room();
        return roomRepository.save(room);
    }

    public Room saveRoom(Room room){
        return roomRepository.save(room);
    }
    @Async
    public void saveRoomAsync(Room room){
        roomRepository.save(room);
    }

    public Room find(String id) throws Exception {
        Optional<Room> roomOptional =  roomRepository.findById(id);
        if(roomOptional.isPresent())return roomOptional.get();
        else throw new Exception("ROOM_NOT_FOUND");
    }

    public String getEncodedCode(String str) {
        try {
            return Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
