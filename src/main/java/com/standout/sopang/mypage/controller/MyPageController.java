package com.standout.sopang.mypage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface MyPageController {

	//주문목록
	public String listMyOrderHistory(@RequestParam Map<String, String> dateMap, Model model, RedirectAttributes redirectAttributes,
									 HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//주문취소
	public String cancelMyOrder(@RequestParam("order_id")  String order_id,Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//반품
	public String returnMyOrder(@RequestParam("order_id")  String order_id,Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//교환
	public String exchangeMyOrder(@RequestParam("order_id")  String order_id,
								  Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//내정보
	public String myDetailInfo(HttpServletRequest request, Model model,HttpServletResponse response)  throws Exception;
	
	//내 정보 수정
	public ResponseEntity modifyMyInfo(@RequestParam("member_pw")  String member_pw,
			@RequestParam("hp1")  String hp1,
			@RequestParam("zipcode")  String zipcode,
			@RequestParam("address")  String address,
			@RequestParam("subaddress")  String subaddress,
					            HttpServletRequest request, HttpServletResponse response)  throws Exception;

	//회원탈퇴
	public ResponseEntity deleteMember(@RequestParam("member_id")  String member_id,  HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
