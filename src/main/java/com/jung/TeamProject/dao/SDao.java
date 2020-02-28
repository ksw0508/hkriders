package com.jung.TeamProject.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.jung.TeamProject.dto.StoreDto;
import com.jung.TeamProject.dto.ApplyDetailDto;
import com.jung.TeamProject.dto.ApplyDto;
import com.jung.TeamProject.dto.MenuDto;
import com.jung.TeamProject.dto.OrderDetailDto;
import com.jung.TeamProject.dto.OrderDto;

public interface SDao {
	
	//���Ե�Ͻ� ȸ�����̵� ÷���ؼ� ���Ե�� ȭ�� ����
	public ArrayList<ApplyDto> RegisterView(String user_id);
	
	//���Ե��
	public void Register(String sName, String bName, String bNo, String sImg, String sAd, String sLat, String sLng,
							String sPhone, String sCategory, int sTip, int mPrice, String user_id);
	
	//���Ե���ϸ� ����ó�����°� ó���Ϸ�� �ٲٱ�
	public void ApplyStatusEnd(int asNo);
	
	//���Ը��(������)
	public ArrayList<StoreDto> Storelist();
	
	//�� ���Ը��(����)
	public ArrayList<StoreDto> MyStorelist(String user_id);
	
	//���Ի󼼸��
	public ArrayList<StoreDto> StoreDetail(int sNo);
	
	//���Գ��� ����ȭ�� + �����̹��� ���� ȭ������ �̵�(������)
	public ArrayList<StoreDto> RegisterContent(int sNo);
	
	//���Լ���(������)
	public void StoreModify(int sNo, String sName, String bName, String bNo, String sAd, String sLat, String sLng,
			String sPhone, String sCategory, int sTip, int mPrice);
	
	//���Լ���(����)
	public void BSModify(int sNo, int sTip, int mPrice);
	
	//���� �̹��� ����(������)
	public void ImgModify(int sNo, String sImg);
	
	//���� ����(������)
	public void StoreDelete(int sNo);
	
	//����ã��(ī�װ�)
	public ArrayList<StoreDto> Category(String sLat, String sLng, String sCategory);
	
	//����ã��(���� + ��ü����)
	public ArrayList<StoreDto> AdSearch(String sLat, String sLng);
	
	//=========================================================================
	
	//�������ǵ��(����)
	public String Apply(String asName, String abNo, String asLat, String asLng, String asAd, String asPhone,
			String asCategory, String user_id);
	
	//�������� ���(������) ����� ���嵵 ���� ������ ���岨�� ������̵� ���� �߰��ؼ� ���θ����
	public ArrayList<ApplyDto> Applylist();
	
	//�������� ���(����) �����ߵ�
	public ArrayList<ApplyDto> MyApplylist(String user_id);
	
	//�������� �󼼸��(���ǳ��� + ������ ȸ������) - (������, ����)
	public ArrayList<ApplyDetailDto> ApplyDetail(int asNo);
	
	//�������� ����ȭ������ �̵�(����)
	public ArrayList<ApplyDto> ApplyContent(int asNo);
	
	//�������� ����(����)
	public void ApplyModify(int asNo, String asName, String abNo, String asLat, String asLng, String asAd, String asPhone,
			String asCategory);
	
	//�������� ���� (����)
	public void ApplyDelete(int asNo);
	
	//=========================================================================
	
	//�޴����
	public String MenuRegister(String mName, String mImg, int mPrice, String mTitle1, String mTitle2, String mInfo, int sNo);
	
	//�޴� ����(���븸)
	public void MenuModify(int mNo, String mName, int mPrice, String mInfo);
	
	//�޴� �̹��� ����ȭ��
	public ArrayList<MenuDto> MImgModify(int mNo);
	
	//�޴� �̹��� ����
	public void BMImgModify(int mNo, String mImg);
	
	//��ǥ�޴� ����Ʈ
	public ArrayList<MenuDto> MTList(int sNo);
	
	//���θ޴� ����Ʈ
	public ArrayList<MenuDto> MMList(int sNo);
	
	//���̵� �޴� ����Ʈ
	public ArrayList<MenuDto> MSList(int sNo);
	
	//�޴� ����
	public void MenuDelete(int sNO);
	
	//=========================================================================
	
	//Today �ֹ�����(����)
	public ArrayList<OrderDto> MyStoreOrder(OrderDto dto);
	
	//Today �ֹ����º� ����Ʈ(����)
	public ArrayList<OrderDto> StatusOrderList(OrderDto dto);
	
	//�ֹ��󼼳���-�ּ�(����)
	public ArrayList<OrderDto> OrderDetail1(OrderDto dto);
	
	//�ֹ��󼼳���-�޴�(����)
	public ArrayList<OrderDetailDto> OrderDetail2(OrderDetailDto dto);
	
	//�ֹ����� �ֹ�����ó�� ����(����)
	public void oStatusModify(OrderDto dto);
	
	//�ֹ���ü����(����)
	public ArrayList<OrderDto> AllOrderList(OrderDto dto);
	
	//��ü�ֹ� ��¥��ȸ�� ���(����)
	public ArrayList<OrderDto> AllOrderCheck(String user_id, Date oDate, Date oDate1);
		
}