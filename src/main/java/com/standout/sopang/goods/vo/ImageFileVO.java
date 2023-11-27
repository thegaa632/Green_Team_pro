package com.standout.sopang.goods.vo;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
//@Setter
@Getter
@ToString
@Component("imageFileVO")
public class ImageFileVO {
	private int goods_id;
	private int image_id;
	private String fileName;
	private String fileType;
	private String reg_id;


}
