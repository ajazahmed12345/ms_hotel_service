package com.ajaz.hotelservice.hotelservice.services;

import com.ajaz.hotelservice.hotelservice.dtos.HotelDto;
import com.ajaz.hotelservice.hotelservice.exceptions.HotelNotFoundException;
import com.ajaz.hotelservice.hotelservice.models.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    Hotel createHotel(Hotel hotel);
    Hotel getHotelById(Long id) throws HotelNotFoundException;
    List<Hotel> getAllHotels();

    Hotel updateHotelById(Long id, HotelDto hotelDto) throws HotelNotFoundException;
}
