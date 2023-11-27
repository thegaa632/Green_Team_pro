package com.standout.sopang.mypage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.standout.sopang.member.dto.MemberDTO;
import com.standout.sopang.member.vo.MemberVO;
import com.standout.sopang.order.dto.OrderDTO;
import com.standout.sopang.order.vo.OrderVO;

public interface MyPageService{

	//주문목록
	public List<OrderDTO> listMyOrderHistory(Map dateMap) throws Exception;

	//주문취소
	public void cancelOrder(String order_id) throws Exception;

	//반품
	public void returnOrder(String order_id) throws Exception;
	
	//교환
	public void exchangeOrder(String order_id) throws Exception;

	//내정보
	public MemberDTO myDetailInfo(String member_id) throws Exception;
	
	//내 정보 수정
	public MemberDTO modifyMyInfo(Map memberMap) throws Exception;
	
	//회원탈퇴
	public void  deleteMember(String member_id) throws Exception;
	
	
	

}
