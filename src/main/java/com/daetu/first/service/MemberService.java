package com.daetu.first.service;

import com.daetu.first.domain.Member;
import com.daetu.first.repository.MemberRepository;
import com.daetu.first.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


// jpa 쓸 때, transactional 이 있어야 함. 데이터 변경이 있을 때 다 transactional 안에 있어야 함.
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /*
    회원 가입
     */
    public Long join(Member member) {

        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.print("join = " + timeMs + "ms");
        }
        // 같은 이름이 있는 중복 회원 X

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
