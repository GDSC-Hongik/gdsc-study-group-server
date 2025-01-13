package com.gdgoc.study_group.member.service;

import com.gdgoc.study_group.member.dao.MemberRepository;

public class MemberService {
  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
}
