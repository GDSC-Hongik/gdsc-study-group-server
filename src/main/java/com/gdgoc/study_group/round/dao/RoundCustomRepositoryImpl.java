package com.gdgoc.study_group.round.dao;

import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.roundMember.domain.RoundMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoundCustomRepositoryImpl implements RoundCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public void saveRoundMember(RoundMember roundMember) {
        entityManager.persist(roundMember);
    }
}
