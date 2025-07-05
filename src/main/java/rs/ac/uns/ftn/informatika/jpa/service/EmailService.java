package rs.ac.uns.ftn.informatika.jpa.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.ActivityReportRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.LikeRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.PostRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private final ActivityReportRepository activityReportRepository;
    @Autowired
    public EmailService(ActivityReportRepository activityReportRepository) {
        this.activityReportRepository = activityReportRepository;
    }

    public void sendActivationEmail(String email, String token) {
        String activationLink = "http://localhost:8080/api/users/activate/" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Aktivacija naloga");
        message.setText("Da biste aktivirali svoj nalog, molimo vas da kliknete na sledeći link: " + activationLink);
        mailSender.send(message);
        System.out.println("Email sent USPESNOOOOOO");
    }

    public void sendUnactiveReportEmail(User user, LocalDateTime lastActivity) {

        String loginLink = "http://localhost:4200/login";
        System.out.println(user.getId());
        System.out.println("Krenulo da se izvrsava");


        Set<User> following = user.getFollowing();

        Set<Integer> followingIds = following.stream()
                .map(User::getId)
                .collect(Collectors.toSet());


        System.out.println(followingIds);



        System.out.println("Krenulo3");
        Long newCommentsCount = activityReportRepository.countNewComments(user.getId(), lastActivity);
        System.out.println("Krenulo4");
        Long newLikesCount = activityReportRepository.countNewLikes(user.getId(), lastActivity);
        System.out.println("Krenulo5");

        Long newPostsCount;
        if (followingIds.isEmpty()) {
            newPostsCount = 0L;
            System.out.println("FollowingIds is empty, setting newPostsCount = 0");
        } else {
            newPostsCount = activityReportRepository.countNewPostsFromFollowed(followingIds, lastActivity);
        }

        String messageText = "We have missed you, you haven't logged in in the past 7 days!\n\n" +
                "Here is what happened since then:\n" +
                "Number of new comments on your posts: " + newCommentsCount + "\n" +
                "Number of new likes on your posts: " + newLikesCount + "\n" +
                "Number of new posts from people you follow: " + newPostsCount + "\n\n" +
                "Click here to log back in: " + loginLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Activity report");
        message.setText(messageText);
        System.out.println("Krenulo6");

        mailSender.send(message);
        System.out.println("Email sent successfully!");
    }


}
