package com.standout.sopang.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.member.dto.MemberDTO;
import com.standout.sopang.order.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.member.vo.MemberVO;
import com.standout.sopang.order.service.ApiService01;
import com.standout.sopang.order.service.OrderService;
import com.standout.sopang.order.vo.OrderVO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EasyPayController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ApiService01 apiService01;

	//EasyPayPopup(RestController)는 view기능이 없어 Controller를 추가함.
	@RequestMapping(value = "/test/kakaoPay")
	public String kakaoPay(@RequestParam Map<String, String> map, HttpServletRequest request, Model model,
						   HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {

		response.setContentType("text/html;charset=UTF-8");

		System.out.println(map.toString());

		//주문정보를 가져온다.
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("orderer");
		String member_id = memberDTO.getMember_id();
		String orderer_name = memberDTO.getMember_name();
		String orderer_hp = memberDTO.getHp1();
		List<OrderDTO> myOrderList = (List<OrderDTO>) session.getAttribute("myOrderList");

		//주문정보를 for로 돌리며 myOrderList에 수령자정보를 담는다.
		for (int i = 0; i < myOrderList.size(); i++) {
			OrderDTO orderDTO = (OrderDTO) myOrderList.get(i);
			orderDTO.setMember_id(member_id);
			orderDTO.setReceiver_name(map.get("receiver_name"));
			orderDTO.setReceiver_hp1(map.get("receiver_hp1"));
			orderDTO.setDelivery_address(map.get("delivery_address"));

			//추후 결제시 필요할 수 있으니 주석으로 남겨둔다.
			orderDTO.setPay_method(map.get("pay_method"));
			orderDTO.setCard_com_name(map.get("card_com_name"));
			orderDTO.setCard_pay_month(map.get("card_pay_month"));
			orderDTO.setPay_orderer_hp_num(map.get("pay_orderer_hp_num"));
			orderDTO.setOrderer_hp(orderer_hp);

			myOrderList.set(i, orderDTO);
		}


		//인증데이터로 결제요청하기
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String res_cd = (String) map.get("res_cd");
		String enc_info = (String) map.get("enc_info");
		String enc_data = (String) map.get("enc_data");
		String card_pay_method = (String) map.get("card_pay_method");
		String ordr_idxx = (String) map.get("ordr_idxx");
		String merchantId = "himedia";
		String url = "https://api.testpayup.co.kr/ep/api/kakao/" + merchantId + "/pay";

		returnMap = apiService01.restApi(map, url);


//      String responseCode = "0000";

		if ("0000".equals(res_cd)) {
			//수령자정보, 주문정보를 주문테이블에 반영한다.
			orderService.addNewOrder(myOrderList);
			System.out.println(map.get("res_cd"));
			System.out.println(map.get("res_msg"));

		} else {
			model.addAttribute("responseMsg", "카카오 인증실패");
			return "/order/payFail";
		}


		return "redirect:/mypage/listMyOrderHistory";
	}


	@RequestMapping(value = "/test/naverPay")
	public String naverPay(@RequestParam Map<String, String> map, HttpServletRequest request,
						   HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {

		response.setContentType("text/html;charset=UTF-8");

		ModelAndView mav = new ModelAndView();
		System.out.println(map.toString());

		//주문정보를 가져온다.
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("orderer");
		String member_id = memberDTO.getMember_id();
		String orderer_name = memberDTO.getMember_name();
		String orderer_hp = memberDTO.getHp1();
		List<OrderDTO> myOrderList = (List<OrderDTO>) session.getAttribute("myOrderList");

		//주문정보를 for로 돌리며 myOrderList에 수령자정보를 담는다.
		for (int i = 0; i < myOrderList.size(); i++) {
			OrderDTO orderDTO = (OrderDTO) myOrderList.get(i);
			orderDTO.setMember_id(member_id);
			orderDTO.setReceiver_name(map.get("receiver_name"));
			orderDTO.setReceiver_hp1(map.get("receiver_hp1"));
			orderDTO.setDelivery_address(map.get("delivery_address"));

			//추후 결제시 필요할 수 있으니 주석으로 남겨둔다.
			orderDTO.setPay_method(map.get("pay_method"));
			orderDTO.setCard_com_name(map.get("card_com_name"));
			orderDTO.setCard_pay_month(map.get("card_pay_month"));
			orderDTO.setPay_orderer_hp_num(map.get("pay_orderer_hp_num"));
			orderDTO.setOrderer_hp(orderer_hp);

			myOrderList.set(i, orderDTO);
		}


		//인증데이터로 결제요청하기
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String res_cd = (String) map.get("res_cd");
		String enc_info = (String) map.get("enc_info");
		String enc_data = (String) map.get("enc_data");
		String card_pay_method = (String) map.get("card_pay_method");
		String ordr_idxx = (String) map.get("ordr_idxx");
		String merchantId = "himedia";
		String url = "https://api.testpayup.co.kr/ep/api/naver/" + merchantId + "/pay";

		System.out.println(map.toString());
		returnMap = apiService01.restApi(map, url);


		System.out.println(returnMap.toString());
		String responseCode = (String) returnMap.get("responseCode");


			//수령자정보, 주문정보를 주문테이블에 반영한다.
			orderService.addNewOrder(myOrderList);
			System.out.println(map.get("res_cd"));
			System.out.println(map.get("res_msg"));

//			model.addAttribute("responseMsg", "네이버 인증실패");


		return "redirect:/mypage/listMyOrderHistory";
	}

	@RequestMapping(value = "/test/sopangPay")
	public String sopangPay(@RequestParam Map<String, String> map, HttpServletRequest request,
							HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {

		response.setContentType("text/html;charset=UTF-8");

//		ModelAndView mav = new ModelAndView();
		System.out.println(map.toString());
		System.out.println("1소팡페이 진입1");

		//주문정보를 가져온다.
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("orderer");
		String member_id = memberDTO.getMember_id();
		String orderer_name = memberDTO.getMember_name();
		String orderer_hp = memberDTO.getHp1();
		List<OrderDTO> myOrderList = (List<OrderDTO>) session.getAttribute("myOrderList");

		//주문정보를 for로 돌리며 myOrderList에 수령자정보를 담는다.
		for (int i = 0; i < myOrderList.size(); i++) {
			OrderDTO orderDTO = (OrderDTO) myOrderList.get(i);
			orderDTO.setMember_id(member_id);
			orderDTO.setReceiver_name(map.get("receiver_name"));
			orderDTO.setReceiver_hp1(map.get("receiver_hp1"));
			orderDTO.setDelivery_address(map.get("delivery_address"));

			//추후 결제시 필요할 수 있으니 주석으로 남겨둔다.
			orderDTO.setPay_method(map.get("pay_method"));
			orderDTO.setCard_com_name(map.get("card_com_name"));
			orderDTO.setCard_pay_month(map.get("card_pay_month"));
			orderDTO.setPay_orderer_hp_num(map.get("pay_orderer_hp_num"));
			orderDTO.setOrderer_hp(orderer_hp);

			myOrderList.set(i, orderDTO);
		}
		System.out.println("myOrderList : " + myOrderList);

		orderService.addNewOrder(myOrderList);
		System.out.println(map.get("res_cd"));
		System.out.println(map.get("res_msg"));

		return "redirect:/mypage/listMyOrderHistory";
	}
}