package com.standout.sopang.cart.config;


import com.standout.sopang.cart.dao.CartDAO;
import com.standout.sopang.cart.dto.CartDTO;
import com.standout.sopang.cart.vo.CartVO;
import com.standout.sopang.goods.dto.GoodsDTO;
import com.standout.sopang.goods.vo.GoodsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CartConvert {

    @Autowired
    ModelMapper modelMapper;


    public List<CartDTO> convertDTO(List<CartVO> goodsList ) {

        return goodsList.stream()
                .map(cart -> modelMapper.map(cart, CartDTO.class))
                .collect(Collectors.toList());
    }

}
