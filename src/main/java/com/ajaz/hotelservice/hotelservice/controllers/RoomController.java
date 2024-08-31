package com.ajaz.hotelservice.hotelservice.controllers;

import com.ajaz.hotelservice.hotelservice.dtos.ApiResponse;
import com.ajaz.hotelservice.hotelservice.dtos.RoomDto;
import com.ajaz.hotelservice.hotelservice.exceptions.RoomNotFoundException;
import com.ajaz.hotelservice.hotelservice.models.Room;
import com.ajaz.hotelservice.hotelservice.models.RoomType;
import com.ajaz.hotelservice.hotelservice.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private RoomService roomService;
    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody Room room){
        Room savedRoom = roomService.createRoom(room);
        RoomDto roomDto = RoomDto.from(savedRoom);
        return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<RoomDto> getAllRooms(){
        List<Room> rooms = roomService.getAllRooms();
        return rooms.stream().map(room -> RoomDto.from(room)).collect(Collectors.toList());
    }

    @GetMapping("/book/{type}/{budget}")
    public ResponseEntity<ApiResponse> bookRoom(@PathVariable RoomType type, @PathVariable Long budget) throws RoomNotFoundException {
        ApiResponse response = roomService.bookRoom(type, budget);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
