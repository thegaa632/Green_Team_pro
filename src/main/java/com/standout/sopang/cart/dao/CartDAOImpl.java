package com.standout.sopang.cart.dao;

import java.util.List;

import com.standout.sopang.cart.dto.CartDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.standout.sopang.cart.vo.CartVO;
import com.standout.sopang.goods.vo.GoodsVO;

@Repository("cartDAO")
public class CartDAOImpl  implements  CartDAO{
	@Autowired
	private SqlSession sqlSession;


	//장바구니
	public List<CartVO> selectCartList(CartDTO cartDTO) throws DataAccessException {
		List<CartVO> cartList =sqlSession.selectList("mapper.cart.selectCartList",cartDTO);
		return cartList;
	}

	public List<GoodsVO> selectGoodsList(List<CartDTO> cartList) throws DataAccessException {
		List<GoodsVO> myGoodsList;
		myGoodsList = sqlSession.selectList("mapper.cart.selectGoodsList",cartList);
		return myGoodsList;
	}


	//장바구니 추가
	public boolean selectCountInCart(CartDTO cartDTO) throws DataAccessException {
		String  result =sqlSession.selectOne("mapper.cart.selectCountInCart",cartDTO);
		return Boolean.parseBoolean(result);
	}
	public void insertGoodsInCart(CartDTO cartDTO) throws DataAccessException{
		int cart_id=selectMaxCartId();
		cartDTO.setCart_id(cart_id);
		sqlSession.insert("mapper.cart.insertGoodsInCart",cartDTO);
	}


	private int selectMaxCartId() throws DataAccessException{
		int cart_id =sqlSession.selectOne("mapper.cart.selectMaxCartId");
		return cart_id;
	}

	//장바구니 삭제
	public void deleteCartGoods(int cart_id) throws DataAccessException{
		sqlSession.delete("mapper.cart.deleteCartGoods",cart_id);
	}

	//장바구니 수정
	public void updateCartGoodsQty(CartDTO cartDTO) throws DataAccessException{
		sqlSession.insert("mapper.cart.updateCartGoodsQty",cartDTO);
	}

}