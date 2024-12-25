package service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.repo.FlightRepository;
import com.gridnine.testing.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.TestData;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    private final List<Flight> flights = TestData.createFlights();
    private final LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
    private final List<Flight> expectedResultOne =
            List.of(
                    //A normal flight with two hour duration
                    new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)))),
                    //A normal multi segment flight
                    new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                            new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)))),
                    //A flight departing in the past
                    //new Flight(List.of(new Segment(threeDaysFromNow.minusDays(6), threeDaysFromNow))),
                    //A flight that departs before it arrives
                    new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.minusHours(6)))),
                    //A flight with more than two hours ground time
                    new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                            new Segment(threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)))),
                    //Another flight with more than two hours ground time
                    new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                            new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4)),
                            new Segment(threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)))));
    private final List<Flight> expectedResultTwo = List.of(
            //A normal flight with two hour duration
            new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)))),
            //A normal multi segment flight
            new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                    new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)))),
            //A flight departing in the past
            new Flight(List.of(new Segment(threeDaysFromNow.minusDays(6), threeDaysFromNow))),
            //A flight that departs before it arrives
            //new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.minusHours(6)))),
            //A flight with more than two hours ground time
            new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                    new Segment(threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)))),
            //Another flight with more than two hours ground time
            new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                    new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4)),
                    new Segment(threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)))));
    private final List<Flight> expectedResultThree = List.of(
            //A normal flight with two hour duration
            new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)))),
            //A normal multi segment flight
            new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                    new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)))),
            //A flight departing in the past
            new Flight(List.of(new Segment(threeDaysFromNow.minusDays(6), threeDaysFromNow))),
            //A flight that departs before it arrives
            new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.minusHours(6)))),
            //A flight with more than two hours ground time
            new Flight(List.of(
//                    new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                    new Segment(threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)))),
            //Another flight with more than two hours ground time
            new Flight(List.of(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
//                    new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4)),
                    new Segment(threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7))))
    );

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Test
    void getFlightsBeforeNow(){
        when(flightRepository.saveAll(flights)).thenReturn(flights);
        var result = flightService.getFlightsBeforeNow(flights);
        assertThat(result.toString()).isEqualTo(expectedResultOne.toString());
    }

    @Test
    void getFlightsArrivalBeforeDeparture(){
        when(flightRepository.saveAll(flights)).thenReturn(flights);
        var result = flightService.getFlightsArrivalBeforeDeparture(flights);
        assertThat(result.toString()).isEqualTo(expectedResultTwo.toString());
    }

    @Test
    void getFlightsGroundTimeMoreThanTwoHours(){
        when(flightRepository.saveAll(flights)).thenReturn(flights);
        var result = flightService.getFlightsGroundTimeMoreThanTwoHours(flights);
        assertThat(result.toString()).isEqualTo(expectedResultThree.toString());
    }
}
