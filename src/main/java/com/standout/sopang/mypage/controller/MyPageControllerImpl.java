package com.standout.sopang.mypage.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.member.dto.MemberDTO;
import com.standout.sopang.order.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.common.base.BaseController;
import com.standout.sopang.member.vo.MemberVO;
import com.standout.sopang.mypage.service.MyPageService;
import com.standout.sopang.order.vo.OrderVO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("myPageController")
@RequestMapping(value="/mypage")
public class MyPageControllerImpl extends BaseController  implements MyPageController{
	@Autowired
	private MyPageService myPageService;
	
	@Autowired
	private MemberDTO memberDTO;
	
	//주문목록
	@Override
	@RequestMapping(value="/listMyOrderHistory" ,method = RequestMethod.GET)
	public String listMyOrderHistory(@RequestParam Map<String, String> dateMap, Model model, RedirectAttributes redirectAttributes,
										   HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session=request.getSession();

		//memberInfo의 member_id get
		memberDTO=(MemberDTO)session.getAttribute("memberInfo");
			if(memberDTO != null) {
			String  member_id=memberDTO.getMember_id();

			//조회기간 fixedSearchPeriod get
			String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
			//조회 기간 초기화
			String beginDate=null,endDate=null;
			String [] tempDate=calcSearchPeriod(fixedSearchPeriod).split(",");
			beginDate=tempDate[0];
			endDate=tempDate[1];
			//조회기간과 member_id를 dateMap에 put해 조회
			dateMap.put("beginDate", beginDate);
			dateMap.put("endDate", endDate);
			dateMap.put("member_id", member_id);
			List<OrderDTO> myOrderHistList=myPageService.listMyOrderHistory(dateMap);
			//검색일자를 년,월,일로 분리해서 화면에 전달
			String beginDate1[]=beginDate.split("-");
			String endDate1[]=endDate.split("-");

				model.addAttribute("beginYear",beginDate1[0]);
				model.addAttribute("beginYear",beginDate1[1]);
				model.addAttribute("beginYear",beginDate1[2]);
				model.addAttribute("endYear",endDate1[0]);
				model.addAttribute("endYear",endDate1[1]);
				model.addAttribute("endYear",endDate1[2]);
				model.addAttribute("myOrderHistList", myOrderHistList);

			return "/mypage/listMyOrderHistory";
		}
		else {
			return "redirect:/member/login";
		}
	}
	
	
	//주문취소
	@Override
	@RequestMapping(value="/cancelMyOrder" ,method = RequestMethod.POST)
	public String cancelMyOrder(@RequestParam("order_id")  String order_id,Model model,RedirectAttributes redirectAttributes,
			                         HttpServletRequest request, HttpServletResponse response)  throws Exception {
//		ModelAndView mav = new ModelAndView();
		//주문 id order_id로 db삭제 후 cancel_order message 리턴
		myPageService.cancelOrder(order_id);
		model.addAttribute("message", "cancel_order");
//		mav.setViewName("redirect:/mypage/listMyOrderHistory.do");
		return "redirect:/mypage/listMyOrderHistory";
	}


	@Override
	@RequestMapping(value="/returnMyOrder" ,method = RequestMethod.POST)
	public String returnMyOrder(String order_id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//주문 id order_id로 db삭제 후 returning_goods message 리턴
		myPageService.returnOrder(order_id);
		model.addAttribute("message", "returning_goods");
		return "redirect:/mypage/listMyOrderHistory";
	}
	//반품
//	@Override
//	@RequestMapping(value="/returnMyOrder" ,method = RequestMethod.POST)
//	public String returnMyOrder(@RequestParam("order_id")  String order_id,
//								RedirectAttributes redirectAttributes,Model model ,HttpServletRequest request, HttpServletResponse response)  throws Exception {
//		//주문 id order_id로 db삭제 후 returning_goods message 리턴
//		myPageService.returnOrder(order_id);
//		model.addAttribute("message", "returning_goods");
//		return "redirect:/mypage/listMyOrderHistory.do";
//	}
	
	
	//교환

	@Override
	@RequestMapping(value="/exchangeMyOrder" ,method = RequestMethod.POST)
	public String exchangeMyOrder(String order_id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//주문 id order_id로 db삭제 후 exchange_goods message 리턴
		myPageService.exchangeOrder(order_id);
		model.addAttribute("message", "exchange_goods");
		return "redirect:/mypage/listMyOrderHistory";
	}

	
	//내정보

	@Override
	@RequestMapping(value="/myDetailInfo" ,method = RequestMethod.GET)
	public String myDetailInfo(HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		//단순히 페이지만 이동, myDetailInfo
		return "/mypage/myDetailInfo";
	}

	
	
	//내 정보 수정
	@Override
	@RequestMapping(value="/modifyMyInfo" ,method = RequestMethod.POST)
	public ResponseEntity modifyMyInfo(
			@RequestParam("member_pw")  String member_pw,
			@RequestParam("hp1")  String hp1,
			@RequestParam("zipcode")  String zipcode,
			@RequestParam("address")  String address,
			@RequestParam("subaddress")  String subaddress,
			               HttpServletRequest request, HttpServletResponse response)  throws Exception {
		Map<String,String> memberMap=new HashMap<String,String>();
		
		HttpSession session=request.getSession();
		memberDTO=(MemberDTO)session.getAttribute("memberInfo");
		String  member_id=memberDTO.getMember_id();
		
		//받아온 정보 memberMap에 put
		memberMap.put("member_pw",member_pw);
		memberMap.put("hp1",hp1);
		memberMap.put("zipcode",zipcode);
		memberMap.put("address",address);
		memberMap.put("subaddress",subaddress);
		memberMap.put("member_id", member_id);
		
		//memberMap을 가지고 db수정
		memberDTO=(MemberDTO)myPageService.modifyMyInfo(memberMap);
		
		//수정된 회원 정보를 다시 세션에 저장한다.
		session.removeAttribute("memberInfo");
		session.setAttribute("memberInfo", memberDTO);
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		
		//완료후 message mod_success 리턴
		message  = "mod_success";
		resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}	
	
	
	//회원탈퇴
	@Override
	@RequestMapping(value="/deleteMember" ,method = RequestMethod.POST)
	public ResponseEntity deleteMember(@RequestParam("member_id")  String member_id, HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		//@RequestParam받은 member_id를 db에서 삭제
		myPageService.deleteMember(member_id);
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		
		//완료 후  message delete_success 리턴
		message  = "delete_success";
		resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
}
