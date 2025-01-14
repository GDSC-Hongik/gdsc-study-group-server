package com.gdgoc.study_group.day.dao;

import com.gdgoc.study_group.day.domain.Day;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends CrudRepository<Day, Long> {

    /**
     * 스터디 요일을 가져옴
     *
     * @param studyId 스터디 아이디
     * @return 스터디 요일
     */
    @Query(value = "select d.day from Day d " +
            "where d.study.id = :studyId")
    String getStudyDay(@Param("studyId") Long studyId);


    /**
     * 스터디 시간을 가져옴
     *
     * @param studyId 스터디 아이디
     * @return 스터디 시간
     */
    @Query(value = "select d.startTime from Day d " +
            "where d.study.id = :studyId")
    String getStudyStartTime(@Param("studyId") Long studyId);


}
