/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author romi_
 */
@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String from, String to, String subject, String body) {

        SimpleMailMessage MimeMessage = new SimpleMailMessage();

        MimeMessage.setFrom(from);
        MimeMessage.setTo(to);
        MimeMessage.setSubject(subject);
        MimeMessage.setText(body);

        javaMailSender.send(MimeMessage);
    }
}