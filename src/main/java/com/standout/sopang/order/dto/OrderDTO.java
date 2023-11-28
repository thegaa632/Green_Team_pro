package com.standout.sopang.order.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class OrderDTO {
    private int order_seq_num;
    private int order_id;
    private int goods_id;

    private String goods_title;
    private int goods_sales_price;
    private String goods_fileName;
    private int order_goods_qty;

    private String member_id;
    private String orderer_hp;
    private String receiver_name;
    private String receiver_hp1;
    private String delivery_address;
    private String pay_method;
    private String card_com_name;
    private String pay_orderer_hp_num;
    private String card_pay_month;

    private String delivery_state;
    private String pay_order_time;

    private String cardNo;

    private String expireYear;
    private String expireMonth;

    private String birthday;
    private String cardPw;
}