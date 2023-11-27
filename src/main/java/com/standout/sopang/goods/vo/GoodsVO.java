package com.standout.sopang.goods.vo;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
//@Setter
@Getter
@ToString
@Component("goodsVO")
public class GoodsVO {
	private int goods_id;
	private String goods_sort;
	private String goods_status;
	private String goods_title;
	private String goods_intro;
	private int goods_sales_price;
	private String goods_delivery_price;
	private Date goods_delivery_date;
	private Date creDate;
	private String goods_fileName;



}
