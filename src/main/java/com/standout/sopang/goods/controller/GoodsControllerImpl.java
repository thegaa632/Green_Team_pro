package com.standout.sopang.goods.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.goods.dto.GoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.common.base.BaseController;
import com.standout.sopang.goods.service.GoodsService;
import com.standout.sopang.goods.vo.GoodsVO;

import net.sf.json.JSONObject;

@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl extends BaseController   implements GoodsController {
	@Autowired
	private GoodsService goodsService;
	
	//리스트페이지

	@Override
	@RequestMapping(value="menuGoods" ,method = RequestMethod.GET)
	public String menuGoods(String menuGoods, Model model,
							HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<GoodsDTO> goodsList=goodsService.menuGoods(menuGoods);
		//추출한 데이터와 카테고리명을 매핑하여 return.
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("menuGoods", menuGoods);
		return "/goods/menuGoods";
	}


	//추천키워드
	// jsp에서 ajax를 사용하기 위해 데이터에 대한 값을 json 형태(자바객체) 형태로 받기위해
	//@Requestbody를 사용해 요청 함

	@Override
	@RequestMapping(value="/keywordSearch",method = RequestMethod.GET,produces = "application/text; charset=utf8")
	public String keywordSearch(String keyword, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		//keyword가 null인경우는 아무것도 return하지않는다.
		if(keyword == null || keyword.equals(""))
			return null ;
		//대소문자를 구분하지않고 검색하도록 한다.
		keyword = keyword.toUpperCase();
		List<String> keywordList =goodsService.keywordSearch(keyword);

		//결과값 산출
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("keyword", keywordList);
		String jsonInfo = jsonObject.toString();

		//변환한 string jsonObject, jsonInfo 리턴
		return jsonInfo ;
	}


	@Override
	@RequestMapping(value="/searchGoods" ,method = RequestMethod.GET)
	public String searchGoods(String searchWord,Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<GoodsDTO> goodsList=goodsService.searchGoods(searchWord);
		model.addAttribute("goodsList",goodsList);
		return "/goods/searchGoods";
	}

	//검색

	@Override
	@RequestMapping(value="/goodsDetail" ,method = RequestMethod.GET)
	public String goodsDetail(@RequestParam("goods_id") String goods_id, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
//
//		//goods_id값에 맞는 상세정보 가져와 goodsMap 할당
		Map goodsMap=goodsService.goodsDetail(goods_id);
		model.addAttribute("goodsMap", goodsMap);

//		//goodsMap을 goodsVO 객체에 대입
		GoodsDTO goodsDTO=(GoodsDTO)goodsMap.get("goodsDTO");
//
//		//퀵메뉴에 방문한 해당 상품정보를 추가
		addGoodsInQuick(goods_id,goodsDTO,session);
//
//		//뷰 + 상품상세 정보 리턴
	return "/goods/goodsDetail";
	}

	//상품상세

//	@RequestMapping(value="/goodsDetail.do" ,method = RequestMethod.GET)
//	public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id,
//			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String viewName=(String)request.getAttribute("viewName");
//		ModelAndView mav = new ModelAndView(viewName);
//		HttpSession session=request.getSession();
//
//		//goods_id값에 맞는 상세정보 가져와 goodsMap 할당
//		Map goodsMap=goodsService.goodsDetail(goods_id);
//		mav.addObject("goodsMap", goodsMap);
//
//		//goodsMap을 goodsVO 객체에 대입
//		GoodsVO goodsVO=(GoodsVO)goodsMap.get("goodsVO");
//
//		//퀵메뉴에 방문한 해당 상품정보를 추가
//		addGoodsInQuick(goods_id,goodsVO,session);
//
//		//뷰 + 상품상세 정보 리턴
//		return mav;
//	}
	
	
	
	//퀵메뉴
	private void addGoodsInQuick(String goods_id,GoodsDTO goodsDTO,HttpSession session){
		//중복체크를 위한 변수 초기화
		boolean already_existed=false;
		
		//기존 퀵메뉴 리스트 quickGoodsList 할당
		List<GoodsDTO> quickGoodsList;
		quickGoodsList=(ArrayList<GoodsDTO>)session.getAttribute("quickGoodsList");
		
		//퀵메뉴에 리스트가 있을때
		if(quickGoodsList!=null){
			
			//퀵메뉴 리스트에는 3개의 리스트만 표시할것임.
			if(quickGoodsList.size() < 3){
				for(int i=0; i<quickGoodsList.size();i++){
					String _goodsBean=String.valueOf(quickGoodsList.get(i).getGoods_id());
					//상품id, goods_id가 동일하다면 already_existed=true, 코드종료.
					if(goods_id.equals(_goodsBean)){
						already_existed=true;
						break;
					}
				}
				//already_existed이 false, 중복되지않는 새로운 상품일 경우 add
				if(already_existed==false){
					quickGoodsList.add(goodsDTO);
				}

			//퀵메뉴 리스트가 3개를 넘어가게 될경우
			}else {
				//첫번재 상품을 없애고 새로운 상품을 추가.
				quickGoodsList.remove(0);
				quickGoodsList.add(goodsDTO);
			}
		
		
		//퀵메뉴에 리스트가 없을 경우 새 ArrayList생성 및 추가 add
		}else{
			quickGoodsList =new ArrayList<GoodsDTO>();
			quickGoodsList.add(goodsDTO);
		}
		
		//위 작업을 완료 한 뒤 세션에 저장.
		session.setAttribute("quickGoodsList",quickGoodsList);
		session.setAttribute("quickGoodsListNum", quickGoodsList.size());
	}
	
}
