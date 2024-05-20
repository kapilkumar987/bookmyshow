package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer>
{
    Optional<Screen> findById(int screenId);
}
