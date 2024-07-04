package com.daetu.first.repository;

import com.daetu.first.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {


    //jpa 쓰려면 entityManager 주입받아야 한다.
    private final EntityManager em;


    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
      Member member =  em.find(Member.class, id); //조회할 타입, 식별자 PK 만들어주면 끝.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m ", Member.class) //객체를 대상으로 쿼리를 날리는데 이게 sql로 번역이 됨.  m은 alias임 .
                .getResultList();
    }
}
