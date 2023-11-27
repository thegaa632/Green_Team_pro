package com.standout.sopang.goods.service;

import com.standout.sopang.config.ConvertList;
import com.standout.sopang.goods.dao.GoodsDAO;
import com.standout.sopang.goods.dto.GoodsDTO;
import com.standout.sopang.goods.dto.ImageFileDTO;
import com.standout.sopang.goods.vo.GoodsVO;
import com.standout.sopang.goods.vo.ImageFileVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service("goodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService {


	@Autowired
	private GoodsDAO goodsDAO;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	ConvertList convertList;
	List<GoodsDTO> dtoList;
	List<ImageFileDTO> imageListDto;

//	메인페이지 - 지정 status별, 메뉴별

	public Map<String, List<GoodsDTO>> listGoods() throws Exception {
		Map<String, List <GoodsDTO>> goodsMap = new HashMap<String, List<GoodsDTO>>();

		//bestseller 저장
		List<GoodsVO> goodsList = goodsDAO.selectGoodsList("bestseller");
		dtoList =convertList.goodsConvertDTO(goodsList);
		goodsMap.put("bestseller", dtoList);

		//디지털 상품 저장
		goodsList = goodsDAO.selectMenusList("디지털");
		dtoList =convertList.goodsConvertDTO(goodsList);
		goodsMap.put("cate_digital", dtoList);

		//도서 상품 저장
		goodsList = goodsDAO.selectMenusList("도서");
		dtoList =convertList.goodsConvertDTO(goodsList);
		goodsMap.put("cate_book", dtoList);

		//건강기능식품 상품 저장
		goodsList = goodsDAO.selectMenusList("건강기능식품");
		dtoList =convertList.goodsConvertDTO(goodsList);
		goodsMap.put("cate_health", dtoList);

		//생활용품 상품 저장
		goodsList = goodsDAO.selectMenusList("생활용품");
		dtoList =convertList.goodsConvertDTO(goodsList);
		goodsMap.put("cate_daily", dtoList);


		//위 정보를 담은 Map return
		return goodsMap;
	}
//goodsList를 dtoList로 바꿔주는 함수를 설정


	//header 카테고리별
	@Override
	public List<GoodsDTO> menuGoods(String menuGoods) throws Exception {

		List<GoodsVO> goodsList = goodsDAO.selectGoodsByMenuGoods(menuGoods);
		dtoList =convertList.goodsConvertDTO(goodsList);
		return dtoList;
	}

	//추천키워드
	@Override
	public List<String> keywordSearch(String keyword) throws Exception {
		List<String> list = goodsDAO.selectKeywordSearch(keyword);
		return list;
	}

	//검색
	@Override
	public List<GoodsDTO> searchGoods(String searchWord) throws Exception {
		List goodsList = goodsDAO.selectGoodsBySearchWord(searchWord);
		dtoList =convertList.goodsConvertDTO(goodsList);

		return dtoList;
	}

	//상품상세
	public Map goodsDetail(String _goods_id) throws Exception {
		Map goodsMap = new HashMap();
		//상품상세정보 추출
		GoodsVO goodsVO = goodsDAO.selectGoodsDetail(_goods_id);
		GoodsDTO goodsDTO =modelMapper.map(goodsVO,GoodsDTO.class);
		goodsMap.put("goodsDTO", goodsDTO);

		//상품 상세이미지 추출
		List<ImageFileVO> imageList = goodsDAO.selectGoodsDetailImage(_goods_id);
		imageListDto =convertList.imgageConvertDTO(imageList);
		goodsMap.put("imageList", imageListDto);

		//위 정보를 담은 Map return
		return goodsMap;
	}

}
