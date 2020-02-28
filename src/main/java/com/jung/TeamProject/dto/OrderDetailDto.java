package com.jung.TeamProject.dto;

public class OrderDetailDto {
	
	private int odNo; //�ֹ��󼼹�ȣ
	private int omNo; //�޴��ڵ�
	private String omName; //�޴��̸�
	private int omStock; //����
	private int omPrice; //����
	private String oNo; //�ֹ���ȣ
	
	public OrderDetailDto() {
		super();
	}

	public OrderDetailDto(int odNo, int omNo, String omName, int omStock, int omPrice, String oNo) {
		super();
		this.odNo = odNo;
		this.omNo = omNo;
		this.omName = omName;
		this.omStock = omStock;
		this.omPrice = omPrice;
		this.oNo = oNo;
	}

	public int getOdNo() {
		return odNo;
	}

	public void setOdNo(int odNo) {
		this.odNo = odNo;
	}

	public int getOmNo() {
		return omNo;
	}

	public void setOmNo(int omNo) {
		this.omNo = omNo;
	}

	public String getOmName() {
		return omName;
	}

	public void setOmName(String omName) {
		this.omName = omName;
	}

	public int getOmStock() {
		return omStock;
	}

	public void setOmStock(int omStock) {
		this.omStock = omStock;
	}

	public int getOmPrice() {
		return omPrice;
	}

	public void setOmPrice(int omPrice) {
		this.omPrice = omPrice;
	}

	public String getoNo() {
		return oNo;
	}

	public void setoNo(String oNo) {
		this.oNo = oNo;
	}

}