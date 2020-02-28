package com.jung.TeamProject.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jung.TeamProject.dao.StoreDao;
import com.jung.TeamProject.dto.ApplyDetailDto;
import com.jung.TeamProject.dto.ApplyDto;
import com.jung.TeamProject.dto.MenuDto;
import com.jung.TeamProject.dto.OrderDetailDto;
import com.jung.TeamProject.dto.OrderDto;
import com.jung.TeamProject.dto.StoreDto;

@Controller
@MultipartConfig(maxFileSize=1024*1024*4)
public class BossController {
	
	@Autowired
	private StoreDao sdao;
	
	//�������� ��� + ȸ�����̵� �α����� ���̵��� �־�� �Ѵ�.
	@RequestMapping("apply")
	public String apply(HttpServletRequest request, Model model) {
		
		System.out.println("apply ��Ʈ�ѷ� ����");
		
		String result =
		sdao.Apply(request.getParameter("asName"), request.getParameter("abNo"),
						request.getParameter("asLat"), request.getParameter("asLng"), request.getParameter("asAd"),
						request.getParameter("asPhone"), request.getParameter("asCategory"), request.getParameter("user_id"));
		
		if(result.equals("Apply-success")) {
			System.out.println("���� ��� ����");	
			model.addAttribute("user_id", request.getParameter("user_id"));
			return "redirect:MyApplyList";
		}
		else {
			System.out.println("���� ��� ����");
			return "redirect:BossHome";
		}
		
	}
	
	//�α����� ����� �������� ����Ʈ(���� �������Ǹ� ����)
	@RequestMapping("MyApplyList")
	public String MyApplyList(HttpServletRequest request, Model model) {
		
		String uId = request.getParameter("user_id");
		System.out.println("ȸ�����̵� : " + uId + "�������Ǹ���Ʈ ��Ʈ�ѷ� ����");
		
		//����� �ְ� ������ �ʿ� ����
		ArrayList<ApplyDto> str = sdao.MyApplylist(uId);
		System.out.println("dao : " + str);
		
		model.addAttribute("myapplylist", str);
		
		return "Boss/Apply/MyApplyList_view";

	}	
	
	//�������� �󼼺��� ����Ʈ
	@RequestMapping("MyApplyDetail")
	public String ApplyDetail(HttpServletRequest request, Model model) {
		
		System.out.println("ApplyDetail ��Ʈ�ѷ� ����");

		int no = Integer.parseInt(request.getParameter("asNo"));
		
		ArrayList<ApplyDetailDto> dto = sdao.ApplyDetail(no);
		model.addAttribute("ApplyDetailList", dto);
		
		return "Boss/Apply/MyApplyDetail_view";
		
	}
	
	//�������� ����ȭ��
	@RequestMapping("ApplyContent")
	public String ApplyContent(HttpServletRequest request, Model model) {
		System.out.println("ApplyContent ��Ʈ�ѷ� ����");
		
		int no = Integer.parseInt(request.getParameter("asNo"));
		
		ArrayList<ApplyDto> dto = sdao.ApplyContent(no);
		model.addAttribute("modify", dto);
		
		return "Boss/Apply/ApplyContent_view";
		
	}
	
	//�������� ����
	@RequestMapping("ApplyModify")
	public String ApplyModify(HttpServletRequest request, Model model) {
		System.out.println("ApplyModify ��Ʈ�ѷ� ����");
		
		int no = Integer.parseInt(request.getParameter("asNo"));
		
		System.out.println("asNo : " + no);
		
		sdao.ApplyModify(no, request.getParameter("asName"), request.getParameter("abNo"),
						request.getParameter("asLat"), request.getParameter("asLng"), request.getParameter("asAd"),
						request.getParameter("asPhone"), request.getParameter("asCategory"));
		
		System.out.println("���� ����");
		
		model.addAttribute("user_id", request.getParameter("user_id"));
		
		return "redirect:MyApplyList";
		
	}
	
	//���������ϱ�
	@RequestMapping("ApplyDelete")
	public String ApplyDelete(HttpServletRequest request, Model model) {
		System.out.println("ApplyDelete ��Ʈ�ѷ� ����");
		
		int no = Integer.parseInt(request.getParameter("asNo"));
		
		sdao.ApplyDelete(no);
		System.out.println("���� ����");	
		model.addAttribute("user_id", request.getParameter("user_id"));
		
		return "redirect:MyApplyList";
		
	}
	
	//======================================================
	
	//�� ���� ����Ʈ(�α����� ���̵��� ���Ͽ� ������ ����Ʈ�� �ҷ��;��Ѵ�.)
	@RequestMapping("MySList")
	public String MySList(HttpServletRequest request, Model model) {
		
		String uId = request.getParameter("user_id");
		System.out.println("ȸ�����̵� : " + uId + "�� ���Ը���Ʈ ��Ʈ�ѷ� ����");
		
		ArrayList<StoreDto> str = sdao.MyStorelist(uId);
		System.out.println("str : " + str);
		
		model.addAttribute("MySList", str);
		return "Boss/Store/MySList_view";
		
	}
	
	
	//�� ���Ը���Ʈ �󼼺���
	@RequestMapping("/MySDetail")
	public String MySDetail(HttpServletRequest request, Model model) {
		System.out.println("MySDetail ��Ʈ�ѷ� ����");
		
		int sno = Integer.parseInt(request.getParameter("sNo"));
		
		ArrayList<StoreDto> dto = sdao.StoreDetail(sno);
		model.addAttribute("MySDetail", dto);
		
		return "Boss/Store/MySDetail_view";
		
	}
	
	//�����Գ��� ����(������ �ֹ�����, �����)
	@RequestMapping("BSModify")
	public String BSModify(HttpServletRequest request, Model model) {
		
		int sNo = Integer.parseInt(request.getParameter("sNo"));
		System.out.println("��ü�ڵ� : " + sNo);
		
		int Tip = Integer.parseInt(request.getParameter("sTip"));
		int Price = Integer.parseInt(request.getParameter("mPrice"));
		
		sdao.BSModify(sNo, Tip, Price);
		
		System.out.println("������ ���� ����");
		model.addAttribute("user_id", request.getParameter("user_id"));
		return "redirect:MySList";
		
	}
	
	//======================================================
	
	//�޴���� ������(�޴� ����Ʈ�ȿ��� �̰Ÿ� ��� �ϳ��� �߰���Ű�� ��)
	@RequestMapping("MenuRegister")
	public String MenuRegister(HttpServletRequest request, Model model) {
		System.out.println("MenuRegister ��Ʈ�ѷ� ����");
		
		int sno = Integer.parseInt(request.getParameter("sNo"));
		 
		ArrayList<StoreDto> dto = sdao.StoreDetail(sno);
		model.addAttribute("sNo", dto); //�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�
		
		return "Boss/Store/MenuRegister";
		
	}
	
	//�޴� ���
	@RequestMapping("MRegister")
	public String MenuRegister(MultipartHttpServletRequest request, Model model) {
		System.out.println("MRegister ��Ʈ�ѷ� ����");
		//===========================
		
		MultipartFile mf = request.getFile("mImg");
		//MultipartFile��ü�� ��, �̋��� getParameter��� getFile()�޼��带 ���
		System.out.println(mf);
		
		String path = "D:/TeamProject/Menu/";
		//������ ���޵� �����̸�
		String originFileName = mf.getOriginalFilename();
		//���ε�� ���� ũ��
		long fileSize = mf.getSize();
		
		System.out.println("originFileName : " + originFileName);
		System.out.println("fileSize : " + fileSize);		
		
		//������ ������ �̸��� �ٲپ� ����(������ �̸��� ������ ������ �ߺ� ����, �ð��� �տ� �ٿ� �ߺ� ����)
		String safeFile = path + System.currentTimeMillis() + originFileName;
		String dfile = System.currentTimeMillis() + originFileName;
				
		try {
			mf.transferTo(new File(safeFile)); //MultipartFile��ü�� �Ϲ� ���� ��ü�� ��ȯ
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//model.addAttribute("author", src);
		model.addAttribute("fileName", dfile);
		model.addAttribute("fileSize", fileSize);
		
		
		String imgname1 = request.getParameter("mImg");
		String mimg = imgname1;
		
		mimg = dfile;
		
		//===========================
		int sno = Integer.parseInt(request.getParameter("sNo")); //��ü�ڵ� �޾ƿ°�
		int mprice = Integer.parseInt(request.getParameter("mPrice"));
		
		String result =
						sdao.MenuRegister(request.getParameter("mName"), mimg, mprice, request.getParameter("mTitle1"), 
								request.getParameter("mTitle2"),request.getParameter("mInfo"), sno);
		
		if(result.equals("Mregister-success")) {
			System.out.println("�޴� ��� ����");	
			model.addAttribute("sNo", sno);
			return "redirect:BMenuList";
		}
		else {
			System.out.println("�޴� ��� ����");
			return "redirect:BossHome";//���н� �ٽ� ���������(����ȭ�� ���� �־��)
		}
		 
	}
	
	//�޴�����(���븸)
	@RequestMapping("MenuModify")
	public String MenuModify(HttpServletRequest request, Model model) {
		
		int sno = Integer.parseInt(request.getParameter("sNo")); //��ü�ڵ� �޾ƿ°�
		
		System.out.println("�޴����� ��Ʈ�ѷ� ����" + sno);
		
		int mno = Integer.parseInt(request.getParameter("mNo"));
		int mprice = Integer.parseInt(request.getParameter("mPrice"));
		
		sdao.MenuModify(mno, request.getParameter("mName"), mprice, request.getParameter("mInfo"));
		
		model.addAttribute("sNo", sno);
		return "redirect:BMenuList";
	}
	
	//�޴��̹��� ����ȭ��
	@RequestMapping("MImgModify")
	public String MImgModify(HttpServletRequest request, Model model) {
		
		System.out.println("�޴��̹��� ����(���) ��Ʈ�ѷ� ����");
		
		int mno = Integer.parseInt(request.getParameter("mNo"));
		
		System.out.println("mNo : " + mno);
		
		ArrayList<MenuDto> dto = sdao.MImgModify(mno);
		model.addAttribute("mImg", dto); //�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�

		return "Boss/Store/MImg_Modify";
	}
	
	//�޴� �̹��� ����
	@RequestMapping("BMImgModify")
	public String BMImgModify(MultipartHttpServletRequest request, Model model) {
		
		int sno = Integer.parseInt(request.getParameter("sNo"));
		int mno = Integer.parseInt(request.getParameter("mNo"));

		String dimg = request.getParameter("dImg");
		String path1 = "D:/TeamProject/Menu/" + dimg;
		//���ϻ���
		File file = new File(path1);
		System.out.println("���" + path1);
		file.delete();
						
		System.out.println("mNo : " + mno);
		
		//===========================
		
		MultipartFile mf = request.getFile("mImg");
		//MultipartFile��ü�� ��, �̋��� getParameter��� getFile()�޼��带 ���
		System.out.println(mf);
		
		String path = "D:/TeamProject/Menu/";
		//������ ���޵� �����̸�
		String originFileName = mf.getOriginalFilename();
		//���ε�� ���� ũ��
		long fileSize = mf.getSize();
		
		System.out.println("originFileName : " + originFileName);
		System.out.println("fileSize : " + fileSize);		
		
		//������ ������ �̸��� �ٲپ� ����(������ �̸��� ������ ������ �ߺ� ����, �ð��� �տ� �ٿ� �ߺ� ����)
		String safeFile = path + System.currentTimeMillis() + originFileName;
		String dfile = System.currentTimeMillis() + originFileName;
				
		try {
			mf.transferTo(new File(safeFile)); //MultipartFile��ü�� �Ϲ� ���� ��ü�� ��ȯ
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("fileName", dfile);
		model.addAttribute("fileSize", fileSize);
		
		
		String imgname1 = request.getParameter("mImg");
		String Mimg = imgname1;
		
		Mimg = dfile;
		
		//===========================
		
		sdao.BMImgModify(mno, Mimg);
		
		System.out.println("�̹��� ���� ����");
		model.addAttribute("sNo", sno);
		return "redirect:BMenuList";
	}
	
	
	
	//�޴�����Ʈ
	@RequestMapping("BMenuList")
	public String BMenuList(HttpServletRequest request, Model model) {
		System.out.println("BMenuList ��Ʈ�ѷ� ����");
		
		//=======================
		//�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�
		int sno = Integer.parseInt(request.getParameter("sNo"));
		
		ArrayList<StoreDto> dto = sdao.StoreDetail(sno);
		model.addAttribute("MySNo", dto); //�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�
		//=======================
		
		//��ǥ�޴�
		ArrayList<MenuDto> dto1 = sdao.MTList(sno);
		model.addAttribute("MTList", dto1); //�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�
		
		//���θ޴�
		ArrayList<MenuDto> dto2 = sdao.MMList(sno);
		model.addAttribute("MMList", dto2); //�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�
		
		//���̵� �޴�
		ArrayList<MenuDto> dto3 = sdao.MSList(sno);
		model.addAttribute("MSList", dto3); //�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�
		
		return "Boss/Store/BMenu_view";
		
	}
	
	//�޴� ���� MenuDelete
	@RequestMapping("MenuDelete")
	public String MenuDelete(HttpServletRequest request, Model model) {
		System.out.println("MenuDelete ��Ʈ�ѷ� ����");
		//int sno = Integer.parseInt(request.getParameter("sNo"));
		//System.out.println("sno" + sno);
		int mno = Integer.parseInt(request.getParameter("mNo"));
		String mimg = request.getParameter("mImg"); //�����̸�
		String path = "D:/TeamProject/Menu/" + mimg;
		
		System.out.println("MenuDelete ��Ʈ�ѷ� ����" +mno +"sdfsdf" + mimg );
		//���ϻ���
		File file = new File(path);
		System.out.println("���" + path);
		file.delete(); 
		
		//DB����
		sdao.MenuDelete(mno);
		
		System.out.println("���� ����");	
		//model.addAttribute("sNo", sno);
		
		return "redirect:BossHome";
		
	}
	
	//======================================================
	//�ֹ�����
	
	//�� ���� �ֹ�����(����) - ���ò���
	@RequestMapping("MyOrderContent")
	public String MyOrderContent(HttpServletRequest request, Model model, OrderDto dto) throws ParseException {
		
		//���ó�¥
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd");		
		Date date = new Date();
		String time1 = format1.format(date);
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(time1);
		
		String bId = request.getParameter("user_id"); 
		dto.setUser_id(bId);
		dto.setoDate(date1);
		
		//�����ֹ� ���
		ArrayList<OrderDto> dto1 = sdao.MyStoreOrder(dto);
		model.addAttribute("MyStoreOrder", dto1);
		
		System.out.println("������̵� : " + bId);
	
		return "Boss/Store/MyOrderContent_view";
	}
	
	//������ �ֹ�����(����) - �ֹ����¿� ���� ����ƮStatusOrderList
	@RequestMapping("StatusOrderList")
	public String StatusOrderList(HttpServletRequest request, Model model, OrderDto dto) throws ParseException {
		
		String bId = request.getParameter("user_id");
		String stauts = request.getParameter("oStatus");

		System.out.println("StatusOrderList(����)��Ʈ�ѷ� ���� : " + "1: " + bId + "     2 : " + stauts);
		
		//���ó�¥
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd");		
		Date date = new Date();
		String time1 = format1.format(date);
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(time1);
		
		dto.setUser_id(bId);
		dto.setoStatus(stauts);
		dto.setoDate(date1);
		
		//�ֹ����º� ����Ʈ
		ArrayList<OrderDto> dto1 = sdao.StatusOrderList(dto);
		model.addAttribute("StatusOrderList", dto1);
		
		return "Boss/Store/StatusOrderList_view";
		
	}
	
	//������ �ֹ�����(����) - �ֹ����¿� ���� ����ƮStatusOrderList + ���� ��ü �ֹ������� �󼼳���
	@RequestMapping("OrderDetail")
	public String OrderDetail(HttpServletRequest request, Model model, OrderDto dto, OrderDetailDto dto1) {
		
		String ono = request.getParameter("oNo");
		
		dto.setoNo(ono); //�ֹ���ȣ(�ּҳ���)
		dto1.setoNo(ono); //�ֹ���ȣ(�޴�����)
		
		//�ֹ��󼼳���(�ּ�)
		ArrayList<OrderDto> od = sdao.OrderDetail1(dto);
		model.addAttribute("OrderDetail1", od);
		
		//�ֹ��󼼳���(�޴�)
		ArrayList<OrderDetailDto> od1 = sdao.OrderDetail2(dto1);
		model.addAttribute("OrderDetail2", od1);
		
		return "Boss/Store/BossOrderDetail_view";
		
	}
	
	//�ֹ����� �ֹ�ó������ ����(����)
	@RequestMapping("oStatusModify")
	public String oStatusModify(HttpServletRequest request, Model model, OrderDto dto) {
		
		String ono = request.getParameter("oNo");
		String ostatus = request.getParameter("oStatus");
		
		dto.setoNo(ono);
		dto.setoStatus(ostatus);
		
		sdao.oStatusModify(dto);
		System.out.println("�ֹ����� �����Ϸ�");

		model.addAttribute("oNo", ono);
		
		return "redirect:OrderDetail";
		
	}
	
	//��ü�ֹ�����(����) AllOrderList
	@RequestMapping("AllOrderList")
	public String AllOrderList(HttpServletRequest request, Model model, OrderDto dto,
									@RequestParam(value = "user_id", required = false) String uId) {
		System.out.println("������̵� : " + uId);
		dto.setUser_id(uId);
		
		ArrayList<OrderDto> od = sdao.AllOrderList(dto);
		model.addAttribute("All", od);
		
		return "Boss/Store/AllOrderList_view";
		
	}
	
	//��ü�ֹ����� ��¥�� ��ȸ(����)
	@RequestMapping("AllOrderCheck")
	public String AllOrderCheck(HttpServletRequest request, Model model) throws ParseException {
		
		System.out.println("123");
		String uId = request.getParameter("user_id");
		String date = request.getParameter("oDate");
		String date1 = request.getParameter("oDate1");	
		System.out.println("date : 	" + date + "  date1 : " + date1);
		
		Date nDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		Date nDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		System.out.println("nDate : " + nDate + "nDate1 : " + nDate1);
		
		ArrayList<OrderDto> dto1 = sdao.AllOrderCheck(uId, nDate, nDate1);
		model.addAttribute("All", dto1);
	
		return "Boss/Store/AllOrderList_view"; 

	}
	
}