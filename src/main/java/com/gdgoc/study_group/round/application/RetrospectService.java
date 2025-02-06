package com.gdgoc.study_group.round.application;

import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.dao.RoundRepository;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.roundMember.dao.RoundMemberRepository;
import com.gdgoc.study_group.roundMember.domain.RoundMember;
import com.gdgoc.study_group.roundMember.dto.RetrospectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.gdgoc.study_group.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrospectService {

    private final RoundRepository roundRepository;
    private final MemberRepository memberRepository;
    private final RoundMemberRepository roundMemberRepository;

    /**
     * 멤버 아이디와 회고 내용을 받아서 회고를 생성합니다.
     *
     * @param roundId 라운드 아이디
     * @param request 멤버 아이디와 회고 내용
     */
    @Transactional(readOnly = false)
    public void createRetrospect(Long roundId, RetrospectDTO request) {

        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        // TODO: dto에서 멤버의 ID를 받아오는 것이 아니라 현재 로그인한 유저의 아이디를 이용하여 roundMember 생성
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RoundMember roundMember = RoundMember.create(round, member, request.retrospect());
        roundMemberRepository.save(roundMember);
    }


    /**
     * 회고를 수정합니다
     *
     * @param roundId 수정할 회고의 회차 ID
     * @param roundMemberId 수정할 회고(round member) ID
     * @param request 멤버의 아이디, 수정할 회고의 내용
     */
    @Transactional(readOnly = false)
    public void updateRetrospect(Long roundId, Long roundMemberId, RetrospectDTO request) {

        Round updatedRound = roundRepository.findById(roundId)
                .orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        // TODO: dto에서 멤버의 ID를 받아오는 것이 아니라 현재 로그인한 유저의 아이디를 이용하여 roundMember 생성
        Member updatedMember = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RoundMember roundMember = roundMemberRepository.findById(roundMemberId)
                .orElseThrow(() -> new CustomException(ROUND_MEMBER_NOT_FOUND));
        // roundId와 해당 회고의 roundId가 일치하지 않으면 에러 발생
        validateRoundMatch(roundId, roundMember.getRound().getId());

        roundMember.update(updatedRound, updatedMember, request.retrospect());
        roundMemberRepository.save(roundMember);
    }


    /**
     * 회고를 삭제합니다.
     *
     * @param roundId 수정할 회고의 회차 ID
     * @param roundMemberId 삭제할 회고(round member) ID
     */
    @Transactional(readOnly = false)
    public void deleteRetrospect(Long roundId, Long roundMemberId) {

        roundRepository.findById(roundId).orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        RoundMember roundMember = roundMemberRepository.findById(roundMemberId)
                .orElseThrow(() -> new CustomException(ROUND_MEMBER_NOT_FOUND));
        validateRoundMatch(roundId, roundMember.getRound().getId());

        roundMemberRepository.delete(roundMember);
    }


    /**
     * 특정 회고를 조회합니다.
     *
     * @param roundId 조회할 회고의 라운드 ID
     * @param roundMemberId 조회할 회고의 ID
     * @return 해당 회고를 작성한 멤버의 아이디와 회고 내용
     */
    public RetrospectDTO getRetrospect(Long roundId, Long roundMemberId) {

        roundRepository.findById(roundId).orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        RoundMember roundMember = roundMemberRepository.findById(roundMemberId)
                .orElseThrow(() -> new CustomException(ROUND_MEMBER_NOT_FOUND));
        validateRoundMatch(roundId, roundMember.getRound().getId());

        return RetrospectDTO.from(roundMember);
    }


    /**
     * 해당 라운드의 모든 회고를 조회합니다.
     *
     * @param roundId 조회할 라운드 ID
     * @return 해당 라운드의 모든 회고를 리스트 형식으로 반환
     */
    public List<RetrospectDTO> getAllRetrospects(Long roundId) {

        return roundMemberRepository.findRoundMemberByRoundId(roundId).stream().map(RetrospectDTO::from).toList();
    }


    // =========== Helper =========== //

    /**
     * 입력한 회차와 회고의 회차가 일치하는지 확인합니다.
     *
     * @param roundId 입력한 회차의 ID
     * @param roundMemberId 회고의 ID
     */
    private void validateRoundMatch(Long roundId, Long roundMemberId) {

        if (!Objects.equals(roundId, roundMemberId)) {
            throw new CustomException(ROUND_MISMATCH);
        }
    }
}
