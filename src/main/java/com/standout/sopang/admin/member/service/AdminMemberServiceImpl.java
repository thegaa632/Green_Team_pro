package com.standout.sopang.admin.member.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.standout.sopang.config.ConvertList;
import com.standout.sopang.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.standout.sopang.admin.member.dao.AdminMemberDAO;
import com.standout.sopang.member.vo.MemberVO;

@Service("adminMemberService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminMemberServiceImpl implements AdminMemberService {
	@Autowired
	private AdminMemberDAO adminMemberDAO;

	@Autowired
	ConvertList convertList;
	
	//회원관리
	public ArrayList<MemberDTO> listMember(HashMap condMap) throws Exception{

		ArrayList<MemberDTO> memberDTOList	 =convertList.memberConvertDTO(adminMemberDAO.listMember(condMap));
//		return adminMemberDAO.listMember(condMap);
		return memberDTOList;
	}
	
}
