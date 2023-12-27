package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.wms.WarehouseManagementService.entity.User;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${domen}")
    private String domen;

    public void send(
            String emailTo,
            String subject,
            String message
    ) {
        var mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public void sendActivationCode(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Добро пожаловать в сервис WarehouseManagementWebservice. Пожалуйста, перейдите по ссылке: %sindex/%s",
                    user.getFirstname(),
                    user.getActivationCode(),
                    domen
            );
            send(
                    user.getEmail(),
                    "Activation code",
                    message
            );
        }
    }

}
