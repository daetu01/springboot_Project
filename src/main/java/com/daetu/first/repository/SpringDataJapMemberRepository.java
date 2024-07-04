package com.daetu.first.repository;

import com.daetu.first.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJapMemberRepository extends JpaRepository<Member, Long> , MemberRepository {

    // JPQL select m from Member m where m.name = ?
    // 인터페이스 이름 만으로도 개발이 끝남. 4
    // querydsl 을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다.
    // jpa , spring jpad, dsl 조합을 한다.

    @Override
    Optional<Member> findByName(String name);
}
