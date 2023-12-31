package com.standout.sopang.member.dto;

import lombok.*;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component("memberDTO")
public class MemberDTO {

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

//	public String getMember_name() {
//		return member_name;
//	}
//	public void setMember_name(String member_name) {
//		this.member_name = member_name;
//	}
//	public String getMember_id() {
//		return member_id;
//	}
//	public void setMember_id(String member_id) {
//		this.member_id = member_id;
//	}
//	public String getMember_pw() {
//		return member_pw;
//	}
//	public void setMember_pw(String member_pw) {
//		this.member_pw = member_pw;
//	}
//	public String getHp1() {
//		return hp1;
//	}
//	public void setHp1(String hp1) {
//		this.hp1 = hp1;
//	}
//	public String getZipcode() {
//		return zipcode;
//	}
//	public void setZipcode(String zipcode) {
//		this.zipcode = zipcode;
//	}
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}
//	public String getSubaddress() {
//		return subaddress;
//	}
//	public void setSubaddress(String subaddress) {
//		this.subaddress = subaddress;
//	}
//	public String getSopang_money() {
//		return sopang_money;
//	}
//	public void setSopang_money(String sopang_money) {
//		this.sopang_money = sopang_money;
//	}
//	public String getJoinDate() {
//		return joinDate;
//	}
//	public void setJoinDate(String joinDate) {
//		this.joinDate = joinDate;
//	}
//	public String getDel_yn() {
//		return del_yn;
//	}
//	public void setDel_yn(String del_yn) {
//		this.del_yn = del_yn;
//	}
//

}

