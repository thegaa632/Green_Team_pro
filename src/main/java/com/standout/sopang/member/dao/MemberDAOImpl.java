package com.standout.sopang.member.dao;

import java.util.HashMap;
import java.util.Map;

import com.standout.sopang.member.dto.MemberDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.standout.sopang.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl  implements MemberDAO{
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	MemberDTO memberDTO;
	//로그인
	@Override
	public MemberVO login(Map loginMap) throws DataAccessException{
		MemberVO member=sqlSession.selectOne("mapper.member.login",loginMap);

	   return member;
	}
	
	//회원가입
	@Override
	public void insertNewMember(MemberDTO memberDTO) throws DataAccessException{
		sqlSession.insert("mapper.member.insertNewMember",memberDTO);
	}

	//아이디 중복확인
	@Override
	public String selectOverlappedID(String id) throws DataAccessException {
		String result =  sqlSession.selectOne("mapper.member.selectOverlappedID",id);
		return result;
	}
	
	
}
