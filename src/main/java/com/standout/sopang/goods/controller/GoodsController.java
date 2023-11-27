package com.standout.sopang.goods.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public interface GoodsController {
	//리스트페이지
	public String menuGoods(@RequestParam("menuGoods") String menuGoods, Model model
							, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//추천키워드
	//즉, @Responsebody 어노테이션을 사용하면 http요청 body를 자바 객체로 전달받을 수 있다.
	//출처: https://cheershennah.tistory.com/179 [Today I Learned. @cheers_hena 치얼스헤나:티스토리]
	public @ResponseBody String keywordSearch(@RequestParam("keyword") String keyword,Model model,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//검색
	public String searchGoods(@RequestParam("searchWord") String searchWord,Model model,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//상품상세
	public String goodsDetail(@RequestParam("goods_id") String goods_id,Model model,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
