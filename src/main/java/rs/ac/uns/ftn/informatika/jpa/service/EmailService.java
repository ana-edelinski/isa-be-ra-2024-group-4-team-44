package rs.ac.uns.ftn.informatika.jpa.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendActivationEmail(String email, String token) {
        String activationLink = "http://localhost:8080/api/users/activate/" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Aktivacija naloga");
        message.setText("Da biste aktivirali svoj nalog, molimo vas da kliknete na sledeći link: " + activationLink);
        mailSender.send(message);
        System.out.println("Email sent USPESNOOOOOO");
    }

    public void sendUnactiveReportEmail(String email) {

        String loginLink = "http://localhost:4200/login";
        Integer newCommentsCount = 0;
        Integer newPostsCount = 0;
        Integer newFollowersCount = 0;

        String messageText = "We have missed you, you haven't logged in in the past 7 days!\n\n" +
                "Here is what happened since then:\n" +
                "Number of new comments on your posts: " + newCommentsCount + "\n" +
                "Number of new posts from people you follow: " + newPostsCount + "\n" +
                "Number of new followers: " + newFollowersCount + "\n\n" +
                "Click here to log back in: " + loginLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Activity report");
        message.setText(messageText);

        mailSender.send(message);
        System.out.println("Email sent successfully!");
        System.out.println(email);
    }


}
