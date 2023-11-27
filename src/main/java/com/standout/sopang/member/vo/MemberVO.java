package com.standout.sopang.member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component("memberVO")
public class MemberVO {
	
	private String member_name;
	private String member_id;
	private String member_pw;
	private String hp1;
	private String zipcode;
	private String address;
	private String subaddress;
	private String sopang_money;
	private String joinDate;
	private String del_yn;
	

	
	
}

