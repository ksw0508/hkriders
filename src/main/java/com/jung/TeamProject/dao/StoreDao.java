package com.jung.TeamProject.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import com.jung.TeamProject.dto.StoreDto;
import com.jung.TeamProject.util.Constant;
import com.jung.TeamProject.dto.ApplyDetailDto;
import com.jung.TeamProject.dto.ApplyDto;
import com.jung.TeamProject.dto.MenuDto;
import com.jung.TeamProject.dto.OrderDetailDto;
import com.jung.TeamProject.dto.OrderDto;

@Repository //DAOŬ������ ��Ÿ����  beanŬ������ ��� ������ ��
public class StoreDao implements SDao {
	
	JdbcTemplate template;
	
	@Autowired  //servlet-context.xml bean bound
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	PlatformTransactionManager transactionManager;
	
	@Autowired 
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Autowired
	private SqlSession sqlSession; //field�� autowired
	
	public StoreDao() {
		this.template = Constant.template;
		this.transactionManager = Constant.transactionManager;
	}
	
	//���Ե��ȭ��(ȸ�����̵� ÷��)
	@Override
	public ArrayList<ApplyDto> RegisterView(String user_id) {
		
		ArrayList<ApplyDto> result = (ArrayList) sqlSession.selectList("RegisterView", user_id);
		return result;
		
	}
	//���Ե��
	@Override
	public void Register(String sName, String bName, String bNo, String sImg, String sAd, String sLat, String sLng,
								String sPhone, String sCategory, int sTip, int mPrice, String user_id) {
		
		StoreDto dto = new StoreDto(0, sName, bName, bNo, sImg, sAd, sLat, sLng, sPhone, sCategory, sTip, 
											mPrice, null, user_id, null);
		sqlSession.insert("Register", dto);
		
	}
	//���Ե���ϸ� ����ó�����°� ó���Ϸ�� �ٲٱ�
	@Override
	public void ApplyStatusEnd(int asNo) {
		
		ApplyDto dto = new ApplyDto();
		dto.setAsNo(asNo);
		sqlSession.insert("ApplyStatusEnd", dto);
		
	}
	
	//��ü ���� ����Ʈ(������)
	@Override 
	public ArrayList<StoreDto> Storelist() {		
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("Storelist");
		System.out.println("DB �� : " + result);
		return result;
	}
	
	//�� ���� ����Ʈ(����)
	@Override
	public ArrayList<StoreDto> MyStorelist(String user_id) {
		StoreDto dto = new StoreDto();
		dto.setUser_id(user_id);
		
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("MyStorelist", dto);
		System.out.println("DB �� : " + result);
		return result;
	}
	
	//���� �󼼸���Ʈ
	@Override
	public ArrayList<StoreDto> StoreDetail(int sNo) {
		System.out.println("StoreDao ����");
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("StoreDetail", sNo);
		System.out.println("���� �󼼸���Ʈ DB �� : " + result);
		return result;
	}
	
	//���� ���� ȭ��(������)
	@Override
	public ArrayList<StoreDto> RegisterContent(int sNo) {
		System.out.println("StoreDao����");
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("RegisterContent", sNo);
		System.out.println("DB �� : " + result);
		return result;
	}
	
	//���Գ������(������)
	@Override
	public void StoreModify(int sNo, String sName, String bName, String bNo, String sAd, String sLat,
			String sLng, String sPhone, String sCategory, int sTip, int mPrice) {
		
		System.out.println("����dao����");
		
		StoreDto dto = new StoreDto(sNo, sName, bName, bNo, null, sAd, sLat, sLng, sPhone, sCategory, sTip, mPrice, null, null, null);
		
		sqlSession.insert("StoreModify", dto);
		
		System.out.println("dao �����̸� 1 : " + sqlSession.insert("StoreModify", dto));
		
	}
	
	//���Լ���(����)
	@Override
	public void BSModify(int sNo, int sTip, int mPrice) {
		
		System.out.println("BSModify dao����");

		StoreDto dto = new StoreDto();
		dto.setsNo(sNo);
		dto.setsTip(sTip);
		dto.setmPrice(mPrice);
		
		sqlSession.insert("BSModify", dto);
		
	}
	
	//���� �̹��� ����(������)
	@Override
	public void ImgModify(int sNo, String sImg) {
		
		System.out.println("����dao����");
		StoreDto dto = new StoreDto();
		dto.setsNo(sNo);
		dto.setsImg(sImg);
		sqlSession.insert("ImgModify", dto);
		
	}
	
	//���� ����
	@Override
	public void StoreDelete(int sNo) {
		
		sqlSession.delete("StoreDelete",sNo);
		System.out.println("�������� ���� ��� : " + sqlSession.delete("StoreDelete", sNo));
		
	}
	
	//���ԯ���(ī�װ�)
	@Override
	public ArrayList<StoreDto> Category(String sLat, String sLng, String sCategory) {
		
		System.out.println("ī�װ� ����ã��");
		
		StoreDto dto = new StoreDto();
		dto.setsLat(sLat);
		dto.setsLng(sLng);
		dto.setsCategory(sCategory);
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("Category", dto);
		System.out.println("DB �� : " + result);
		
		return result;
		
	}
	
	
	//����ã�� AdSearch()�̸��� selectList(�̸�)�ϰ� mapper id�ϰ� 3������ ��� ���ƾ� �Ѵ�. --> ���� + ��ü����
	@Override
	public ArrayList<StoreDto> AdSearch(String sLat, String sLng) {
		
		System.out.println("����ã��");
		
		StoreDto dto = new StoreDto();
		dto.setsLat(sLat);
		dto.setsLng(sLng);
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("AdSearch", dto);
		System.out.println("DB �� : " + result);
		
		return result;
		
	}

	//=============================================================================================
	
	//�������� ���(����)
	@Override
	public String Apply(String asName, String abNo, String asLat, String asLng, String asAd, String asPhone,
			String asCategory, String user_id) {
		
		ApplyDto dto = new ApplyDto(0, asName, abNo, asLat, asLng, asAd, asPhone, asCategory, null, null, user_id);
		
		int result = sqlSession.insert("Apply", dto);
		
		if(result > 0)	{ //1�̸� ���Լ���
			System.out.println("Apply db��� ����");
			return "Apply-success";
		}
		else {
			System.out.println("Apply db��� ����");
			return "Apply-failed";
		}
		
	}
	
	//�������� ����Ʈ(������)
	@Override 
	public ArrayList<ApplyDto> Applylist() {
		
		ArrayList<ApplyDto> result = (ArrayList) sqlSession.selectList("Applylist");
		System.out.println("DB �� : " + result);
		
		return result;
	}
	
	//�������� ����Ʈ(���岨�� ���̰�)
	@Override
	public ArrayList<ApplyDto> MyApplylist(String user_id) {
		
		ApplyDto dto = new ApplyDto();
		dto.setUser_id(user_id);
		
		ArrayList<ApplyDto> result = (ArrayList) sqlSession.selectList("MyApplylist", dto);
		System.out.println("DB �� : " + result);
		return result;
	}
		
	//�������� �� ����Ʈ(����,������)
	@Override
	public ArrayList<ApplyDetailDto> ApplyDetail(int asNo) {
		System.out.println("StoreDao ����");
		ArrayList<ApplyDetailDto> result = (ArrayList) sqlSession.selectList("ApplyDetail", asNo);
		System.out.println("�󼼸���Ʈ DB �� : " + result);
		return result;
	}
	
	//�������� ����ȭ��(����)
	@Override
	public ArrayList<ApplyDto> ApplyContent(int asNo) {
		System.out.println("StoreDao����");
		ArrayList<ApplyDto> result = (ArrayList) sqlSession.selectList("ApplyContent", asNo);
		System.out.println("DB �� : " + result);
		return result;
		
	}
	
	//�������� ����ó��(����)
	@Override
	public void ApplyModify(int asNo, String asName, String abNo, String asLat, String asLng, String asAd, String asPhone,
			String asCategory) {
		
		ApplyDto dto = new ApplyDto(asNo, asName, abNo, asLat, asLng, asAd, asPhone, asCategory, null, null ,null);
		
		sqlSession.insert("ApplyModify", dto);
		System.out.println("apply db���� ����");
		
	}
	
	//�������� ����(����)
	@Override
	public void ApplyDelete(int asNo) {
		
		sqlSession.delete("ApplyDelete",asNo);
		System.out.println("�������� ���� ��� : " + sqlSession.delete("ApplyDelete", asNo));
		
	}
	
	//=============================================================================================
	
	//�޴� ���
	@Override
	public String MenuRegister(String mName, String mImg, int mPrice, String mTitle1, String mTitle2, String mInfo, int sNo) {
		
		MenuDto dto = new MenuDto(0, mName, mImg, mPrice, mTitle1, mTitle2, mInfo, null, sNo, null);
		
		int result1
		= sqlSession.insert("MenuRegister", dto); 
	
		System.out.println("result1 : " + result1);
		
		if(result1 > 0 ) {
			return "Mregister-success";
		}
		else {
			return "Mregister-failed";
		}
		
	}
	
	//�޴� ����(���븸)
	@Override
	public void MenuModify(int mNo, String mName, int mPrice, String mInfo) {
		
		System.out.println("MenuModify dao����");
		
		MenuDto dto = new MenuDto();
		dto.setmNo(mNo);
		dto.setmName(mName);
		dto.setmPrice(mPrice);
		dto.setmInfo(mInfo);
		
		sqlSession.insert("MenuModify", dto);
		
	}
	
	//�޴��̹��� ����ȭ��(���)
	@Override
	public ArrayList<MenuDto> MImgModify(int mNo) {
		System.out.println("MImgModify Dao����");
		MenuDto dto = new MenuDto();
		dto.setmNo(mNo);
		ArrayList<MenuDto> result = (ArrayList) sqlSession.selectList("MImgModify", dto);
		System.out.println("DB �� : " + result);
		return result;	
	}
	
	//�޴��̹��� ����
	@Override
	public void BMImgModify(int mNo, String mImg) {
		System.out.println("����dao����");
		MenuDto dto = new MenuDto();
		dto.setmNo(mNo);
		dto.setmImg(mImg);
		sqlSession.insert("BMImgModify", dto);
	}
	
	
	//��ǥ�޴� ����Ʈ
	@Override
	public ArrayList<MenuDto> MTList(int sNo) {
		System.out.println("MTListDao����");
		MenuDto dto = new MenuDto();
		dto.setsNo(sNo);
		ArrayList<MenuDto> result = (ArrayList) sqlSession.selectList("MTList", dto);
		System.out.println("DB �� : " + result);
		return result;	
	}
	
	//���θ޴� ����Ʈ
	@Override
	public ArrayList<MenuDto> MMList(int sNo) {
		System.out.println("MMListDao����");
		MenuDto dto = new MenuDto();
		dto.setsNo(sNo);
		ArrayList<MenuDto> result = (ArrayList) sqlSession.selectList("MMList", dto);
		System.out.println("DB �� : " + result);
		return result;	
	}
	
	//���̵�޴� ����Ʈ
	@Override
	public ArrayList<MenuDto> MSList(int sNo) {
		System.out.println("MSListDao����");
		MenuDto dto = new MenuDto();
		dto.setsNo(sNo);
		ArrayList<MenuDto> result = (ArrayList) sqlSession.selectList("MSList", dto);
		System.out.println("DB �� : " + result);
		return result;	
	}
	
	//�޴�����
	@Override
	public void MenuDelete(int sNo) {		
		sqlSession.delete("MenuDelete",sNo);
		System.out.println("�������� ���� ��� : " + sqlSession.delete("MenuDelete", sNo));
	}
	
	//=========================================================================
	
	//Today �ֹ�����(����)
	@Override
	public ArrayList<OrderDto> MyStoreOrder(OrderDto dto) {
		
		System.out.println("������ �ֹ���� dao ����");
		
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("MyStoreOrder", dto);
		
		return result;
	}
	
	//Today �ֹ����º� ����Ʈ(����)
	@Override
	public ArrayList<OrderDto> StatusOrderList(OrderDto dto) {
		
		System.out.println("������ �ֹ����º� ��� dao ����");
		
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("StatusOrderList", dto);
		
		return result;
		
	}

	//�ֹ��󼼳��� - �ּ�(����)
	@Override
	public ArrayList<OrderDto> OrderDetail1(OrderDto dto) {
		System.out.println("������ �ֹ��󼼳��� ��� dao ����");
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("OrderDetail1", dto);
		return result;
	}
	
	//�ֹ��󼼳��� - �޴�(����)
	@Override
	public ArrayList<OrderDetailDto> OrderDetail2(OrderDetailDto dto) {
		System.out.println("������ �ֹ��󼼳��� ��� dao ����");
		ArrayList<OrderDetailDto> result = (ArrayList) sqlSession.selectList("OrderDetail2", dto);
		return result;
	}
	
	//�ֹ����� �ֹ�����ó�� ����(����)
	@Override
	public void oStatusModify(OrderDto dto) {
		sqlSession.insert("oStatusModify", dto);	
	}
	
	//�ֹ���ü����(����)
	@Override
	public ArrayList<OrderDto> AllOrderList(OrderDto dto) {
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("AllOrderList", dto);
		return result;
	}
	//��ü�ֹ� ��¥��ȸ�� ���(����)
	@Override
	public ArrayList<OrderDto> AllOrderCheck(String user_id, Date oDate, Date oDate1) {
		
		OrderDto dto = new OrderDto();
		
		dto.setUser_id(user_id);
		dto.setoDate(oDate);
		dto.setoDate1(oDate1);
		
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("AllOrderCheck", dto);		
		return result;	
		
	}
	
	
}