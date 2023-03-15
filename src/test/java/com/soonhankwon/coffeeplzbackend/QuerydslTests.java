package com.soonhankwon.coffeeplzbackend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soonhankwon.coffeeplzbackend.domain.QUser;
import com.soonhankwon.coffeeplzbackend.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class QuerydslTests {
    @PersistenceContext
    EntityManager em;

    @Mock
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        Long point = 15000L;
        User user = new User(1L, loginId, password, email, point);
        em.persist(user);
    }

    @Test
    public void startQuerydsl() {
        QUser m = new QUser("m");

        User findUser = queryFactory
                .select(m)
                .from(m)
                .where(m.loginId.eq("tester4"))
                .fetchOne();

        assert findUser != null;
    }
}
