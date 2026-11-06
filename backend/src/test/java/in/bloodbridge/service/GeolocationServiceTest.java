package in.bloodbridge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeolocationServiceTest {

    private GeolocationService geolocationService;

    @BeforeEach
    void setUp() {
        geolocationService = new GeolocationService();
    }

    @Test
    @DisplayName("Distance calculation between New Delhi and Noida is accurate")
    void testDistanceCalculation() {
        // AIIMS Delhi (28.5672, 77.2100) to Fortis Noida (28.6186, 77.3725) ~16.8 km
        double distance = geolocationService.calculateDistanceKm(28.5672, 77.2100, 28.6186, 77.3725);
        assertTrue(distance > 15.0 && distance < 20.0, "Expected distance between 15 and 20 km, got: " + distance);
    }

    @Test
    @DisplayName("Distance band formatting maps accurately")
    void testDistanceBandFormatting() {
        assertEquals("< 2 km", geolocationService.formatDistanceBand(1.2));
        assertEquals("2–5 km", geolocationService.formatDistanceBand(3.5));
        assertEquals("5–10 km", geolocationService.formatDistanceBand(7.0));
        assertEquals("10–25 km", geolocationService.formatDistanceBand(18.0));
        assertEquals("25–50 km", geolocationService.formatDistanceBand(35.0));
        assertEquals("> 50 km", geolocationService.formatDistanceBand(75.0));
    }
}
