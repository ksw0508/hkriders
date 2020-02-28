package com.jung.TeamProject.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jung.TeamProject.dao.StoreDao;
import com.jung.TeamProject.dao.UserDao;
import com.jung.TeamProject.dto.MenuDto;
import com.jung.TeamProject.dto.StoreDto;

@Controller
public class MainController {
	
	@Autowired
	private StoreDao sdao;
	
	@Autowired
	private UserDao udao;
	
	//����ã��(ī�װ���)
	@RequestMapping("Category")
	public String Category(HttpServletRequest request, Model model) {
		
		String result = null;
		
		System.out.println("Category ��Ʈ�ѷ� ����");
		
		String sLat = request.getParameter("sLat");
		String sLng = request.getParameter("sLng");
		String sCategory = request.getParameter("sCategory");
		
		System.out.println("sLat : " + sLat + " sLng : " + sLng + " ī�װ� : " + sCategory);
		
		ArrayList<StoreDto> str = sdao.Category(sLat, sLng, sCategory);
		
		System.out.println("str : " + str);
		System.out.println("DB���ٿ�");
	
		if(str.isEmpty()) {
			System.out.println("����� ����.");
			return "Main/NoneStore";
		}
		else {
			System.out.println("�������");
			model.addAttribute("searchlist", str);
			return "Main/SearchList_view";
		}

	}
	
	//���� ã��(����,�浵�� �´� ���� ����Ʈ�� �Ѹ�) + ��ü����
	@RequestMapping("search")
	public String search(HttpServletRequest request, Model model) {

		System.out.println("��Ʈ�ѷ� ����");
		
		String sLat = request.getParameter("sLat");
		String sLng = request.getParameter("sLng");
		String sCategory = request.getParameter("sCategory");
		
		System.out.println("sLat : " + sLat + " sLng : " + sLng + " ī�װ� : " + sCategory);
		
		ArrayList<StoreDto> str = sdao.AdSearch(sLat, sLng);
		
		System.out.println("str : " + str);
		System.out.println("DB���ٿ�");
	
		if(str.isEmpty()) {
			System.out.println("����� ����.");
			return "Main/NoneStore";
		}
		else {
			System.out.println("�������");
			model.addAttribute("searchlist", str);
			return "Main/SearchList_view";
		}

	}
	
	//ã������ �޴� ����Ʈ
	@RequestMapping("SearchMenuList")
	public String SearchMenuList(HttpServletRequest request, Model model) {
		System.out.println("SearchMenuList ��Ʈ�ѷ� ����");
		
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
		
		return "Main/SearchMenuList_view";
		
	}
	
	//���Ծ����� ����� �̵�
	@RequestMapping("NoneStore")
	public String NoneStore() {
		return "Main/NoneStore";
	} 
	
	//�ֹ�
	@RequestMapping("/MenuOrder")
	public String MenuOrder(HttpServletRequest request, Model model) throws ParseException {
		
		System.out.println("Order ��Ʈ�ѷ� ����");
		
		//�ֹ���ȣ �����
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String ymd = ym +  new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String subNum = "";
		
		//���ó�¥ Date�������� ��ȯ�ؼ� �ֱ�
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd");		
		Date date = new Date();
		String date1 = format1.format(date); //date -> String	
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);

		for(int i = 1; i <= 6; i ++) {
			subNum += (int)(Math.random() * 10);
		}
		 
		String oNo = ymd + "-" + subNum; //�ֹ���ȣ
		
		//�޴��ֹ�(�ּҳ���)
		int amount = Integer.parseInt(request.getParameter("oAmount"));
		
		int sno = Integer.parseInt(request.getParameter("sNo"));
		String result = 
				udao.Order1(oNo, request.getParameter("osName"), request.getParameter("oName"), request.getParameter("oAd1"),
								request.getParameter("oAd2"), request.getParameter("oPhone"), request.getParameter("oText"),
								amount, request.getParameter("user_id"), date2, sno);
		
		
		if(result.equals("order-success")) {
			
			System.out.println("1�� �ֹ� ����");	
			
			String[] omNo = request.getParameterValues("omNo"); //����
			String [] omStock = request.getParameterValues("omStock"); //����
			String[] omPrice = request.getParameterValues("omPrice"); //����
			
			String [] omName = request.getParameterValues("omName");
			
			int[] omno = null;
			int[] omstock = null;
			int[] omprice = null;
			
			if( omNo != null ) {
				
				omno = new int[omNo.length];
				omstock = new int[omStock.length];
				omprice = new int[omPrice.length];
				
				for(int i=0; i<omno.length; i++) {
					
					omno[i] = Integer.parseInt(omNo[i]);
					omstock[i] = Integer.parseInt(omStock[i]);
					omprice[i] = Integer.parseInt(omPrice[i]);
					
					udao.Order(omno[i], omName[i], omstock[i], omprice[i], oNo);
					
				}
			}
			
			System.out.println("�ֹ��󼼳����� ����");
			return "redirect:home";
		}
		else {
			System.out.println("�ֹ� ����");
			return "redirect:home";//���� ������ �ϳ����� ����̳� �������� �ٷ� ȸ�������������� �Ѿ���ص���.
		}
		
	}

}
