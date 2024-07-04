package com.daetu.first.service;

import com.daetu.first.domain.Member;
import com.daetu.first.repository.MemberRepository;
import com.daetu.first.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



// Test
// Test에 Transactional 달면 시작할 때 transactional 되면서 rollback 됨.
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    // productions 코드가 나가는 거는 한글 이름 애매한데 테스트는 영어권 사람들하고 일하는 게 아니면 한글로도 많이 적음.
    //
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;



    @Test
     void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring");
        // when
        Long saveId = memberService.join(member);

        // then
       Member findMember =  memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member = new Member();
        member.setName("spring");

        Member member1 = new Member();
        member1.setName("spring");

        // when
        memberService.join(member);
        IllegalStateException e =  assertThrows(IllegalStateException.class, () -> memberService.join(member1));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*    try {
             memberService.join(member1);
             fail();
         } catch (IllegalStateException e){
             assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        // then

    }

}