package com.jung.TeamProject.dto;

import java.sql.Timestamp;

//��������Dto(���Ե��,��ϸ���Ʈ-������, ã�����Ը���Ʈ + ��ī�װ��� ����Ʈ - ��(����������), �����Ը���Ʈ - ���� )
//�󼼸���Ʈ�� ���� ������ �߱⶧���� ���� �ȸ���
public class StoreDto {
	
	private int sNo; //��ü��ȣ
	private String sName; //��ü��
	private String bName; //���ָ�
	private String bNo; //����ڹ�ȣ
	private String sImg; //�����̹���
	private String sAd; //�����ѱ��ּ�
	private String sLat; //����
	private String sLng; //�浵
	private String sPhone; //������ȭ��ȣ
	private String sCategory; //ī�װ�
	private int sTip; //�����
	private int mPrice; //�ּ��ֹ��ݾ�
	private Timestamp sDate; //�����
	private String user_id; //ȸ�����̵�(FK)
	
	
	
	private String dImg; //���� �̹��� �����ϱ� ���ؼ� �ʿ�
	
	public StoreDto() {
		super();
	}

	public StoreDto(int sNo, String sName, String bName, String bNo, String sImg, String sAd, String sLat, String sLng,
			String sPhone, String sCategory, int sTip, int mPrice, Timestamp sDate, String user_id, String dImg) {
		super();
		this.sNo = sNo;
		this.sName = sName;
		this.bName = bName;
		this.bNo = bNo;
		this.sImg = sImg;
		this.sAd = sAd;
		this.sLat = sLat;
		this.sLng = sLng;
		this.sPhone = sPhone;
		this.sCategory = sCategory;
		this.sTip = sTip;
		this.mPrice = mPrice;
		this.sDate = sDate;
		this.user_id = user_id;
		
		this.dImg = dImg;
	
	}

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbNo() {
		return bNo;
	}

	public void setbNo(String bNo) {
		this.bNo = bNo;
	}

	public String getsImg() {
		return sImg;
	}

	public void setsImg(String sImg) {
		this.sImg = sImg;
	}

	public String getsAd() {
		return sAd;
	}

	public void setsAd(String sAd) {
		this.sAd = sAd;
	}

	public String getsLat() {
		return sLat;
	}

	public void setsLat(String sLat) {
		this.sLat = sLat;
	}

	public String getsLng() {
		return sLng;
	}

	public void setsLng(String sLng) {
		this.sLng = sLng;
	}

	public String getsPhone() {
		return sPhone;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public String getsCategory() {
		return sCategory;
	}

	public void setsCategory(String sCategory) {
		this.sCategory = sCategory;
	}

	public int getsTip() {
		return sTip;
	}

	public void setsTip(int sTip) {
		this.sTip = sTip;
	}

	public int getmPrice() {
		return mPrice;
	}

	public void setmPrice(int mPrice) {
		this.mPrice = mPrice;
	}

	public Timestamp getsDate() {
		return sDate;
	}

	public void setsDate(Timestamp sDate) {
		this.sDate = sDate;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
	
	
	
	
	
	

	public String getdImg() {
		return dImg;
	}

	public void setdImg(String dImg) {
		this.dImg = dImg;
	}
	
	
		
}