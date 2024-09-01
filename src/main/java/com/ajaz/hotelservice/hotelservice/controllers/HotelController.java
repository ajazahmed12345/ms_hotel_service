package com.ajaz.hotelservice.hotelservice.controllers;

import com.ajaz.hotelservice.hotelservice.dtos.HotelDto;
import com.ajaz.hotelservice.hotelservice.dtos.RoomDto;
import com.ajaz.hotelservice.hotelservice.exceptions.HotelNotFoundException;
import com.ajaz.hotelservice.hotelservice.models.Hotel;
import com.ajaz.hotelservice.hotelservice.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private HotelService hotelService;
    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }
    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody Hotel hotel){
        Hotel savedHotel = hotelService.createHotel(hotel);
        HotelDto hotelDto = HotelDto.from(savedHotel);
        return new ResponseEntity<>(hotelDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable("id") Long id) throws HotelNotFoundException {
        Hotel hotel = hotelService.getHotelById(id);
        HotelDto hotelDto = HotelDto.from(hotel);

        return new ResponseEntity<>(hotelDto, HttpStatus.OK);
    }

    @GetMapping
    public List<HotelDto> getAllHotels(){
        List<Hotel> hotels = hotelService.getAllHotels();

        return hotels.stream().map(hotel -> HotelDto.from(hotel)).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable("id") Long id, @RequestBody HotelDto hotelDto) throws HotelNotFoundException {
        Hotel updatedHotel = hotelService.updateHotelById(id, hotelDto);
        HotelDto response = HotelDto.from(updatedHotel);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
