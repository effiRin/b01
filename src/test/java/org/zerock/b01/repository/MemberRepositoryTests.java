package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;  // 회원 테스트라서 패스워드 인코더가 필요

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void selectMember(){

        String mid = "member90";

        Optional<Member> result = memberRepository.getWithRoles(mid);

        log.info(result.get());

    }

    // 회원가입하는 코드 - 모든 회원이 user라는 권한을 가지고 90번 이상은 user+admin을 가지도록 설계
    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email" + i + "@aaa.bbb")
                    .build();

            member.addRole(MemberRole.USER);

            if (i >= 90) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

}
