package in.bloodbridge.service;

import org.springframework.stereotype.Service;

@Service
public class GeolocationService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public double calculateDistanceKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_KM * c;
        return Math.round(distance * 10.0) / 10.0;
    }

    public String formatDistanceBand(Double distanceKm) {
        if (distanceKm == null) return "Within city";
        if (distanceKm < 2.0) return "< 2 km";
        if (distanceKm <= 5.0) return "2–5 km";
        if (distanceKm <= 10.0) return "5–10 km";
        if (distanceKm <= 25.0) return "10–25 km";
        if (distanceKm <= 50.0) return "25–50 km";
        return "> 50 km";
    }

    public boolean isWithinRadius(Double centerLat, Double centerLon, Double targetLat, Double targetLon, double radiusKm) {
        if (centerLat == null || centerLon == null || targetLat == null || targetLon == null) {
            return true;
        }
        return calculateDistanceKm(centerLat, centerLon, targetLat, targetLon) <= radiusKm;
    }
}
