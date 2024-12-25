package repo;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.repo.FlightRepository;
import org.junit.jupiter.api.Test;
import util.TestData;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FlightRepositoryTest {

    private final FlightRepository flightRepository = new FlightRepository() {
        @Override
        public List<Flight> saveAll(List<Flight> flights) {
            return flights;
        }

        @Override
        public List<Flight> getFlightsByTime(LocalDateTime arrival, LocalDateTime departure) {
            return List.of(new Flight(List.of(new Segment(arrival, departure))));
        }
    };
    private final LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3).withNano(0);
    private final List<Flight> flights = TestData.createFlights();

    @Test
    void saveAllSuccess() {

        var actualResult = flightRepository
                .saveAll(flights);

        assertThat(actualResult).isEqualTo(flights);
        assertThat(
                actualResult.get(0).getSegments().get(0).getDepartureDate().withNano(0))
                .isEqualTo(this.threeDaysFromNow);
    }

    @Test
    void getFlightsByTimeSuccess() {

        var actualResult = flightRepository
                .getFlightsByTime(threeDaysFromNow, threeDaysFromNow.plusHours(2));

        assertThat(
                actualResult.get(0).getSegments().get(0).getDepartureDate().withNano(0))
                .isEqualTo(this.threeDaysFromNow);
        assertThat(
                actualResult.get(0).getSegments().get(0).getArrivalDate().withNano(0))
                .isEqualTo(this.threeDaysFromNow.plusHours(2));
    }
}
