package com.standout.sopang.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.standout.sopang.member.dto.MemberDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.member.vo.MemberVO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface MemberController {
	//로그인
	public String login(@RequestParam Map<String, String> loginMap, HttpServletRequest request,
							  HttpServletResponse response, Model model) throws Exception;

	//회원가입
	public ResponseEntity addMember(@ModelAttribute("member") MemberDTO member, HttpServletRequest request,
									HttpServletResponse response) throws Exception;

	//아이디 중복확인
	public ResponseEntity overlapped(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	//로그아웃
	public String logout(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response)
			throws Exception;
}
