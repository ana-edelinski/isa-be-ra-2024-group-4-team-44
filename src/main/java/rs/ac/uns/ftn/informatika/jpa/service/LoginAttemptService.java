package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPTS = 5;
    private final long ATTEMPT_RESET_TIME = TimeUnit.MINUTES.toMillis(1);
    private final ConcurrentHashMap<String, Attempt> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String ip) {
        Attempt attempt = attempts.get(ip);
        if (attempt == null) return false;

        if (System.currentTimeMillis() - attempt.timestamp > ATTEMPT_RESET_TIME) {
            attempts.remove(ip);
            return false;
        }
        return attempt.count >= MAX_ATTEMPTS;
    }

    public void loginFailed(String ip) {
        Attempt attempt = attempts.get(ip);
        if (attempt == null) {
            attempt = new Attempt(1, System.currentTimeMillis());
        } else {
            if (System.currentTimeMillis() - attempt.timestamp > ATTEMPT_RESET_TIME) {
                attempt.count = 1;
                attempt.timestamp = System.currentTimeMillis();
            } else {
                attempt.count++;
            }
        }
        attempts.put(ip, attempt);
    }

    public void loginSucceeded(String ip) {
        attempts.remove(ip);
    }

    private static class Attempt {
        int count;
        long timestamp;

        Attempt(int count, long timestamp) {
            this.count = count;
            this.timestamp = timestamp;
        }
    }
}
