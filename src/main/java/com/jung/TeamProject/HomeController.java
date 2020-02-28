package com.jung.TeamProject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jung.TeamProject.dao.StoreDao;
import com.jung.TeamProject.dto.ApplyDto;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "Main/main_view";//ùȭ��
	}
	
	@Autowired
	private StoreDao sdao;
	
	//��ٱ���
	@RequestMapping("cart")
	public String cart() {
		return "redirect:html/cart.html";
	}

	//�޴���� ���ȭ��
	@RequestMapping("MModal")
	public String MenuModal() {
		return "Main/MenuModal";
	}
	
	//Order_view(�ֹ��ϴ�������)
	@RequestMapping("Order_view") //Ȩ�� ����
	public String Order_view() {
		return "Main/Order_view";
	}
	
	//====================================================================== ���� ����ϴ� �Ϲ����� ���� Ȩ������
	
	//header
	@RequestMapping("header") //Ȩ�� ����
	public String header() {
		return "Main/header";
	}
	
	//header1
	@RequestMapping("header1") //Ȩ�� ����
	public String header1() {
		return "Main/header1";
	}
	
	//�̺�Ʈ ���
	@RequestMapping("EventBanner") //Ȩ�� ����
	public String EventBanner() {
		return "Main/EventBanner";
	}
	
	//�ּҰ˻�(����ã�µ� ���� �˻�â)
	@RequestMapping("AddressSection") //Ȩ�� ����
	public String AddressSection() {
		return "Main/AddressSection";
	}
	
	//ī�װ���
	@RequestMapping("CategoryBar") //Ȩ�� ����
	public String CategoryBar() {
		return "Main/CategoryBar";
	}
	
	//footer
	@RequestMapping("footer") //Ȩ�� ����
	public String footer() {
		return "Main/footer";
	}
	
	//Ȩ = main
	@RequestMapping("home") //Ȩ�� ����
	public String home() {
		return "Main/main_view";
	}
	
	//ȸ������ȭ�� �̵�
	@RequestMapping("join_view")
	public String join_view() {
		return "Main/join_view";
	}
	
	
	//����ȭ�鿡�� �α���Ŭ���Ͽ� �α��������� �̵�
	@RequestMapping("login_view")
	public String login_view() {
		return "Main/login";
	}
	
	//���� ã�� �� ī�װ����� �̵��ϴµ� �ʿ��� ī�װ� �޴���
	@RequestMapping("CatagoryBar")
	public String CatagoryBar() {
		return "Main/Catagory_Bar";
	}
	
	//====================================================================== ȸ������ �α��� ȸ��ó������ ������
	
	//���̵� ã��
	@RequestMapping("findIdView")
	public String findIdView() throws Exception{
		return "Main/User/findIdView";
	}
	
	//��й�ȣ ã��
	@RequestMapping("findPwView")
	public String findPwView() throws Exception {
		return "Main/User/findPwView";
	}
	
	//ȸ������
	@RequestMapping("pwModiView")
	public String pwModiView() {
		System.out.println("pwModiView ����");
		return "Main/User/pwModiView";
	}
	
	//ȸ��Ż��
	@RequestMapping("memDelView")
	public String memberDelView() {
		return "Main/User/memDelView";
	}
		
	
	//====================================================================== ������ ������
	
	
	//������ ���� ������
	@RequestMapping("ManagerHome")
	public String ManagerHome() {
		return "Manager/MMain_view";
	}
	
	//������ ���Ե�� ������
	@RequestMapping("StoreRegister")
	public String Store_Register(HttpServletRequest request, Model model) {
		
		String uId = request.getParameter("user_id");
		
		ArrayList<ApplyDto> dto = sdao.RegisterView(uId);
		model.addAttribute("RegisterView", dto);
		
		return "Manager/Store/StoreRegister_view";
	}
	
	
	//====================================================================== ����������
	
	//������� ������
	@RequestMapping("BossHome")
	public String BossHome() {
		return "Boss/BMain_view";
	}
	
	//�������� ������
	@RequestMapping("StoreApply")
	public String StoreApply() {
		return "Boss/Apply/Apply_view";
	}
	
	//�������� ���� ������
	@RequestMapping("ApplyFalse")
	public String ApplyFalse() {
		return "Boss/Apply/Apply_False";
	}

}