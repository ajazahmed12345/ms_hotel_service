package com.ajaz.hotelservice.hotelservice.services;

import com.ajaz.hotelservice.hotelservice.dtos.ApiResponse;
import com.ajaz.hotelservice.hotelservice.dtos.ApiStatus;
import com.ajaz.hotelservice.hotelservice.dtos.RoomDto;
import com.ajaz.hotelservice.hotelservice.exceptions.RoomNotFoundException;
import com.ajaz.hotelservice.hotelservice.models.*;
import com.ajaz.hotelservice.hotelservice.repositories.HotelRepository;
import com.ajaz.hotelservice.hotelservice.repositories.RoomRepository;
import com.ajaz.hotelservice.hotelservice.utilities.EmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
    private EmailUtil emailUtil;

    @Value("${email.app.passkey}")
    private String emailAppPasskey;

    @Value("${email.from}")
    private String emailFrom;

    @Value("${email.to}")
    private String emailTo;



    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository, EmailUtil emailUtil){
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.emailUtil = emailUtil;
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

        // send the email to ajaz.dtici786@gmail.com to notify about the room booking
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, emailAppPasskey);
            }
        };
        Session session = Session.getInstance(props, auth);

        Long changeMoney = budget - minPricedRoom.getPrice();

        ApiResponse response = ApiResponse.builder()
                .message("Room booked successfully with min price: " + minPricedRoom.getPrice() + " returned amount = " + changeMoney)
                .status(ApiStatus.SUCCESS)
                .roomDto(RoomDto.from(minPricedRoom))
                .build();

        emailUtil.sendEmail(session, emailTo, "Testing subject for mail service", response.toString());

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

    public String deleteRoomById(Long id) throws RoomNotFoundException {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isEmpty()){
            throw new RoomNotFoundException("Room trying to delete with id: " + id + " does not exist");
        }

        roomRepository.deleteById(id);

        return "Room from Hotel " + roomOptional.get().getHotelName() + " and with id: " + id + " has been deleted successfully.";
    }
}
