package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private static final int MAX_REQUESTS = 5;
    private static final long TIME_WINDOW_MILLIS = 60 * 1000;

    // Map userId -> list of timestamps
    private final Map<Integer, LinkedList<Long>> userRequestTimestamps = new ConcurrentHashMap<>();

    public synchronized boolean isAllowed(Integer userId) {
        long now = Instant.now().toEpochMilli();

        userRequestTimestamps.putIfAbsent(userId, new LinkedList<>());
        LinkedList<Long> timestamps = userRequestTimestamps.get(userId);

        // Remove old timestamps
        while (!timestamps.isEmpty() && (now - timestamps.peek()) > TIME_WINDOW_MILLIS) {
            timestamps.poll();
        }

        if (timestamps.size() < MAX_REQUESTS) {
            timestamps.add(now);
            return true;
        } else {
            return false;
        }
    }
}
