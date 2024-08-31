package com.ajaz.hotelservice.hotelservice.repositories;

import com.ajaz.hotelservice.hotelservice.models.Room;
import com.ajaz.hotelservice.hotelservice.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByRoomType(RoomType type);
}
