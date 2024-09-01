package com.ajaz.hotelservice.hotelservice.services;

import com.ajaz.hotelservice.hotelservice.dtos.ApiResponse;
import com.ajaz.hotelservice.hotelservice.dtos.ApiStatus;
import com.ajaz.hotelservice.hotelservice.dtos.RoomDto;
import com.ajaz.hotelservice.hotelservice.exceptions.RoomNotFoundException;
import com.ajaz.hotelservice.hotelservice.models.*;
import com.ajaz.hotelservice.hotelservice.repositories.HotelRepository;
import com.ajaz.hotelservice.hotelservice.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository){
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public Room createRoom(Room room){

        Optional<Hotel> hotelOptional = hotelRepository.findByName(room.getHotelName());

        if(hotelOptional.isEmpty()){
            Hotel hotel = new Hotel();
            hotel.setName(room.getHotelName());
            room.setHotel(hotel);
        }
        else{
            room.setHotel(hotelOptional.get());
        }

        return roomRepository.save(room);
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public ApiResponse bookRoom(RoomType type, Long budget) throws RoomNotFoundException {
        List<Room> roomsOfGivenType = roomRepository.findAllByRoomType(type);
        List<Room> filteredRooms = roomsOfGivenType.stream().filter(
                room -> room.getRoomStatus().equals(RoomStatus.AVAILABLE) && room.getPrice() <= budget)
                .collect(Collectors.toList());

        if(filteredRooms.isEmpty()){
            throw new RoomNotFoundException("No rooms found for the type: " + type + " and under budget: " + budget);
        }
        // find min priced room
        Room minPricedRoom = null;
        Long minPrice = Long.MAX_VALUE;

        for(Room room : filteredRooms){
            if(room.getPrice() < minPrice){
                minPrice = room.getPrice();
                minPricedRoom = room;
            }
        }

        minPricedRoom.setRoomStatus(RoomStatus.BOOKED);
        roomRepository.save(minPricedRoom);

        Long changeMoney = budget - minPricedRoom.getPrice();

        ApiResponse response = ApiResponse.builder()
                .message("Room booked successfully with min price: " + minPricedRoom.getPrice() + " returned amount = " + changeMoney)
                .status(ApiStatus.SUCCESS)
                .roomDto(RoomDto.from(minPricedRoom))
                .build();

        return response;

    }

    public Room updateRoomById(Long id, RoomDto roomDto) throws RoomNotFoundException {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isEmpty()){
            throw new RoomNotFoundException("Room trying to update with id: " + id + " does not exist");
        }

        Room existingRoom = roomOptional.get();
        existingRoom.setRoomNumber(roomDto.getRoomNumber());
        existingRoom.setFloorNumber(roomDto.getFloorNumber());
        existingRoom.setRoomType(roomDto.getRoomType());
        existingRoom.setRoomStatus(roomDto.getRoomStatus());
        existingRoom.setPrice(roomDto.getPrice());

        return roomRepository.save(existingRoom);
    }
}
