package com.standout.sopang.mypage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.standout.sopang.member.dto.MemberDTO;
import com.standout.sopang.order.config.OrderConvert;
import com.standout.sopang.order.dto.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.standout.sopang.member.vo.MemberVO;
import com.standout.sopang.mypage.dao.MyPageDAO;
import com.standout.sopang.order.vo.OrderVO;

@Service("myPageService")
@Transactional(propagation=Propagation.REQUIRED)
public class MyPageServiceImpl  implements MyPageService{
	@Autowired
	private MyPageDAO myPageDAO;

	@Autowired
	OrderConvert convertDTO;
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	MemberDTO memberDTO;


	// Map<String, String> dateMap,
	//주문목록
	public List<OrderDTO> listMyOrderHistory(Map dateMap) throws Exception{
		List<OrderVO> myOrderHistoryList = myPageDAO.selectMyOrderHistoryList(dateMap);
		List<OrderDTO>  myOrderHistoryDTOList	= convertDTO.convertList(myOrderHistoryList);
		return myOrderHistoryDTOList;
	}
	
	//주문취소
	public void cancelOrder(String order_id) throws Exception{
		myPageDAO.updateMyOrderCancel(order_id);
	}
	
	//반품
	public void returnOrder(String order_id) throws Exception{
		myPageDAO.updateMyOrderReturn(order_id);
	}
	
	//교환
	public void exchangeOrder(String order_id) throws Exception{
		myPageDAO.updateMyOrderExchange(order_id);
	}
	
	//내정보
	public MemberDTO myDetailInfo(String member_id) throws Exception{
//		return myPageDAO.selectMyDetailInfo(member_id);
		return modelMapper.map(myPageDAO.selectMyDetailInfo(member_id),MemberDTO.class);
	}
	
	//내 정보 수정
	public MemberDTO modifyMyInfo(Map memberMap) throws Exception{
		 String member_id=(String)memberMap.get("member_id");
		 myPageDAO.updateMyInfo(memberMap);
//		return myPageDAO.selectMyDetailInfo(member_id);
		 return modelMapper.map(myPageDAO.selectMyDetailInfo(member_id),MemberDTO.class);
	}
	
	//회원탈퇴
	public void deleteMember(String member_id) throws Exception{
		myPageDAO.deleteMember(member_id);
	}
	
}
