package com.ajaz.hotelservice.hotelservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long roomNumber;
    private Long floorNumber;
    private String hotelName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Enumerated(EnumType.ORDINAL)
    private RoomType roomType;

    @Enumerated(EnumType.ORDINAL)
    private RoomStatus roomStatus;

    private Long price;

}
