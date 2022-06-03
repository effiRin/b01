package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {

    @Id // id 쓸때는 generate value를 했는데 여기는 회원이 직접 입력하는 거라 안씀
    private String mid;

    private String mpw;
    private String email;
    private boolean del;

    private boolean social;

    // element collection을 만들기 위해서 아래 추가
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();
    //1대 다의 관계에선 set을 써라

    public void changePassword(String mpw){
        this.mpw = mpw;
    }

    public void changeEmail(String email){
        this.email = email;
    }

    public void changeDel(boolean del){
        this.del = del;
    }

    // Role들은 권한 바꾸는 용
    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }

    // social 바꾸는 용
    public void changeSocial(boolean social){this.social = social;}
}
