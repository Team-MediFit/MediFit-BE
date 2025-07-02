package com.medifitbe.mail.application.service;

import com.medifitbe.jobdata.domain.JobRecommendation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendTo(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("no-reply@medifit.com");
        mailSender.send(message);
    }

    public void sendRecommendationsWithHtml(String to, List<JobRecommendation> recommendations) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("맞춤 채용 추천");

            Context context = new Context();
            context.setVariable("jobList", recommendations);
            context.setVariable("email", to);
            context.setVariable("currentDate", java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy. MM. dd")));
            context.setVariable("currentDay", java.time.LocalDate.now().getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.KOREAN));

            String html = templateEngine.process("email", context);
            helper.setText(html, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }
}