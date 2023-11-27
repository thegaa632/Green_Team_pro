package com.standout.sopang.cart.dao;

import java.util.List;

import com.standout.sopang.cart.dto.CartDTO;
import org.springframework.dao.DataAccessException;

import com.standout.sopang.cart.vo.CartVO;
import com.standout.sopang.goods.vo.GoodsVO;

public interface CartDAO {
	//장바구니
	public List<CartVO> selectCartList(CartDTO cartDTO) throws DataAccessException;
	public List<GoodsVO> selectGoodsList(List<CartDTO> cartList) throws DataAccessException;

	//장바구니 추가
	public boolean selectCountInCart(CartDTO cartDTO) throws DataAccessException;
	public void insertGoodsInCart(CartDTO cartDTO) throws DataAccessException;

	//장바구니 삭제
	public void deleteCartGoods(int cart_id) throws DataAccessException;

	//장바구니 수정
	public void updateCartGoodsQty(CartDTO cartDTO) throws DataAccessException;

}