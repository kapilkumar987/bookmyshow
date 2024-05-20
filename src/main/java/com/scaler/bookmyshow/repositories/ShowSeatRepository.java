package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer>
{
    // Figure out which seats are available for "KungFu Panda 3"

    Optional<ShowSeat> findById(int showSeatId);

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    List<ShowSeat> findAllByShowIdAndSeatIdsInAndSeatStatus_Available(int showId, List<Integer> seatIds);

    // Mapping Table: M:M Relationship (Cardinality)


}
