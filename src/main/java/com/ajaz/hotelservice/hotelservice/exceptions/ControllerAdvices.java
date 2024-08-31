package com.ajaz.hotelservice.hotelservice.exceptions;

import com.ajaz.hotelservice.hotelservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleHotelNotFoundException(HotelNotFoundException e){
        ExceptionDto response = new ExceptionDto(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleRoomNotFoundException(RoomNotFoundException e){
        ExceptionDto response = new ExceptionDto(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
