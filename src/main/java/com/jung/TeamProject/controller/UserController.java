package com.jung.TeamProject.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.jung.TeamProject.command.BCommand;
import com.jung.TeamProject.command.BIdFindCommand;
import com.jung.TeamProject.command.BModCommand;
import com.jung.TeamProject.command.BPwFindCommand;
import com.jung.TeamProject.dao.BDaoTemplate;
import com.jung.TeamProject.dao.UserDao;
import com.jung.TeamProject.dto.OrderDetailDto;
import com.jung.TeamProject.dto.OrderDto;
import com.jung.TeamProject.dto.UserDto;
import com.jung.TeamProject.util.Constant;
import com.jung.social.NaverLoginBO;

@Controller
public class UserController {
	
	private JdbcTemplate template;
	private PlatformTransactionManager transactionManager;
	private BDaoTemplate dao;
	
	private BCommand com;
	
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template;
	}
	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		Constant.transactionManager = this.transactionManager;
	}
	
	@Autowired
	public void setDao(BDaoTemplate dao) {
		this.dao = dao;
		Constant.dao = this.dao;
	}
	
	@Autowired
	private UserDao udao;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	//ȸ������
	@RequestMapping("join")
	public String join(HttpServletRequest request, Model model) {
		
		//��й�ȣ ��ȣȭ ���� �߰� �κ�
		System.out.println("��й�ȣ ��ȣȭ ����");
		String u1Pw = request.getParameter("user_pw"); // �Է��� ��й�ȣ ���� u1Pw�� ����
		String uPw_org = u1Pw;
		u1Pw = passwordEncoder.encode(uPw_org);//��ȣȭ �ܰ�	
		System.out.println(u1Pw);		
		System.out.println("��й�ȣ ��ȣȭ ��ȯ");
		//��й�ȣ ��ȣȭ ��������� �߰��κ� �ؿ� ��й�ȣ�� �տ��� request.getParameter�� �޾ƿͼ� �ٷ� �׳� �Է�
		
		String result = 
		udao.Join(request.getParameter("user_id"), u1Pw, request.getParameter("user_name"), request.getParameter("user_sex"), 
					request.getParameter("user_email"), request.getParameter("user_phone"), request.getParameter("user_profile"),
					request.getParameter("user_ag1"), request.getParameter("user_ag2"), request.getParameter("user_code"));
		
		System.out.println("�������� : " + result);
		
		if(result.equals("join-success")) {
			System.out.println("���� ����");	
			return "redirect:login_view";
		}
		else {
			System.out.println("���� ����");
			return "redirect:join_view";//���� ������ �ϳ����� ����̳� �������� �ٷ� ȸ�������������� �Ѿ���ص���.
		}
		
	}
	
	//ȸ������ ���̵� �ߺ�üũ
	@RequestMapping("IdCheck")
		public @ResponseBody String IdCheck(UserDto dto1) {
		System.out.println("���̵� �ߺ��˻� ���� : " + dto1);
		
		String result = null;
		
		try {
		
			String str = udao.IdCheck(dto1); //dao���� ���̵� ��������
		
			if(str == null) {
				System.out.println("DB�� ���̵� ����");
				result = "ok";
			}
			else {
				System.out.println("DB�� ���̵� ����");
				result = "no";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(result);
	
		return result;
	
	}
	
	//ȸ�� �ֹ�����
	@RequestMapping("MyOrderInfo")
	public String MyOrderInfo(HttpServletRequest request, Model model, OrderDto dto) {
		
		String uId = request.getParameter("user_id");
		System.out.println("�ֹ����� ��Ʈ�ѷ��� ���� ȸ�����̵�� : " + uId);
		dto.setUser_id(uId);
		
		ArrayList<OrderDto> dto1 = udao.MyOrderInfo(dto);
		model.addAttribute("MyOrderInfo", dto1); //�޴���Ͻ� ��ü��ȣ �޾Ƽ� �����ϱ� ���� �ʿ�
			
		return "Main/UserOrderInfo_view";
	}
	
	//ȸ�� �ֹ����� ��¥ ��ȸ
	@RequestMapping("OrderDcheck")
	public String OrderDcheck(HttpServletRequest request, Model model) throws ParseException {
		
		System.out.println("123");
		String uId = request.getParameter("user_id");
		String date = request.getParameter("oDate");
		String date1 = request.getParameter("oDate1");	
		System.out.println("date : 	" + date + "  date1 : " + date1);
		
		Date nDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		Date nDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		System.out.println("nDate : " + nDate + "nDate1 : " + nDate1);
		
		ArrayList<OrderDto> dto1 = udao.OrderDcheck(uId, nDate, nDate1);
		model.addAttribute("MyOrderInfo", dto1);
		 
		System.out.println("123111111 : " + dto1); 
	
		return "Main/UserOrderInfo_view"; 

	}
	
	//ȸ�� �ֹ� �󼼳���
	@RequestMapping("MyOrderInfoDetail")
	public String MyOrderInfoDetail(HttpServletRequest request, Model model, OrderDto dto, OrderDetailDto dto1,
										@RequestParam(value = "oNo", required = false) String oNo) throws ParseException {
		
		System.out.println("�ֹ���ȣ : " + oNo); 
		
		dto.setoNo(oNo); //�ֹ����̺�
		dto1.setoNo(oNo); //�ֹ������̺�
		
		//�ֹ����̺� ����
		ArrayList<OrderDto> od1 = udao.MyOrderInfoDetail(dto);
		model.addAttribute("MyOrderInfoDetail", od1);
		
		//�ֹ������̺� ����
		ArrayList<OrderDetailDto> od2 = udao.MyOrderInfoDetail1(dto1);
		model.addAttribute("MyOrderInfoDetail1", od2);
		
		return "Main/UserOrderInfoDetail_view"; 

	}
	
	
	
	//�α���
	@RequestMapping(value = "login1", method = RequestMethod.GET)
	public ModelAndView login(Model md, HttpSession session,
			@RequestParam(value = "log", required = false) String log,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		
		System.out.println("��Ʈ�ѷ��� ����");
		
		ModelAndView model = new ModelAndView();
		
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		
		System.out.println("���̹�" + naverAuthUrl);
		
		//���̹� url
		md.addAttribute("url", naverAuthUrl);
		
		if (log != null) {
			System.out.println("login log ����");
			//model.addObject("log", "before login!");
			model.setViewName("Main/login");
		} 
		if (error != null) {
			System.out.println("login error ����");
			model.setViewName("Main/login");
			//model.addObject("error", "Invalid username and password!");
		} 
		//�α׾ƿ� ��ư ���� �̵��ϴ� ������ �̸�
		if (logout != null) {
			System.out.println("login logout ����");
			//model.addObject("msg", "You've been logged out successfully.");
			model.setViewName("Main/login");
		}
		
		System.out.println("model : " + model);
		
		return model;		
	}
	
	//���̵� ã��
	@RequestMapping("findIdDo")
	public String findId(HttpServletRequest request, HttpServletResponse response, Model md) throws Exception{
		System.out.println("findIdDo controller");

		com = new BIdFindCommand();
		com.execute(request, md);
		String uEmail = (String)request.getAttribute("uEmail");		
		
		response.setContentType("text/plain; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		String result = (String)request.getAttribute("result");
		System.out.println("��Ʈ�ѷ� ���� id�� : " + result);
		
		md.addAttribute("id", result);
		
		return "Main/User/findIdResult";
	}
	
	//�ӽ� ��й�ȣ
	@RequestMapping("findPwDo")
	public String findPwDo(HttpServletRequest request, HttpServletResponse response, Model md, UserDto dto) throws Exception {
		ModelAndView mav;
			
		System.out.println("findPwDo ��Ʈ�ѷ�");
		com = new BPwFindCommand();
		com.execute(request, md);
		
		
		int result = (int) request.getAttribute("result");
		
		response.setContentType("text/plain; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();		
				
		if(result == 1) {
			// �ӽ� ��й�ȣ ����
			String pw = "";
			for (int i = 0; i < 12; i++) {
				pw += (char) ((Math.random() * 26) + 97);
			}
			String u1Pw = pw;
			String uPw_org = u1Pw;
			u1Pw = passwordEncoder.encode(uPw_org);
			System.out.println(pw);
			dto.setUser_pw(u1Pw);
			// ��й�ȣ ����
			udao.update_pw(dto);			
			
			// ��й�ȣ ���� ���� �߼�
			dao.send_mail(dto, "findPwDo", pw);
			
			return "Main/User/findPwResult";
		}
		else {
			return "Main/User/findPwError";
		}
	}
	
	//ȸ������ ����ȭ��
	@RequestMapping("memberModify")
	public String join_view(HttpServletRequest request, HttpServletResponse response, Model md) {
		System.out.println("memberModify ��Ʈ�ѷ�");
		com = new BModCommand();
		com.execute(request, md);		
		return "Main/User/memberModiView";
	}
	
	//ȸ������ �������� ��й�ȣó��
	@RequestMapping("pwModiDo")
	public void pwModiDo(HttpServletRequest request, UserDto dto) {		
		String pw = request.getParameter("user_pw");		
		String uPw = passwordEncoder.encode(pw);
		
		dto.setUser_id(request.getParameter("logid"));
		dto.setUser_pw(uPw);
		
		udao.pwModiDo(dto);
		System.out.println("udao�� ������ ����");
	}
	
	//ȸ������ ����ó��
	@RequestMapping("memberModiDo")
	public String memberModiDo(HttpServletRequest request, UserDto dto) {
		dto.setUser_id(request.getParameter("logid"));
		dto.setUser_name(request.getParameter("user_name"));
		dto.setUser_sex(request.getParameter("user_sex1"));
		dto.setUser_email(request.getParameter("user_email"));
		dto.setUser_phone(request.getParameter("user_phone"));
		dto.setUser_profile(request.getParameter("user_profile"));
		
		udao.memberModiDo(dto);
		System.out.println("udao�� ������ ����");
		
		return "Main/main_view";
	}
	
	@RequestMapping("memberDelDo")
	public String memberDelDo(HttpServletRequest request, UserDto dto, Model md) {
		
		dto.setUser_id(request.getParameter("logid"));
		udao.memberDelDo(dto);
		System.out.println("udao�� ������ ����");
		
		return "Main/User/memDelComp";
	}


	/* NaverLoginBO */
	private NaverLoginBO naverLoginBO;
	
	private String apiResult = null;
	
	@Autowired  //bean����
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}
	
	//���̹� �α��� ������ callbackȣ�� �޼ҵ�
	@RequestMapping("callback")
	public ModelAndView callback(@RequestParam String code, @RequestParam String state, HttpSession session) throws IOException {
		OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
		String apiResult = naverLoginBO.getUserProfile(oauthToken);
		
		System.out.println("Naver login success");
		
		return new ModelAndView("mainFrame", "result", apiResult);
	}
	
}