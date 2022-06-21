package org.effirin.b01.service;

import org.effirin.b01.dto.MemberJoinDTO;

public interface MemberService {

    class MidExistException extends Exception{

    } // 중첩 클래스 (클래스 안에 클래스 만드는 것)

    void join(MemberJoinDTO memberJoinDTO) throws MidExistException; // id가 중복되었다는 예외

}
