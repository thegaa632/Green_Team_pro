package com.standout.sopang.order.config;

import com.standout.sopang.mypage.dto.MyPageDTO;
import com.standout.sopang.mypage.vo.MyPageVO;
import com.standout.sopang.order.dto.OrderDTO;
import com.standout.sopang.order.vo.OrderVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


    @Service
    public class OrderConvert {
        @Autowired
        ModelMapper modelMapper;
        public List<OrderDTO> convertList(List<OrderVO> myPageList) {

            return myPageList.stream()
                    .map(orderList -> modelMapper.map(orderList, OrderDTO.class))
                    .collect(Collectors.toList());

    }
}
