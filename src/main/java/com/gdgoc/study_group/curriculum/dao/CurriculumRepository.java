package com.gdgoc.study_group.curriculum.dao;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends CrudRepository<Curriculum, Long> {

    /**
     * 스터디의 모든 커리큘럼을 가져옴
     *
     * @param studyId 스터디 아이디
     * @return 커리큘럼 리스트
     */
    @Query(value = "select c from Curriculum c " +
            "where c.study.id = :studyId")
    List<CurriculumDTO> findAllCurriculums(@Param("studyId") Long studyId);


    /**
     * 스터디의 특정 회차의 subject를 가져옴
     *
     * @param studyId 스터디 아이디
     * @param week 조회할 회차
     * @return 해당 회차의 subject
     */
    @Query(value = "select c.subject from Curriculum c " +
            "where c.study.id = :studyId AND c.week = :week")
    String findSubjectByWeek(@Param("studyId") Long studyId, @Param("week") Integer week);
}
