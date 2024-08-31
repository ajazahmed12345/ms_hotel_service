package com.ajaz.hotelservice.hotelservice.dtos;

import com.ajaz.hotelservice.hotelservice.models.Hotel;
import com.ajaz.hotelservice.hotelservice.models.Room;
import com.ajaz.hotelservice.hotelservice.models.RoomStatus;
import com.ajaz.hotelservice.hotelservice.models.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private Long roomNumber;
    private Long floorNumber;
    private String hotelName;

    @Enumerated(EnumType.ORDINAL)
    private RoomType roomType;

    @Enumerated(EnumType.ORDINAL)
    private RoomStatus roomStatus;

    private Long price;

    public static RoomDto from(Room room){

        RoomDto roomDto = RoomDto.builder()
                .roomNumber(room.getRoomNumber())
                .floorNumber(room.getFloorNumber())
                .hotelName(room.getHotelName())
                .roomType(room.getRoomType())
                .roomStatus(room.getRoomStatus())
                .price(room.getPrice())
                .build();

        return roomDto;
    }
}
