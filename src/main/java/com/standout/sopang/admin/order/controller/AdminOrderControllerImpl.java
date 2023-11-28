package com.standout.sopang.admin.order.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.order.dto.OrderDTO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

import com.standout.sopang.admin.goods.service.AdminGoodsService;
import com.standout.sopang.admin.order.service.AdminOrderService;
import com.standout.sopang.common.base.BaseController;
import com.standout.sopang.goods.vo.GoodsVO;
import com.standout.sopang.goods.vo.ImageFileVO;
import com.standout.sopang.member.vo.MemberVO;
import com.standout.sopang.mypage.controller.MyPageController;
import com.standout.sopang.mypage.service.MyPageService;
import com.standout.sopang.order.vo.OrderVO;

@Controller("adminOrderController")
@RequestMapping(value="/admin/order")
public class AdminOrderControllerImpl extends BaseController  implements AdminOrderController{
	@Autowired
	private AdminOrderService adminOrderService;
	
	//주문목록
	@Override
	@RequestMapping(value="/adminOrderMain" ,method={RequestMethod.GET, RequestMethod.POST})
	public String adminOrderMain(@RequestParam Map<String, String> dateMap, Model model,
			                          HttpServletRequest request, HttpServletResponse response)  throws Exception {

		//fixedSearchPeriod값을 받아 저장
		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
		
		//기간 초기화
		String beginDate=null,endDate=null;

		//페이징 고정 값
		int startRow = 1, pageSize = 10;

		//fixedSearchPeriod값 가공해 dateMap에 put
		String [] tempDate=calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate=tempDate[0];
		endDate=tempDate[1];
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);
		
		//condMap에 put 후 listNewOrder수행.
		HashMap<String,Object> condMap=new HashMap<String,Object>();
		condMap.put("beginDate",beginDate);
		condMap.put("endDate", endDate);
		condMap.put("startRow", startRow);
		condMap.put("pageSize", pageSize);

		List<OrderDTO> newOrderList=adminOrderService.listNewOrder(condMap);
		
		//리턴된 배송리스트 member_list를  mav의 member_list에 부여
		model.addAttribute("newOrderList",newOrderList);

		//날짜형식지정
		String beginDate1[]=beginDate.split("-");
		String endDate2[]=endDate.split("-");
		model.addAttribute("endYear",endDate2[0]);
		model.addAttribute("endYear",endDate2[1]);
		model.addAttribute("endYear",endDate2[2]);
		model.addAttribute("beginMonth",beginDate1[2]);
		model.addAttribute("beginMonth",beginDate1[1]);
		model.addAttribute("beginYear",beginDate1[0]);


		return "/admin/order/adminOrderMain";
	}
	
	
	
	//주문수정 - 배송수정
	@Override
	@RequestMapping(value="/modifyDeliveryState" ,method={RequestMethod.POST})
	public ResponseEntity modifyDeliveryState(@RequestParam Map<String, String> deliveryMap, 
			                        HttpServletRequest request, HttpServletResponse response)  throws Exception {
		//modifyDeliveryState 수행
		adminOrderService.modifyDeliveryState(deliveryMap);
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		
		//완료시 message mod_success return
		message  = "mod_success";
		resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	
}
