package com.jung.TeamProject.dao;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jung.TeamProject.dto.OrderDetailDto;
import com.jung.TeamProject.dto.OrderDto;
import com.jung.TeamProject.dto.UserDto;

//mybatis����� ���ؼ��� SqlSessionŬ������ �̿���
@Repository //DAOŬ������ ��Ÿ����  beanŬ������ ��� ������ ��
//(@Controller,@Service�� CommandŬ����,@Repository�� DaoŬ����)
public class UserDao implements UDao {
	
	JdbcTemplate template;
	
	@Autowired
	private SqlSession sqlSession; //field�� autowired
	
	public UserDao() {	
	}	

	//ȸ������
	@Override
	public String Join(String user_id, String user_pw, String user_name, String user_sex, String user_email, 
							String user_phone, String user_profile, String user_ag1, String user_ag2, String user_code) {
		
		UserDto dto = new UserDto(0, user_id, user_pw, user_name, user_sex, user_email, user_phone, 
															user_profile, user_ag1, user_ag2, user_code, null);
		
		System.out.println("���� ����1");
		
		int result;
		
		result = sqlSession.insert("Join",dto);
		System.out.println("result : " + result);
		
		if(result > 0)	{ //1�̸� ���Լ���
			return "join-success";
		}
		else {
			return "join-failed";
		}
		
	}
	
	//ȸ������Ʈ
	@Override 
	public ArrayList<UserDto> Userlist() {
		ArrayList<UserDto> result = (ArrayList) sqlSession.selectList("Userlist");
		System.out.println("DB �� : " + result);
		return result;
	}
	
	//ȸ��������
	@Override
	public ArrayList<UserDto> UserDetail(int user_no) {
		//�̰�� user_no�� int�� �־��⶧���� mapper���� user_no�Ķ���͸� Integer�� ����Ѵ�.
		//���� UserDto dto = new UserDto(user_no,....������ null) �̰ų�
		//UserDto dto = new UserDto()
		//dto.setUser_no();�� ���� dto�� �־��⶧���� �Ķ���͵� dto�� ����� �Ѵ�.
		
		ArrayList<UserDto> result = (ArrayList) sqlSession.selectList("UserDetail", user_no);
		return result;
	}

	//���̵� �ߺ�üũ
	@Override
	public String IdCheck(UserDto dto1) {
		System.out.println("���̵� �ߺ� üũ");
		return sqlSession.selectOne("IdCheck",dto1); 
	}
	
	// ��й�ȣ ����
	@Override
	public int update_pw(UserDto dto){
		return sqlSession.update("UpdatePw", dto);
	}
	
	// ��й�ȣ ����
	@Override
	public void pwModiDo(UserDto dto){
		sqlSession.update("pwModiDo", dto);
	}
	
	// ȸ������ ����
	@Override
	public void memberModiDo(UserDto dto){
		sqlSession.update("memberModiDo", dto);
	}
	
	//ȸ������ ����
	@Override
	public void memberDelDo(UserDto dto) {
		sqlSession.delete("memberDelDo", dto);
	}
	
	//�޴� �ֹ�(�ּҳ���)
	@Override
	public String Order1(String oNo, String osName, String oName, String oAd1, String oAd2, String oPhone, String oText,
			int oAmount, String user_id, Date oDate, int sNo) {
		
		OrderDto dto = new OrderDto();
		dto.setoNo(oNo);
		dto.setOsName(osName);
		dto.setoName(oName);
		dto.setoAd1(oAd1);
		dto.setoAd2(oAd2);
		dto.setoPhone(oPhone);
		dto.setoText(oText);
		dto.setoAmount(oAmount);
		dto.setUser_id(user_id);
		dto.setoDate(oDate);
		dto.setsNo(sNo);
		
		int result;
		
		result = sqlSession.insert("Order1",dto);
		System.out.println("result : " + result);
		
		if(result > 0)	{ //1�̸� �ֹ�����
			return "order-success";
		}
		else {
			return "order-failed";
		}

	}

	//�޴� �ֹ�(�󼼳���)
	@Override
	public void Order(int omNo, String omName, int omStock, int omPrice, String oNo) {
		
		OrderDetailDto dto = new OrderDetailDto();
		
		dto.setOmNo(omNo);
		dto.setOmName(omName);
		dto.setOmStock(omStock);
		dto.setOmPrice(omPrice);
		dto.setoNo(oNo);
		
		sqlSession.insert("Order",dto);
		
	}
	
	//ȸ�� �ֹ� ���
	@Override
	public ArrayList<OrderDto> MyOrderInfo(OrderDto dto) {
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("MyOrderInfo", dto);	
		return result;
	}
	
	//ȸ�� �ֹ� ��¥��ȸ�� ���
	@Override
	public ArrayList<OrderDto> OrderDcheck(String user_id, Date oDate, Date oDate1) {
		
		OrderDto dto = new OrderDto();
		dto.setUser_id(user_id);
		dto.setoDate(oDate);
		dto.setoDate1(oDate1);
		
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("OrderDcheck", dto);
		
		return result;
	}
	
	//ȸ�� �ֹ� �󼼸��(�ֹ����̺�)
	@Override
	public ArrayList<OrderDto> MyOrderInfoDetail(OrderDto dto) {
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("MyOrderInfoDetail", dto);
		return result;
	}
	
	//ȸ�� �ֹ� �󼼸��(�ֹ��� ���̺�)
	@Override
	public ArrayList<OrderDetailDto> MyOrderInfoDetail1(OrderDetailDto dto) {
		ArrayList<OrderDetailDto> result = (ArrayList) sqlSession.selectList("MyOrderInfoDetail1", dto);
		return result;
	}
	
}