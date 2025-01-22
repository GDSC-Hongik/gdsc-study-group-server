package com.gdgoc.study_group.study.application;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.day.dto.DayDTO;
import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gdgoc.study_group.exception.ErrorCode.STUDY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class AdminStudyService {

    public final StudyRepository studyRepository;

    public AdminStudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }


    /**
     * 스터디를 생성합니다.
     *
     * @param request 스터디 생성 DTO
     * @return ResponseDTO 반환
     */
    public Long createStudy(StudyCreateRequest request) {

        Study study = Study.create(
                        request.name(),
                        request.description(),
                        request.requirements(),
                        request.question(),
                        request.maxParticipants(),
                        request.studyStatus()
        );


        // TODO: 스터디를 생성한 유저를 스터디장으로 설정한 뒤 studyMembers에 추가

        // 등록된 커리큘럼이 있다면 엔티티로 변환하여 리스트에 추가
        List<Curriculum> curriculums = request.curriculums().stream()
                .map(curriculumDTO -> Curriculum.create(study, curriculumDTO.week(), curriculumDTO.subject()))
                .collect(Collectors.toList());

        // 등록된 스터디 날짜가 있다면 엔티티로 변환하여 리스트에 추가
        List<Day> days = request.days().stream()
                        .map(dayDTO -> Day.create(study, dayDTO.day(), dayDTO.startTime()))
                        .collect(Collectors.toList());

        study.addInfo(curriculums, days);
        studyRepository.save(study);

        return study.getId();
    }


    public Long updateStudy(Long studyId, StudyCreateRequest updateRequest) {
        Optional<Study> study = studyRepository.findById(studyId);

        if (study.isEmpty()) {}

        Study existingStudy = studyRepository.findById(studyId).get();
        Study updatedStudy =
                Study.builder()
                        .id(existingStudy.getId())
                        .name(existingStudy.getName())
                        .description(existingStudy.getDescription())
                        .requirement(existingStudy.getRequirement())
                        .question(existingStudy.getQuestion())
                        .curriculums(new ArrayList<>())
                        .days(new ArrayList<>())
                        .maxParticipants(existingStudy.getMaxParticipants())
                        .isApplicationClosed(existingStudy.getIsApplicationClosed())
                        .build();

        // 등록된 커리큘럼이 있다면 엔티티로 변환하여 스터디에 추가
        if (updateRequest.curriculums() != null) {
            for (CurriculumDTO curriculumDTO : updateRequest.curriculums()) {
                updatedStudy.getCurriculums().add(createCurriculum(curriculumDTO, updatedStudy));
            }
        }

        // 등록된 스터디 날짜가 있다면 엔티티로 변환하여 스터디에 추가
        if (updatedStudy.getDays() != null) {
            for (DayDTO dayDTO : updateRequest.days()) {
                updatedStudy.getDays().add(createDay(dayDTO, updatedStudy));
            }
        }

        studyRepository.save(updatedStudy);

        return updatedStudy.getId();
    }

    /**
     * 스터디장 권한 확인 필요 스터디를 삭제합니다
     *
     * @param studyId 삭제할 스터디의 아이디
     * @return 해당하는 스터디의 존재 여부
     */
    public void deleteStudy(Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));
        studyRepository.delete(study);
    }

}
