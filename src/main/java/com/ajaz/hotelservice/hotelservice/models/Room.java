package com.ajaz.hotelservice.hotelservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rooms")
@JsonIgnoreProperties(ignoreUnknown = true)
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
