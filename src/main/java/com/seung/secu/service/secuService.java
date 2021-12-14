package com.seung.secu.service;

import com.seung.secu.dao.secuDAO;
import com.seung.secu.dto.STORE;
import com.seung.secu.dto.secuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class secuService {

    @Autowired
    private secuDAO dao;

    @Autowired
    private PasswordEncoder pwEnc;

    @Autowired
    private JavaMailSender mailSender;

    private ModelAndView mav = new ModelAndView();

    // security 사용하기 예시 : join
    public ModelAndView secuJoin(secuDTO secu) {

        // 암호화
        secu.setSecuPw(pwEnc.encode(secu.getSecuPw()));

        // 1. secu.getSecuPw() : 우리가 입력한 패스워드
        // 2. pwEnc.encode() : 암호화
        // 3. secu.setSecuPw() : 변경된 것을 패스워드로 설정

        System.out.println("암호화된 비번 : " +secu.getSecuPw());


        // void 타입으로 dao에 넘겨줌(실패/성공여부에 상관없이)
        dao.secuJoin(secu);

        // 그래서 실패하든 성공하든 인덱스로 보냄!
        mav.setViewName("index");

        return mav;
    }

    // security 사용하기 예시2 : login
    public ModelAndView secuLogin(secuDTO secu) {
        // 1. 이메일 주소를 통해 암호화된 비밀번호 가져오기!
        secuDTO secu1 = dao.secuLogin(secu);

        // 2. pwEnc.matches() : 입력한 비밀번호와 암호화된 비밀번호를 비교, 타입은 boolean(true or false)
        if(pwEnc.matches(secu.getSecuPw(), secu1.getSecuPw())){
            // 같다면
            System.out.println("암호가 일치합니다!");
            mav.setViewName("index");
            // 3. 암호가 일치할 경우에 해당 이메일로 메일 전송하기
            // secuMailSend(); 이용 - 드디어ㅠㅜㅠㅜㅠㅜㅠ
            // 메소드 호출!
            secuMailSend(secu.getSecuMail());

        } else {
            // 다르다면
            System.out.println("암호가 일치하지 않습니다!");
            mav.setViewName("index");
        }

        return mav;
    }

    // 메일 전송용 메소드를 따로 만들어서 위의 메소드에서 호출하기
    public void secuMailSend(String secuMail) {
        // 임의의 문자 6자리를 생성하기 (UUID사용)
        String uuid = UUID.randomUUID().toString().substring(1, 7);

        // 보낼 메세지 생성(HTML 형식으로 만들어주기)
        String str = "<h2>안녕하세요. 인천일보 아카데미 입니다.</h2>"
                + "<p>로그인에 성공하셨습니다.</p>"
                + "<p>인증번호는" + uuid + "입니다.</p>";

        MimeMessage mail = mailSender.createMimeMessage();

        try {
            // 메일 설정
            mail.setSubject("스프링부트 이메일 인증테스트");         // 메일 제목
            mail.setText(str, "UTF-8", "html");   // 내용, 인코딩, 타입
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(secuMail));  // 받는 사람

            // 메일 전송
            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    
    
    // 카카오지도 API 이용해서 값 가져오기
    public ModelAndView dmap3(STORE store) {
        int result = dao.dmap3(store);
        
        if(result >0){
            mav.setViewName("dmap3");
            mav.addObject("store", store);
        } else {
            mav.setViewName("index");
        }
        return mav;
    }
}
