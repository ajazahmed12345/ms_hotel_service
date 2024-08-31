package com.ajaz.hotelservice.hotelservice.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private ApiStatus status;
    private RoomDto roomDto;
}
