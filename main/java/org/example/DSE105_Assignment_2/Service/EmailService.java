package org.example.DSE105_Assignment_2.Service;

import org.example.DSE105_Assignment_2.Model.User;
import org.example.DSE105_Assignment_2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Async
    public void sendBulkEmail(String[] recipients, String subject, String content, String fromAddress) throws MessagingException {
        for (String recipient : recipients) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromAddress);
            helper.setTo(recipient);
            helper.setSubject(subject);

            // Personalize the content using merge tags
            String personalizedContent = personalizeContent(content, recipient);
            helper.setText(personalizedContent, true); // Enable HTML content

            mailSender.send(message);
        }
    }

    private String personalizeContent(String content, String recipientEmail) {
        // Fetch the user by email to get their first name
        Optional<User> userOpt = userRepository.findByEmail(recipientEmail);
        String firstName = userOpt.isPresent() ? userOpt.get().getFirstName() : "User";

        // Replace merge tags with actual values
        return content
                .replace("{First.Name}", firstName)
                .replace("{Email}", recipientEmail);
    }
}