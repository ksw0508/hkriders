package com.jung.TeamProject.controller;

import java.io.File;

import java.util.ArrayList;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jung.TeamProject.dao.StoreDao;
import com.jung.TeamProject.dao.UserDao;
import com.jung.TeamProject.dto.ApplyDetailDto;
import com.jung.TeamProject.dto.ApplyDto;
import com.jung.TeamProject.dto.MenuDto;
import com.jung.TeamProject.dto.StoreDto;

@Controller
@MultipartConfig(maxFileSize=1024*1024*4)
public class ManagerController {
	
	@Autowired
	private UserDao udao;
	
	@Autowired
	private StoreDao sdao;
	
	//ȸ�� ����Ʈ
	@RequestMapping("UserList")
	public String List(Model model) {
		model.addAttribute("UserList", udao.Userlist()); //���⼭ "list�� jsp���� c:forEach items ���� �־�� �Ǵ� ���̴�.
		System.out.println("dao : " + udao.Userlist());
		return "Manager/User/userlist_view"; 
	}
	
	//ȸ�� �� ����Ʈ
	@RequestMapping("UserDetail")
	public String UserDetail(HttpServletRequest request, Model model) {
		int uNo = Integer.parseInt(request.getParameter("user_no"));
		System.out.println("user_no : " + uNo);
		
		model.addAttribute("UserDetail", udao.UserDetail(uNo)); //���⼭ "list�� jsp���� c:forEach items ���� �־�� �Ǵ� ���̴�.
		System.out.println("dao : " + udao.UserDetail(uNo));
		return "Manager/User/UserDetailList_view"; 
	}

	//======================================================================
	
	
	//�������� ��ü ����Ʈ (������)
	@RequestMapping("ApplyList")
	public String applylist(Model model) {
		
		//��ü ����Ʈ ����� �ְ� ������ �ʿ� ����
		ArrayList<ApplyDto> str = sdao.Applylist();
		model.addAttribute("applylist", str);
		
		return "Manager/Apply/ApplyList_view";

	} 
	
	//�������� �󼼺��� ����Ʈ
	@RequestMapping("ApplyDetail")
	public String ApplyDetail(HttpServletRequest request, Model model) {
		
		System.out.println("ApplyDetail ��Ʈ�ѷ� ����");

		int no = Integer.parseInt(request.getParameter("asNo"));
		
		ArrayList<ApplyDetailDto> dto = sdao.ApplyDetail(no);
		model.addAttribute("ApplyDetailList", dto);
		
		return "Manager/Apply/ApplyDetail_view";
		
	}
	
	
	//======================================================================
	
	
	//���Ե��
	@RequestMapping("register")
	public String register(MultipartHttpServletRequest request, Model model) {
		
		//===========================
		
		MultipartFile mf = request.getFile("sImg");
		//MultipartFile��ü�� ��, �̋��� getParameter��� getFile()�޼��带 ���
		System.out.println(mf);
		
		String path = "D:/TeamProject/Store/";
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
		
		
		String imgname1 = request.getParameter("sImg");
		String Simg = imgname1;
		
		Simg = dfile;
		
		//===========================
		
		int Tip = Integer.parseInt(request.getParameter("sTip"));
		int Price = Integer.parseInt(request.getParameter("mPrice"));
		
		
		sdao.Register(request.getParameter("sName"), request.getParameter("bName"), request.getParameter("bNo"), Simg, request.getParameter("sAd"),
						request.getParameter("sLat"), request.getParameter("sLng"), request.getParameter("sPhone"), request.getParameter("sCategory"),
						Tip, Price, request.getParameter("user_id"));
		
		//����ó������ ó���Ϸ� ����
		int no = Integer.parseInt(request.getParameter("asNo"));
		sdao.ApplyStatusEnd(no);
		
		System.out.println("���� ��� ����");	
		return "redirect:StoreList";
		
	}
	
	//���� ����Ʈ
	@RequestMapping("StoreList")
	public String StoreList(Model model) {
		
		ArrayList<StoreDto> str = sdao.Storelist();
		System.out.println("str : " + str);
		
		model.addAttribute("storelist", str);
		return "Manager/Store/StoreRegisterList_view";
		
	}
	
	//���Ը���Ʈ �󼼺���(����,������ ���� ���� ��ư�� �������� ������ �ص��Ǵµ� �����,�ֹ����� ���������� ������ ��)
	@RequestMapping("StoreDetail")
	public String StoreDetail(HttpServletRequest request, Model model) {
		System.out.println("StoreDetail ��Ʈ�ѷ� ����");
		
		int sno = Integer.parseInt(request.getParameter("sNo"));
		
		ArrayList<StoreDto> dto = sdao.StoreDetail(sno);
		model.addAttribute("StoreDetailList", dto);
		
		return "Manager/Store/StoreDetail_view";
		
	}
	
	//���� ���� ����ȭ��
	@RequestMapping("RegisterContent")
	public String RegisterContent(HttpServletRequest request, Model model) {
		System.out.println("RegisterContent ��Ʈ�ѷ� ����");
		
		int no = Integer.parseInt(request.getParameter("sNo"));
		
		ArrayList<StoreDto> dto = sdao.RegisterContent(no);
		model.addAttribute("Smodify", dto);
		
		return "Manager/Store/RegisterContent_view";
		
	}
	
	//���� �̹���(�ΰ�) ����,���ε� ȭ�� --> dao �޼��� �� �ڵ�� ���� ����ȭ���̶� ����(�ҷ����� ���� �����ϱ�)
	@RequestMapping("RegisterImgContent")
	public String RegisterImgContent(HttpServletRequest request, Model model) {
		System.out.println("RegisterImgContent ��Ʈ�ѷ� ����");
		
		int no = Integer.parseInt(request.getParameter("sNo"));
		
		ArrayList<StoreDto> dto = sdao.RegisterContent(no);
		model.addAttribute("Smodify", dto);
		
		return "Manager/Store/RegisterImgConten_view";
		
	}
	
	//���� ���� ����
	@RequestMapping("StoreModify")
	public String StoreModify(HttpServletRequest request, Model model) {
		
		int no = Integer.parseInt(request.getParameter("sNo"));
		System.out.println("sNo : " + no);
		
		int Tip = Integer.parseInt(request.getParameter("sTip"));
		int Price = Integer.parseInt(request.getParameter("mPrice"));
		
		sdao.StoreModify(no, request.getParameter("sName"), request.getParameter("bName"), request.getParameter("bNo"), request.getParameter("sAd"),
						request.getParameter("sLat"), request.getParameter("sLng"), request.getParameter("sPhone"), request.getParameter("sCategory"),
						Tip, Price);
		
		System.out.println("���� ����");
		
		return "redirect:StoreList";
		
	}
	
	//���� �̹��� ���� ����(���� ���ε�,����)ImgModify
	@RequestMapping("ImgModify")
	public String ImgModify(MultipartHttpServletRequest request, Model model) {
		
		int no = Integer.parseInt(request.getParameter("sNo"));

		String dimg = request.getParameter("dImg");
		String path1 = "D:/TeamProject/Store/" + dimg;
		//���ϻ���
		File file = new File(path1);
		System.out.println("���" + path1);
		file.delete();
						
		System.out.println("sNo : " + no);
		
		//===========================
		
		MultipartFile mf = request.getFile("sImg");
		//MultipartFile��ü�� ��, �̋��� getParameter��� getFile()�޼��带 ���
		System.out.println(mf);
		
		String path = "D:/TeamProject/Store/";
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
		
		
		String imgname1 = request.getParameter("sImg");
		String Simg = imgname1;
		
		Simg = dfile;
		
		//===========================
		
		sdao.ImgModify(no, Simg);
		
		System.out.println("�̹��� ���� ����");
		return "redirect:StoreList";
	}
	
	//���Ի����ϱ�
	@RequestMapping("StoreDelete")
	public String StoreDelete(HttpServletRequest request, Model model) {
		System.out.println("StoreDelete ��Ʈ�ѷ� ����");
		
		int no = Integer.parseInt(request.getParameter("sNo"));
		String simg = request.getParameter("sImg"); //�����̸�
		String path = "D:/TeamProject/Store/" + simg;
		
		//���ϻ���
		File file = new File(path);
		System.out.println("���" + path);
		file.delete(); 
		
		//DB����
		sdao.StoreDelete(no);
		
		System.out.println("���� ����");	
		
		return "redirect:StoreList";
		
	}
	
	//=====================================================================
	//�޴�����Ʈ
	@RequestMapping("StoreMenu")
	public String StoreMenu(HttpServletRequest request, Model model) {
		System.out.println("StoreMenu ��Ʈ�ѷ� ����");
		
		//=======================
		//�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�
		int sno = Integer.parseInt(request.getParameter("sNo"));
		System.out.println("sno : " + sno);
		
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
		
		return "Manager/Store/MMenu_view";
		
	}
	
}