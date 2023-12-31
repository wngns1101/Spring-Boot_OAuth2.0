package com.example.loginApiStudy.Repository;

import com.example.loginApiStudy.Entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KakaoRepository {

    private final EntityManager em;
    public void join(User user){
        em.persist(user);
    }
}
