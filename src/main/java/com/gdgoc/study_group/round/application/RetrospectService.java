package com.gdgoc.study_group.round.application;

import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.dao.RoundRepository;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.roundMember.dao.RoundMemberRepository;
import com.gdgoc.study_group.roundMember.domain.RoundMember;
import com.gdgoc.study_group.roundMember.dto.RetrospectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createRetrospect(Long roundId, RetrospectRequest request) {

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
     * @param roundId 수정할 회고의 회차
     * @param roundMemberId 수정할 회고가 있는 round member의 id
     * @param request 멤버의 아이디, 수정할 회고의 내용
     */
    @Transactional(readOnly = false)
    public void updateRetrospect(Long roundId, Long roundMemberId, RetrospectRequest request) {
        Round updatedRound = roundRepository.findById(roundId)
                .orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        // TODO: dto에서 멤버의 ID를 받아오는 것이 아니라 현재 로그인한 유저의 아이디를 이용하여 roundMember 생성
        Member updatedMember = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RoundMember roundMember = roundMemberRepository.findById(roundMemberId)
                .orElseThrow(() -> new CustomException(ROUND_MEMBER_NOT_FOUND));
        // roundId와 해당 회고의 roundId가 일치하지 않으면 에러 발생
        if (!Objects.equals(roundMember.getRound().getId(), roundId)) {
            throw new CustomException(ROUND_MISMATCH);
        }

        roundMember.update(updatedRound, updatedMember, request.retrospect());
        roundMemberRepository.save(roundMember);
    }
}
