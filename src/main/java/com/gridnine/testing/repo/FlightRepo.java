package com.gridnine.testing.repo;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.List;

public class FlightRepo implements FlightRepository {

    @Override
    public List<Flight> saveAll(List<Flight> flights) {
        return flights;
    }

    @Override
    public List<Flight> getFlightsByTime(LocalDateTime arrival, LocalDateTime departure) {
        return List.of(new Flight(List.of(new Segment(arrival, departure))));
    }
}
