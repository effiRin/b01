package org.effirin.b01.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User {

    private String mid;

    private String mpw;

    private String email;

    private boolean del;

    private boolean social;

    private Map<String, Object> props; // 소셜 로그인 정보

    public MemberSecurityDTO(String email, String mpw, Map<String,Object> props){ // 생성자 만들기
        // 부모 클래스가 user라서 여기에 맞춰줘야 함. 그래서 먼저 통일하기

        super(email,mpw,List.of(new SimpleGrantedAuthority("ROLE_USER")));

        this.mid = email;
        this.mpw = mpw;
        this.props = props;

    }

    public MemberSecurityDTO(String username, String password, String email,
                             boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;

    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.props;
    }

    @Override
    public String getName() {
        return mid;
    }
}