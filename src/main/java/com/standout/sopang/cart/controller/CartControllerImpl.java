package com.standout.sopang.cart.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.cart.dto.CartDTO;
import com.standout.sopang.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.cart.service.CartService;
import com.standout.sopang.cart.vo.CartVO;
import com.standout.sopang.common.base.BaseController;
import com.standout.sopang.goods.vo.GoodsVO;
import com.standout.sopang.member.vo.MemberVO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("cartController")
@RequestMapping(value="/cart")
public class CartControllerImpl extends BaseController implements CartController{
	@Autowired
	private CartService cartService;
	@Autowired
	private CartDTO cartDTO;
	@Autowired
	private MemberDTO memberDTO;
	
	//장바구니

	@Override
	@RequestMapping(value="/myCartList" ,method = RequestMethod.GET)
	public String myCartMain(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberInfo");
		//회원정보에 맞는 장바구니 리스트를 불러온다.
		if (memberDTO != null) {
			String member_id = memberDTO.getMember_id();

			cartDTO.setMember_id(member_id);
			Map<String, List> cartMap = cartService.myCartList(cartDTO);
			session.setAttribute("cartMap", cartMap);
			return "/cart/myCartList";
		} else {
			return "redirect:/member/login";
		}
	}

//	@RequestMapping(value="/myCartList" ,method = RequestMethod.GET)
//	public String myCartMain(HttpServletRequest request, HttpServletResponse response, Model model,
//	RedirectAttributes redirectAttributes)  throws Exception {
//
//		HttpSession session=request.getSession();
//		MemberVO memberVO=(MemberVO)session.getAttribute("memberInfo");
//
//		//회원정보에 맞는 장바구니 리스트를 불러온다.
//		if (memberVO != null) {
//			String member_id = memberVO.getMember_id();
//
//			cartVO.setMember_id(member_id);
//			Map<String, List> cartMap = cartService.myCartList(cartVO);
//			session.setAttribute("cartMap", cartMap);
//			return "/cart/myCartList";
//		} else {
//			return "redirect:/member/login.do";
//		}
//	}


	//장바구니 추가
	@RequestMapping(value="/addGoodsInCart" ,method = RequestMethod.POST,produces = "application/text; charset=utf8")
	public  @ResponseBody String addGoodsInCart(@RequestParam("goods_id") int goods_id,
			                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		HttpSession session=request.getSession();
		memberDTO=(MemberDTO)session.getAttribute("memberInfo");
		String member_id=memberDTO.getMember_id();
		
		//회원정보와 추가하고자하는 상품id로 장바구니 중복체크 후 boolean형 isAreadyExisted로 리턴값 저장
		cartDTO.setMember_id(member_id);
		cartDTO.setGoods_id(goods_id);
		boolean isAreadyExisted=cartService.findCartGoods(cartDTO);
		
		//중복될경우 already_existed return, 외의 경우 add_success return
		if(isAreadyExisted==true){return "already_existed";}
		else{cartService.addGoodsInCart(cartDTO);return "add_success";}
		// 문자열로 나가는데 어떻게 처리할지?

	}
	
	
	
	//장바구니 삭제
	@RequestMapping(value="/removeCartGoods" ,method = RequestMethod.POST)
	public String removeCartGoods(@RequestParam("cart_id") int cart_id,
			                          HttpServletRequest request, HttpServletResponse response,
								  Model model,RedirectAttributes redirectAttributes)  throws Exception{

		//@RequestParam받은 cart_id 상품을 삭제 후 myCartList로 redirect
		cartService.removeCartGoods(cart_id);
		return "redirect:/cart/myCartList";
	}

	//장바구니 수정
	//나중에 @RequestParam Map으로 처리할 수 있는지 확인
	@RequestMapping(value="/modifyCartQty" ,method = RequestMethod.POST)
	public @ResponseBody String  modifyCartQty(@RequestParam("goods_id") int goods_id,
			                                   @RequestParam("cart_goods_qty") int cart_goods_qty,
			                                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberDTO=(MemberDTO)session.getAttribute("memberInfo");
		String member_id=memberDTO.getMember_id();
		
		//member_id의 @RequestParam받은 goods_id와 cart_goods_qty에 수정내용을 반영하고 결과값을 리턴받아 result에 저장
		cartDTO.setMember_id(member_id);
		cartDTO.setGoods_id(goods_id);
		cartDTO.setCart_goods_qty(cart_goods_qty);
		boolean result=cartService.modifyCartQty(cartDTO);
		
		//완료시 modify_success, 외의 경우 modify_failed를 리턴.
		if(result==true){return "modify_success";}
		else{return "modify_failed";	}
		
	}
	
	
}
