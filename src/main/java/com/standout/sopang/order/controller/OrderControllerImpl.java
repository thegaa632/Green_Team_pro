package com.standout.sopang.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.goods.dto.GoodsDTO;
import com.standout.sopang.member.dto.MemberDTO;
import com.standout.sopang.order.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.standout.sopang.common.base.BaseController;
import com.standout.sopang.order.service.ApiService01;
import com.standout.sopang.order.service.OrderService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("orderController")
@RequestMapping(value = "/order")
public class OrderControllerImpl extends BaseController implements OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDTO orderDTO;
	@Autowired
	private ApiService01 apiService01;

	// �����ֹ�
	@RequestMapping(value = "/orderEachGoods", method = RequestMethod.POST)
	public String orderEachGoods(@ModelAttribute("orderDTO") OrderDTO _orderDTO, HttpServletRequest request,
								 HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {

		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		session = request.getSession();

		// �α��� ���� üũ
		Boolean isLogOn = (Boolean) session.getAttribute("isLogOn");
		String action = (String) session.getAttribute("action");

		// ������ �α��� ������ ���� �ֹ����� ����
		if (isLogOn == null || isLogOn == false) {
			session.setAttribute("orderInfo", _orderDTO);
			session.setAttribute("action", "/order/orderEachGoods.do");
			return "redirect:/member/login";
		}
		// �α׾ƿ� ������ ��� �α��� ȭ������ �̵�
		else {
			if (action != null && action.equals("/order/orderEachGoods.do")) {
				orderDTO = (OrderDTO) session.getAttribute("orderInfo");
				session.removeAttribute("action");
			} else {
				orderDTO = _orderDTO;
			}
		}

		// myOrderList�� ������ ��ǰ���� orderDTO�� �����̷�Ʈ.
		List myOrderList = new ArrayList<OrderDTO>();
		myOrderList.add(orderDTO);
		session.setAttribute("myOrderList", myOrderList);

		// + ȸ�������� �Բ� �����̷�Ʈ.
		MemberDTO memberInfo = (MemberDTO) session.getAttribute("memberInfo");
		session.setAttribute("orderer", memberInfo);

		return "/order/orderEachGoods";
	}


	// �����ֹ�
	@RequestMapping(value = "/orderAllCartGoods", method = RequestMethod.POST)
	public String orderAllCartGoods(@RequestParam("cart_goods_qty") String[] cart_goods_qty,
									HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		List myOrderList = new ArrayList<OrderDTO>();

		//��ٱ��� ����Ʈ�� �޾� ����
		Map cartMap = (Map) session.getAttribute("cartMap");
		List<GoodsDTO> myGoodsList = (List<GoodsDTO>) cartMap.get("myGoodsList");

		//ȸ�������� �޾� ����
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberInfo");

		//��ǰ:�������� input�� ������ �Ѱ�� ������ �̿��� ����.
		//cart_goods_qty, ���� input�� ����ŭ for��.
		for (int i = 0; i < cart_goods_qty.length; i++) {
			//��ǰ:������ ���� Ȯ��.
			String[] cart_goods = cart_goods_qty[i].split(":");
			for (int j = 0; j < myGoodsList.size(); j++) {
				//��ǰ ��ü get
				GoodsDTO goodsDTO = myGoodsList.get(j);
				//��ǰ id get
				int goods_id = goodsDTO.getGoods_id();
				//goodsid�� ���޹��� ��ǰ id���� ������
				if (goods_id == Integer.parseInt(cart_goods[0])) {
					//�ֹ���ü�� �����Ѵ�.
					OrderDTO _orderDTO = new OrderDTO();
					//�ش��ǰ title����
					String goods_title = goodsDTO.getGoods_title();
					//�ش��ǰ ���� ����
					int goods_sales_price = goodsDTO.getGoods_sales_price();
					//�ش��ǰ fileName�� ������
					String goods_fileName = goodsDTO.getGoods_fileName();
					//�ֹ���ü�� set
					_orderDTO.setGoods_id(goods_id);
					_orderDTO.setGoods_title(goods_title);
					_orderDTO.setGoods_sales_price(goods_sales_price);
					_orderDTO.setGoods_fileName(goods_fileName);
					_orderDTO.setOrder_goods_qty(Integer.parseInt(cart_goods[1]));
					//�ϼ��� �ֹ���ü�� myOrderList�� �׾ư���.
					myOrderList.add(_orderDTO);
					break;
				}
			}
		}
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberDTO);
		return "/order/orderAllCartGoods";
	}


	@Override
	@RequestMapping(value = "/payToOrderGoods", method = RequestMethod.POST)
	public String payToOrderGoods(Map<String, String> receiverMap, HttpServletRequest request,
								  Model model, RedirectAttributes redirectAttributes, HttpServletResponse response)
			throws Exception {

		//�ֹ��� ������ �����´�.
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("orderer");
		String member_id = memberDTO.getMember_id();
		String orderer_name = memberDTO.getMember_name();
		String orderer_hp = memberDTO.getHp1();

		//�ֹ������� �����´�.
		List<OrderDTO> myOrderList = (List<OrderDTO>) session.getAttribute("myOrderList");

		//������������
		String responseCode = "";
		String responseMsg = "";
		String itemName = "";
		String orderNumber = "";
		int amount = 0;
		//�ֹ������� for�� ������ myOrderList�� ������������ ��´�.
		for (int i = 0; i < myOrderList.size(); i++) {
			OrderDTO orderDTO = (OrderDTO) myOrderList.get(i);
			orderDTO.setMember_id(member_id);
			orderDTO.setReceiver_name(receiverMap.get("receiver_name"));
			orderDTO.setReceiver_hp1(receiverMap.get("receiver_hp1"));
			orderDTO.setDelivery_address(receiverMap.get("delivery_address"));

			//���� ������ �ʿ��� �� ������ �ּ����� ���ܵд�.
			orderDTO.setPay_method(receiverMap.get("pay_method"));
			orderDTO.setCard_com_name(receiverMap.get("card_com_name"));
			orderDTO.setCard_pay_month(receiverMap.get("card_pay_month"));
			orderDTO.setPay_orderer_hp_num(receiverMap.get("pay_orderer_hp_num"));
			orderDTO.setOrderer_hp(orderer_hp);



			//payup form �߰�
			if(myOrderList.size() == 1) {
				itemName = orderDTO.getGoods_title();
			}else if(myOrderList.size() > 1){
				itemName = orderDTO.getGoods_title() +" �� " + i + "��";
			}
			orderNumber += String.valueOf(orderDTO.getOrder_seq_num());
			amount += orderDTO.getGoods_sales_price();

//         amount = String.valueOf(orderVO.getGoods_sales_price());
			myOrderList.set(i, orderDTO);
		}

		String merchantId = "himedia";
		String expireMonth = receiverMap.get("expireMonth");
		String expireYear = receiverMap.get("expireYear");
		String birthday = receiverMap.get("birthday");
		String cardPw = receiverMap.get("cardPw");
		String userName = memberDTO.getMember_name();

		String cardNo = receiverMap.get("cardNo");
		String quota = receiverMap.get("card_pay_month");
		String apiCertKey = "ac805b30517f4fd08e3e80490e559f8e";
		String timestamp = "2023020400000000";
		String signature = apiService01.encrypt(merchantId + "|" + orderNumber + "|" + amount + "|" + apiCertKey + "|" + timestamp) ;


		String url = "https://api.testpayup.co.kr/v2/api/payment/"+merchantId+"/keyin2";
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		map.put("merchantId", merchantId);
		map.put("orderNumber", orderNumber);
		map.put("expireMonth", expireMonth);
		map.put("expireYear", expireYear);
		map.put("birthday", birthday);
		map.put("cardPw", cardPw);
		map.put("amount", Integer.toString(amount));
		map.put("itemName", itemName);
		map.put("userName", userName);
		map.put("cardNo", cardNo);
		map.put("quota", quota);
		map.put("signature", signature);
		map.put("timestamp", timestamp);

		System.out.println("�����°� = " + map.toString());
		returnMap = apiService01.restApi(map, url);
		System.out.println("dbȮ��"+ returnMap.toString());

		//���̾� �ŷ���ȣ
		String transactionId = (String) returnMap.get("transactionId");

		responseCode = (String) returnMap.get("responseCode");
		responseMsg = (String) returnMap.get("responseMsg");


		if("0000".equals(responseCode)) {
			System.out.println("�����߽��ϴ�.");

			//����������, �ֹ������� �ֹ����̺� �ݿ��Ѵ�.
			orderService.addNewOrder(myOrderList);
			session.setAttribute("returnMap", returnMap);

			//�Ϸ� �� listMyOrderHistory�� ����.
			return "redirect:/mypage/listMyOrderHistory.do";
		}else {
			System.out.println("�����߽��ϴ�.");

			model.addAttribute("responseMsg", responseMsg);
			//���н� �ٽ� �ֹ��������� �̵�
			return "/order/payFail";
		}
	}



	//��������
	@Override
	@RequestMapping(value="/payFail",method = RequestMethod.POST)
	public String payFail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/order/payFail";
	}

}