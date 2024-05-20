package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.SeatTypeShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatTypeShowRepository extends JpaRepository<SeatTypeShow, Integer>
{
    List<SeatTypeShow> findAllByShowId(int showId);
}
