package com.ssu.soundbridge.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "wlstj0385@gmail.com";
    //인증 번호 저장 map
    private final Map<String, String> codeMap  = new ConcurrentHashMap<>();
    /** 이메일별 ‘인증 완료’ 여부 저장 */
    private final Map<String, Boolean> verifiedMap = new ConcurrentHashMap<>();

    // 랜덤으로 숫자 생성
    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) { // 인증 코드 8자리
            int index = random.nextInt(3); // 0~2까지 랜덤, 랜덤값으로 switch문 실행

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> key.append(random.nextInt(10)); // 숫자
            }
        }
        return key.toString();
    }

    public MimeMessage createMail(String mail, String number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("이메일 인증");
        String body = "";
        body += "<h3>요청하신 인증 번호입니다.</h3>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>감사합니다.</h3>";
        message.setText(body, "UTF-8", "html");

        return message;
    }

    // 메일 발송
    @Async("mailExecutor")
    public void sendSimpleMessage(String sendEmail) throws MessagingException {
        String number = createNumber(); // 랜덤 인증번호 생성
        MimeMessage message = createMail(sendEmail, number); // 메일 생성
        try {
            javaMailSender.send(message); // 메일 발송
            codeMap .put(sendEmail, number);
            verifiedMap.remove(sendEmail);             // 이전 인증 상태 초기화
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("메일 발송 중 오류가 발생했습니다.");
        }
    }

    public boolean checkVerificationNumber(String mail, String userNumber) {
        String storedNumber =  codeMap.getOrDefault(mail,null); // 해당 이메일의 인증 번호 반환
        boolean ok = storedNumber != null && storedNumber.equals(userNumber);
        if (ok) verifiedMap.put(mail, true);    // 인증 완료 표시
        return ok;
    }
    public boolean isVerified(String email) {
        return verifiedMap.getOrDefault(email, false);
    }
    /** 인증 성공 후 호출해 Map 값 삭제 */
    public void clearVerification(String email) {
        verifiedMap.remove(email);
        codeMap.remove(email);
    }
}
