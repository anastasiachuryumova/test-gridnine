package com.gridnine.testing.repo;

import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository {
    List<Flight> saveAll(List<Flight> flights);
    List<Flight> getFlightsByTime(LocalDateTime arrival, LocalDateTime departure);
}
