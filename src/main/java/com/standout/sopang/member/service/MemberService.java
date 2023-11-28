package com.standout.sopang.member.service;

import java.util.Map;

import com.standout.sopang.member.dto.MemberDTO;
import com.standout.sopang.member.vo.MemberVO;

public interface MemberService {
	//로그인
	public MemberDTO login(Map  loginMap) throws Exception;
	
	//회원가입
	public void addMember(MemberDTO memberDTO) throws Exception;
	
	//아이디 중복확인
	public String overlapped(String id) throws Exception;
}
