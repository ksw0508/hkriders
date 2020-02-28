package com.jung.TeamProject.dao;

import java.util.ArrayList;
import java.util.Date;

import com.jung.TeamProject.dto.OrderDetailDto;
import com.jung.TeamProject.dto.OrderDto;
import com.jung.TeamProject.dto.UserDto;

public interface UDao {
	
	//����
	public String Join(String user_id, String user_pw, String user_name, String user_sex, String user_email, 
							String user_phone, String user_profile, String user_ag1, String user_ag2, String user_code);
	
	//���̵� �ߺ�üũ
	public String IdCheck(UserDto dto1);
	
	//ȸ�����
	public ArrayList<UserDto> Userlist();
	
	//ȸ�� �󼼸��
	public ArrayList<UserDto> UserDetail(int user_no);
	
	//�ӽú�й�ȣ ����
	public int update_pw(UserDto dto);
	
	//��й�ȣ ����
	public void pwModiDo(UserDto dto);
	
	//ȸ������ ����
	public void memberModiDo(UserDto dto);
	
	//ȸ������ ����
	public void memberDelDo(UserDto dto);
	
	//�޴� �ֹ�(�ּҳ���)
	public String Order1(String oNo, String osName, String oName, String oAd1, String oAd2, String oPhone, String oText, 
							int oAmount, String user_id, Date oDate, int sNo);
	
	//�޴� �ֹ�(�󼼳���)
	public void Order(int omNo, String omName, int omStock, int omPrice, String oNo);
	
	//ȸ�� �ֹ� ���
	public ArrayList<OrderDto> MyOrderInfo(OrderDto dto);
	
	//ȸ�� �ֹ� ��¥��ȸ�� ���
	public ArrayList<OrderDto> OrderDcheck(String user_id, Date oDate, Date oDate1);
	
	//ȸ�� �ֹ� �󼼸��(�ֹ����̺�)
	public ArrayList<OrderDto> MyOrderInfoDetail(OrderDto dto);
	
	//ȸ�� �ֹ� �󼼸��(�ֹ��� ���̺�)
	public ArrayList<OrderDetailDto> MyOrderInfoDetail1(OrderDetailDto dto);
	
}