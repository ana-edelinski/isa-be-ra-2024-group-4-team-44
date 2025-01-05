package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.repository.PostRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Long> getPostCounts() {
        Map<String, Long> result = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime weekStart = LocalDate.now().with(java.time.temporal.ChronoField.DAY_OF_WEEK, 1).atStartOfDay();
        result.put("weekly_posts", postRepository.countPostsBetweenDates(weekStart, now));

        LocalDateTime monthStart = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        result.put("monthly_posts", postRepository.countPostsBetweenDates(monthStart, now));

        LocalDateTime yearStart = LocalDate.now().withDayOfYear(1).atStartOfDay();
        result.put("yearly_posts", postRepository.countPostsBetweenDates(yearStart, now));

        return result;
    }

    public Map<String, Long> getCommentCounts() {
        Map<String, Long> result = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime weekStart = LocalDate.now().with(java.time.temporal.ChronoField.DAY_OF_WEEK, 1).atStartOfDay();
        result.put("weekly_comments", postRepository.countCommentsBetweenDates(weekStart, now));

        LocalDateTime monthStart = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        result.put("monthly_comments", postRepository.countCommentsBetweenDates(monthStart, now));

        LocalDateTime yearStart = LocalDate.now().withDayOfYear(1).atStartOfDay();
        result.put("yearly_comments", postRepository.countCommentsBetweenDates(yearStart, now));

        return result;
    }
}
