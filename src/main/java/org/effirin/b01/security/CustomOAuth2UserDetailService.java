package org.effirin.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.effirin.b01.security.dto.MemberSecurityDTO;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserDetailService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

//        // 소셜 로그인했을 때 얘가 동작하는지 확인
//        log.info("==========================================");
//        log.info("==========================================");
//        log.info(userRequest);
//        log.info("==========================================");

//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        Map<String, Object> paramMap = oAuth2User.getAttributes();
//
//        paramMap.forEach((k,v) -> {
//            log.info("-------------------------------------");
//            log.info(k +":" + v); // 콘솔에 id, properties 등이 찍힘 -> 확인
//        });

        log.info("userRequest....");
        log.info(userRequest);

        log.info("oauth2 user.....................................");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("NAME: "+clientName);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;

        switch (clientName){
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
        }

        log.info("===============================");
        log.info(email);   // 카카오 로그인 했을 때 이메일을 얻어오는지 확인
        log.info("===============================");

//        return super.loadUser(userRequest);

        return new MemberSecurityDTO(email,"1111",paramMap);
        // board/register로 로그인해서 들어가지는지 확인
    }

    private String getKakaoEmail(Map<String, Object> paramMap){

        log.info("KAKAO-----------------------------------------");
        Object value = paramMap.get("kakao_account");

        log.info(value);

        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String)accountMap.get("email");

        log.info("email..." + email);   // 카카오 로그인 했을 때 이메일을 얻어오는지 확인

        return email;
    }

}
