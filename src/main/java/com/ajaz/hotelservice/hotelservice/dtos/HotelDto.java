package com.ajaz.hotelservice.hotelservice.dtos;

import com.ajaz.hotelservice.hotelservice.models.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private String name;
    private List<RoomDto> rooms = new ArrayList<>();
    private String address;
    private String about;

    public static HotelDto from(Hotel hotel){
        HotelDto hotelDto = new HotelDto();
        hotelDto.setName(hotel.getName());
        hotelDto.setAddress(hotel.getAddress());
        hotelDto.setAbout(hotel.getAbout());

        List<RoomDto> roomDtos = hotel.getRooms().stream().map(e->RoomDto.from(e)).collect(Collectors.toList());

        hotelDto.setRooms(roomDtos);
        return hotelDto;
    }
}
