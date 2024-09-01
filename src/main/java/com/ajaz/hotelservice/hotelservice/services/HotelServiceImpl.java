package com.ajaz.hotelservice.hotelservice.services;

import com.ajaz.hotelservice.hotelservice.dtos.HotelDto;
import com.ajaz.hotelservice.hotelservice.exceptions.HotelNotFoundException;
import com.ajaz.hotelservice.hotelservice.models.Hotel;
import com.ajaz.hotelservice.hotelservice.repositories.HotelRepository;
import com.ajaz.hotelservice.hotelservice.repositories.RoomRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class HotelServiceImpl implements HotelService{

    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    public HotelServiceImpl(HotelRepository hotelRepository){
        this.hotelRepository = hotelRepository;
//        this.floorRepository = floorRepository;
    }
    @Override
    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(Long id) throws HotelNotFoundException {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);

        if(hotelOptional.isEmpty()){
            throw new HotelNotFoundException("Hotel with id: " + id + " not found");
        }

        return hotelOptional.get();
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel updateHotelById(Long id, HotelDto hotelDto) throws HotelNotFoundException {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if(hotelOptional.isEmpty()){
            throw new HotelNotFoundException("Hotel trying to update with id: " + id + " does not exist");
        }

        Hotel existingHotel = hotelOptional.get();
        existingHotel.setName(hotelDto.getName());
        existingHotel.setAddress(hotelDto.getAddress());
        existingHotel.setAbout(hotelDto.getAbout());

        return hotelRepository.save(existingHotel);
    }
}
