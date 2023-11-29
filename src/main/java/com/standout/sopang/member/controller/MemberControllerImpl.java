package com.standout.sopang.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.member.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.standout.sopang.common.base.BaseController;
import com.standout.sopang.member.service.MemberService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller("memberController")
@RequestMapping(value = "/member")
public class MemberControllerImpl extends BaseController implements MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberDTO memberDTO;

	@RequestMapping(value="/login" ,method = RequestMethod.GET)
	public String login(){
		return "/member/login";
	}
	//로그인
	@Override
	@RequestMapping(value="/login" ,method = RequestMethod.POST)
	public String login(@RequestParam Map<String, String> loginMap, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		memberDTO = memberService.login(loginMap);
		log.info("memberDTO : " + memberDTO);
		//memberDTO가 존재할 경우
		if (memberDTO != null && memberDTO.getMember_id() != null) {
			HttpSession session = request.getSession();


			//로그인 여부 isLogOn와 회원정보 memberInfo를 세션에 저장한다.
			session.setAttribute("isLogOn", true);
			session.setAttribute("memberInfo", memberDTO);
			return "/main/main";
			//메인페이지로 이동.

		} else { //memberVO가 존재하지않을 경우 message를 담아 return + login페이지로 이동
			String message = "아이디나  비밀번호가 틀립니다. 다시 로그인해주세요";
			model.addAttribute("message", message);
		}
		return "/member/login";
	}


	@RequestMapping(value="/join" ,method = RequestMethod.GET)
	public String join(){
		return "/member/join";
	}


	//회원가입
	@Override
	@RequestMapping(value="/join" ,method = RequestMethod.POST)
	public ResponseEntity addMember(@ModelAttribute("memberDTO") MemberDTO _member,
									HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");

		try {
			//회원가입을 try, addMember 성공시 안내문구와 함께  login페이지로 이동한다.
			memberService.addMember(_member);
			message  = "<script>";
			message +=" alert('sopang에 오신걸 환영합니다!');";
			message += " location.href='"+request.getContextPath()+"/member/login';";
			message += " </script>";

		}catch(Exception e) {
			//오류발생시, 회원가입페이지로 재이동
			message  = "<script>";
			message += " location.href='"+request.getContextPath()+"/member/join';";
			message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);

		//각 케이스에 따른 위 설정값 return
		return resEntity;
	}




	//아이디 중복확인
	@Override
	@RequestMapping(value="/overlapped" ,method = RequestMethod.POST)
	public ResponseEntity overlapped(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseEntity resEntity = null;

		//overlapped의 경과를 매핑해 return 한다.
		String result = memberService.overlapped(id);
		resEntity = new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}



	//로그아웃

	//한번 더 체크하기
	@Override
	@RequestMapping(value="/logout" ,method = RequestMethod.GET)
	public String logout(Model model, RedirectAttributes redirectAttributes,
						 HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		//세션 설정 초기화 및 메인페이지 이동.
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		return "redirect:/main/main";
	}

}