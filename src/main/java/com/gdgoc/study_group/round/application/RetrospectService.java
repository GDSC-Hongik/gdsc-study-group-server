package com.gdgoc.study_group.round.application;

import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.dao.RoundRepository;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.roundMember.domain.RoundMember;
import com.gdgoc.study_group.round.dto.RetrospectRequest;
import com.gdgoc.study_group.round.dto.RetrospectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gdgoc.study_group.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrospectService {

    private final RoundRepository roundRepository;
    private final MemberRepository memberRepository;

    /**
     * 멤버 아이디와 회고 내용을 받아서 회고를 생성합니다.
     *
     * @param roundId 라운드 아이디
     * @param request 멤버 아이디와 회고 내용
     */
    @Transactional(readOnly = false)
    public Long createRetrospect(Long roundId, Long memberId, RetrospectRequest request) throws CustomException {

        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        if (roundRepository.findRoundMember(roundId, memberId).isPresent()) {
            throw new CustomException(RETROSPECT_ALREADY_EXIST);
        }

        RoundMember roundMember = RoundMember.create(round, member, request.retrospect());
        roundRepository.saveRoundMember(roundMember);

        return roundMember.getId();
    }


    /**
     * 회고를 수정합니다
     *
     * @param roundId 수정할 회고의 회차 ID
     * @param request 멤버의 아이디, 수정할 회고의 내용
     */
    @Transactional(readOnly = false)
    public Long updateRetrospect(Long roundId, Long memberId, RetrospectRequest request) throws CustomException {

        RoundMember roundMember = roundRepository.findRoundMember(roundId, memberId)
                .orElseThrow(() -> new CustomException(RETROSPECT_NOT_FOUND));
        validateRetrospectOwner(roundMember, memberId);

        roundMember.update(request.retrospect());
        roundRepository.saveRoundMember(roundMember);

        return roundMember.getId();
    }


    /**
     * 회고를 삭제합니다.
     *
     * @param roundId 수정할 회고의 회차 ID
     */
    @Transactional(readOnly = false)
    public void deleteRetrospect(Long roundId, Long memberId) throws CustomException {

        RoundMember roundMember = roundRepository.findRoundMember(roundId, memberId)
                .orElseThrow(() -> new CustomException(RETROSPECT_NOT_FOUND));
        validateRetrospectOwner(roundMember, memberId);

        roundRepository.deleteRoundMemberById(roundMember.getId());
    }


    /**
     * 특정 회고를 조회합니다.
     *
     * @param roundId 조회할 회고의 라운드 ID
     * @return 해당 회고를 작성한 멤버의 아이디와 회고 내용
     */
    public RetrospectResponse getRetrospect(Long roundId, Long memberId) throws CustomException {

        RoundMember roundMember = roundRepository.findRoundMember(roundId, memberId)
                .orElseThrow(() -> new CustomException(RETROSPECT_NOT_FOUND));

        return RetrospectResponse.from(roundMember);
    }


    /**
     * 해당 라운드의 모든 회고를 조회합니다.
     *
     * @param roundId 조회할 라운드 ID
     * @return 해당 라운드의 모든 회고를 리스트 형식으로 반환
     */
    public List<RetrospectResponse> getAllRetrospects(Long roundId) {

        return roundRepository.findRoundMemberByRoundId(roundId).stream().map(RetrospectResponse::from).toList();
    }

    //==========HELPER==========//
    private void validateRetrospectOwner(RoundMember roundMember, Long memberId) {
        if (!roundMember.getMember().getId().equals(memberId)) {
            throw new CustomException(FORBIDDEN);
        }
    }
}

