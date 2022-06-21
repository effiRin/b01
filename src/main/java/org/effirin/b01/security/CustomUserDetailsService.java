package org.effirin.b01.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.effirin.b01.domain.Member;
import org.effirin.b01.repository.MemberRepository;
import org.effirin.b01.security.dto.MemberSecurityDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    public CustomUserDetailsService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("====================");
        log.info("=======login========");
        log.info(username);

        Optional<Member> resultData = memberRepository.getWithRoles(username);
        // Optional로 설계한 이유 : 이 회원이 존재하지 않을 수 있어서
        // optional이 데이터가 없으면 throws new (return 대신에 예외를 쓸때) 하는 기능이 있음
        // user를 찾았는데 없을 때 예외를 던져주는 걸로 optional이 편하기 때문

        Member member = resultData.orElseThrow(() -> new UsernameNotFoundException(username));
        // 이런 식으로 직접 던져주는 것, 이때 Supplier라는 return type이 반환됨

        log.info(member);

        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getMid(),
                        member.getMpw(),
                        member.getEmail(),
                        member.isDel(),
                        false,
                        member.getRoleSet()
                                .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name()))
                                .collect(Collectors.toList()
                                )
                );

        return memberSecurityDTO;

//        UserDetails result = User.builder()
//                .username(username)
//                .password(passwordEncoder.encode("1111"))
//                .authorities("ROLE_USER")
//                .build();
//        return result;
        // 1차 테스트
    }
}
