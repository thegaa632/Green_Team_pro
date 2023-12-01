package com.standout.sopang.admin.goods.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.goods.dto.GoodsDTO;
import com.standout.sopang.goods.dto.ImageFileDTO;
import com.standout.sopang.member.dto.MemberDTO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.admin.goods.service.AdminGoodsService;
import com.standout.sopang.common.base.BaseController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("adminGoodsController")
@RequestMapping(value = "/admin/goods")
public class AdminGoodsControllerImpl extends BaseController implements AdminGoodsController {
	private static final String CURR_IMAGE_REPO_PATH = "C:\\sopang\\file_repo";
	@Autowired
	private AdminGoodsService adminGoodsService;

	// ��ǰ���� - ��ǰ����Ʈ
	@RequestMapping(value = "/adminGoodsMain", method = { RequestMethod.POST, RequestMethod.GET })
	public String adminGoodsMain(@RequestParam Map<String, String> dateMap, HttpServletRequest request
		, Model model,	HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session = request.getSession();
		session.setAttribute("side_menu", "admin_mode");
		
		//fixedSearchPeriod���� �޾� ����
		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");

		//�Ⱓ �ʱ�ȭ
		String beginDate = null, endDate = null;



		//fixedSearchPeriod�� ������ dateMap�� put
		String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate = tempDate[0];
		endDate = tempDate[1];
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);

		//condMap�� put �� listNewGoods����.
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("beginDate", beginDate);
		condMap.put("endDate", endDate);
		List<GoodsDTO> newGoodsList = adminGoodsService.listNewGoods(condMap);
		
		//���ϵ� ��ǰ����Ʈ newGoodsList��  mav�� newGoodsList�� �ο�
		model.addAttribute("newGoodsList", newGoodsList);

		//��¥��������
		String beginDate1[] = beginDate.split("-");
		String endDate2[] = endDate.split("-");

		model.addAttribute("beginYear", beginDate1[0]);
		model.addAttribute("beginYear", beginDate1[1]);
		model.addAttribute("beginYear", beginDate1[2]);
		model.addAttribute("endYear", endDate2[0]);
		model.addAttribute("endYear", endDate2[1]);
		model.addAttribute("endYear", endDate2[2]);

		
		return "/admin/goods/adminGoodsMain";
	}

	// ��ǰ�߰�
	@RequestMapping(value = "/addNewGoods", method = { RequestMethod.POST })
	public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String imageFileName = null;
		
		//form ���� �޾� newGoodsMap�� put
		Map newGoodsMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			newGoodsMap.put(name, value);
		}

		//���ǿ��� get�� memberInfo�� reg_id, �������̰� ��.
		HttpSession session = multipartRequest.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberInfo");
		String reg_id = memberDTO.getMember_id();

		//baseController upload
		List<ImageFileDTO> imageFileList = upload(multipartRequest);
		
		//imageFileList�� �޾� setReg_id�� newGoodsMap�� put
		if (imageFileList != null && imageFileList.size() != 0) {
			for (ImageFileDTO imageFileDTO : imageFileList) {
				imageFileDTO.setReg_id(reg_id);
			}
			newGoodsMap.put("imageFileList", imageFileList);
		}

		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			//��ǰ, ���������� ����ִ� newGoodsMap���� addNewGoods ����
			int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
			
			//imageFileList�� ���� ��� 
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileDTO imageFileDTO : imageFileList) {
					
					//temp�ȿ� imageFileName �̸����� ���� ����,
					imageFileName = imageFileDTO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					
					//���� goods_id�� �������� ������ �ϳ� ����� 
					File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
					
					//�̵��Ѵ�.
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}
			
			//���� ������ �Ϸ�Ǹ� �ȳ��ϸ�, adminGoodsMain ��ǰ��� �������� reload�Ѵ�.
			message = "<script>";
			message += " alert('����ǰ�� �߰��߽��ϴ�.');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain';";
			message += ("</script>");
		} catch (Exception e) {
			//���ܹ߻��� ��� 
			//�̹� ������ �����Ǿ� �ִ� ���� ������ �߻��� ����Ѵ�.
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileDTO imageFileDTO : imageFileList) {
					imageFileName = imageFileDTO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//������� temp��ξ��� ���ϵ��� �����Ѵ�.
					srcFile.delete();
				}
			}
			//������ �Ϸ��� ���� �ȳ��ϸ�, adminGoodsMain ��ǰ��� �������� reload�Ѵ�.
			message = "<script>";
			message += " alert('������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain';";
			message += ("</script>");
			e.printStackTrace();
		}
		
		
		//�� ��쿡 ���� message�� ������ resEntity �����Ѵ�.
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value = "/deleteGoods", method = RequestMethod.GET)
	public String deleteGoods(String goods_id, HttpServletRequest request, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		//goods_id�� ������ ���� ����.
		adminGoodsService.deleteGoods(goods_id);

		//��ǰ������ �� �����Ǿ��ٸ�
		//�ش� goods_id ����� �̹��� ���ϵ� ������ �뷮������ �ߺ��� �����Ѵ�.
		File folder = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
		try {
			//folder�� �����ϴ� �� �Ʒ��� ������ �ݺ��ȴ�.
			//������ �ƹ��͵� ���� �������� ������ �� �������� ������ ���� �����ϰ� ������ �����ϴ� ������ ��ģ��.
			while (folder.exists()) {
				File[] folder_list = folder.listFiles();
				for (int j = 0; j < folder_list.length; j++) {
					//���� ����
					folder_list[j].delete();
				}
				if (folder_list.length == 0 && folder.isDirectory()) {
					//��� ������ �����Ǿ��ٸ�, ������ �����Ѵ�.
					folder.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//�Ϸ��� adminGoodsMain�� reload

		return "redirect:/admin/goods/adminGoodsMain";
	}

//	// ��ǰ����
//	@RequestMapping(value = "/deleteGoods", method = RequestMethod.GET)
//	public String deleteGoods(@RequestParam("goods_id") String goods_id, HttpServletRequest request,
//							  , RedirectAttributes redirectAttributes, Model model, HttpServletResponse response) throws Exception {
//		//goods_id�� ������ ���� ����.
//		adminGoodsService.deleteGoods(goods_id);
//
//		//��ǰ������ �� �����Ǿ��ٸ�
//		//�ش� goods_id ����� �̹��� ���ϵ� ������ �뷮������ �ߺ��� �����Ѵ�.
//		File folder = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
//		try {
//			//folder�� �����ϴ� �� �Ʒ��� ������ �ݺ��ȴ�.
//			//������ �ƹ��͵� ���� �������� ������ �� �������� ������ ���� �����ϰ� ������ �����ϴ� ������ ��ģ��.
//			while (folder.exists()) {
//				File[] folder_list = folder.listFiles();
//				for (int j = 0; j < folder_list.length; j++) {
//					//���� ����
//					folder_list[j].delete();
//				}
//				if (folder_list.length == 0 && folder.isDirectory()) {
//					//��� ������ �����Ǿ��ٸ�, ������ �����Ѵ�.
//					folder.delete();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		//�Ϸ��� adminGoodsMain�� reload
//
//		return "redirect:/admin/goods/adminGoodsMain";
//	}

	
	
	
	
	// ��ǰ����
	@Override
	@RequestMapping(value = "/modifyGoods", method = { RequestMethod.POST })
	public ResponseEntity modifyGoods(@RequestParam("goods_id") String goods_id,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {

		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String imageFileName = null;

		//form ���� �޾� newGoodsMap�� put
		Map newGoodsMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			newGoodsMap.put(name, value);
		}

		HttpSession session = multipartRequest.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberInfo");

		
		//���ɹ��� imageFileList�� �������� getFileName�� ������� �ʴ��� Ȯ���ϰ�, ī��Ʈ�Ѵ�.
		int check = 0;
		List<ImageFileDTO> imageFileList = upload(multipartRequest);
		if (imageFileList != null && imageFileList.size() != 0) {
			for (ImageFileDTO imageFileDTO : imageFileList) {
				if (imageFileDTO.getFileName() == "" || imageFileDTO.getFileName() == null) {
					check += 1;
				}
			}
			newGoodsMap.put("imageFileList", imageFileList);
		}

		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			//modifyGoods ��ǰ���� ����
			adminGoodsService.modifyGoods(goods_id, newGoodsMap);
			for (ImageFileDTO imageFileDTO : imageFileList) {
				
				//���ɹ��� �������� getFileName�� ã�� �� ���ٸ� ����/�̹����� ����/���ε������ʴ´�.
				if (imageFileDTO.getFileName() == "" || imageFileDTO.getFileName() == null) {
				} else {
					//����Ʈ�� ���������� �� �޾Ҵٸ�
					imageFileName = imageFileDTO.getFileName();
					//temp �ӽ����� �ȿ� ���ϻ��� imageFileName
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//�̸��� goods_id�� ������ ����� copyFileToDirectory
					File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
					FileUtils.copyFileToDirectory(srcFile, destDir);
				}
				
			}
			
			//�� ������ �Ϸ��� ���� �ȳ��ϸ� adminGoodsMain�� reload
			message = "<script>";
			message += " alert('�����Ǿ����ϴ�!');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain';";
			message += ("</script>");
			
		} catch (Exception e) {
			//������ ���ܰ� ������
			
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileDTO imageFileDTO : imageFileList) {
					imageFileName = imageFileDTO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//temp�ӽ� ������ ������ ���ϵ��� �����Ѵ�.
					srcFile.delete();
				}
			}

			//�� ������ �Ϸ��� ���� �ȳ��ϸ� adminGoodsMain�� reload
			message = "<script>";
			message += " alert('������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain';";
			message += ("</script>");
			e.printStackTrace();
		}
		
		//�� ��쿡 ���� message�� ������ resEntity �����Ѵ�.
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

}
