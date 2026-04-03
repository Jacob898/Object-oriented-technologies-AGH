package pl.edu.agh.to.school.greenMail;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Profile("dev")
@Component
//@DependsOn("greenMail")

public class GreenMailHandler {

    private final GreenMail greenMail;

    public GreenMailHandler(GreenMail greenMail) {
        this.greenMail = greenMail;
    }

    @PostConstruct
    public void startService() {
        greenMail.start();
        System.out.println("GreenMail service started on port 1025");
    }

    @PreDestroy
    private void showAllGatheredEmails() throws MessagingException {
        for (MimeMessage message : greenMail.getReceivedMessages()) {
            String formattedMessage = "From: " + Arrays.toString(message.getFrom()) +
                    " | Subject: " + message.getSubject() +
                    " | Body: " + GreenMailUtil.getBody(message);
            System.out.println(formattedMessage);
        }
        greenMail.stop();
    }
}
