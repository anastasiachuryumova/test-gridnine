package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.repo.FlightRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    //вылет до текущего момента времени
    public List<Flight> getFlightsBeforeNow(List<Flight> flights) {
        List<Flight> allFlights = flightRepository.saveAll(flights);
        List<Segment> segments = allFlights.stream()
                .flatMap(flight -> flight.getSegments().stream())
                .filter(s -> s.getDepartureDate().isBefore(LocalDateTime.now()))
                .toList();
        return allFlights.stream().filter(i -> !i.getSegments().equals(segments)).toList();
    }
    //имеются сегменты с датой прилёта раньше даты вылета
    public List<Flight> getFlightsArrivalBeforeDeparture(List<Flight> flights){
        List<Flight> allFlights = flightRepository.saveAll(flights);
        List<Segment> segments = allFlights.stream()
                .flatMap(flight -> flight.getSegments().stream())
                .filter(s -> s.getArrivalDate().isBefore(s.getDepartureDate()))
                .toList();
        return allFlights.stream().filter(i -> !i.getSegments().equals(segments)).toList();
    }
    //общее время, проведённое на земле превышает два часа
    // (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним)
    public List<Flight> getFlightsGroundTimeMoreThanTwoHours(List<Flight> flights){
        List<Flight> allFlights = flightRepository.saveAll(flights);
        //List<Flight> result = new ArrayList<>();
        for (Flight flight : allFlights) {
            for (int i = 0; i < flight.getSegments().size() - 1; i++) {
                Duration duration =
                        Duration.between(flight.getSegments().get(i).getArrivalDate().toLocalTime(),
                                flight.getSegments().get(i + 1).getDepartureDate().toLocalTime());
                Period period = Period.between(flight.getSegments().get(i).getArrivalDate().toLocalDate(),
                        flight.getSegments().get(i + 1).getDepartureDate().toLocalDate());
                if (duration.toHours() > 2 || period.getDays() > 0) {
                    flight.getSegments().remove(i);
                }
            }
        }
        return allFlights;
    }
}
