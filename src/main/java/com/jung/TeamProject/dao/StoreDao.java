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

@Repository //DAO클래스를 나타내며  bean클래스로 사용 가능케 함
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
	private SqlSession sqlSession; //field로 autowired
	
	public StoreDao() {
		this.template = Constant.template;
		this.transactionManager = Constant.transactionManager;
	}
	
	//가게등록화면(회원아이디 첨부)
	@Override
	public ArrayList<ApplyDto> RegisterView(String user_id) {
		
		ArrayList<ApplyDto> result = (ArrayList) sqlSession.selectList("RegisterView", user_id);
		return result;
		
	}
	//가게등록
	@Override
	public void Register(String sName, String bName, String bNo, String sImg, String sAd, String sLat, String sLng,
								String sPhone, String sCategory, int sTip, int mPrice, String user_id) {
		
		StoreDto dto = new StoreDto(0, sName, bName, bNo, sImg, sAd, sLat, sLng, sPhone, sCategory, sTip, 
											mPrice, null, user_id, null);
		sqlSession.insert("Register", dto);
		
	}
	//가게등록하면 입점처리상태가 처리완료로 바꾸기
	@Override
	public void ApplyStatusEnd(int asNo) {
		
		ApplyDto dto = new ApplyDto();
		dto.setAsNo(asNo);
		sqlSession.insert("ApplyStatusEnd", dto);
		
	}
	
	//전체 가게 리스트(관리자)
	@Override 
	public ArrayList<StoreDto> Storelist() {		
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("Storelist");
		System.out.println("DB 값 : " + result);
		return result;
	}
	
	//내 가게 리스트(사장)
	@Override
	public ArrayList<StoreDto> MyStorelist(String user_id) {
		StoreDto dto = new StoreDto();
		dto.setUser_id(user_id);
		
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("MyStorelist", dto);
		System.out.println("DB 값 : " + result);
		return result;
	}
	
	//가게 상세리스트
	@Override
	public ArrayList<StoreDto> StoreDetail(int sNo) {
		System.out.println("StoreDao 들어옴");
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("StoreDetail", sNo);
		System.out.println("가게 상세리스트 DB 값 : " + result);
		return result;
	}
	
	//가게 수정 화면(관리자)
	@Override
	public ArrayList<StoreDto> RegisterContent(int sNo) {
		System.out.println("StoreDao들어옴");
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("RegisterContent", sNo);
		System.out.println("DB 값 : " + result);
		return result;
	}
	
	//가게내용수정(관리자)
	@Override
	public void StoreModify(int sNo, String sName, String bName, String bNo, String sAd, String sLat,
			String sLng, String sPhone, String sCategory, int sTip, int mPrice) {
		
		System.out.println("수정dao들어옴");
		
		StoreDto dto = new StoreDto(sNo, sName, bName, bNo, null, sAd, sLat, sLng, sPhone, sCategory, sTip, mPrice, null, null, null);
		
		sqlSession.insert("StoreModify", dto);
		
		System.out.println("dao 성공이면 1 : " + sqlSession.insert("StoreModify", dto));
		
	}
	
	//가게수정(사장)
	@Override
	public void BSModify(int sNo, int sTip, int mPrice) {
		
		System.out.println("BSModify dao들어옴");

		StoreDto dto = new StoreDto();
		dto.setsNo(sNo);
		dto.setsTip(sTip);
		dto.setmPrice(mPrice);
		
		sqlSession.insert("BSModify", dto);
		
	}
	
	//가게 이미지 수정(관리자)
	@Override
	public void ImgModify(int sNo, String sImg) {
		
		System.out.println("수정dao들어옴");
		StoreDto dto = new StoreDto();
		dto.setsNo(sNo);
		dto.setsImg(sImg);
		sqlSession.insert("ImgModify", dto);
		
	}
	
	//가게 삭제
	@Override
	public void StoreDelete(int sNo) {
		
		sqlSession.delete("StoreDelete",sNo);
		System.out.println("입점문의 삭제 결과 : " + sqlSession.delete("StoreDelete", sNo));
		
	}
	
	//가게칮기(카테고리)
	@Override
	public ArrayList<StoreDto> Category(String sLat, String sLng, String sCategory) {
		
		System.out.println("카테고리 가게찾기");
		
		StoreDto dto = new StoreDto();
		dto.setsLat(sLat);
		dto.setsLng(sLng);
		dto.setsCategory(sCategory);
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("Category", dto);
		System.out.println("DB 값 : " + result);
		
		return result;
		
	}
	
	
	//가게찾기 AdSearch()이름과 selectList(이름)하고 mapper id하고 3가지가 모두 같아야 한다. --> 메인 + 전체보기
	@Override
	public ArrayList<StoreDto> AdSearch(String sLat, String sLng) {
		
		System.out.println("가게찾기");
		
		StoreDto dto = new StoreDto();
		dto.setsLat(sLat);
		dto.setsLng(sLng);
		ArrayList<StoreDto> result = (ArrayList) sqlSession.selectList("AdSearch", dto);
		System.out.println("DB 값 : " + result);
		
		return result;
		
	}

	//=============================================================================================
	
	//입점문의 등록(사장)
	@Override
	public String Apply(String asName, String abNo, String asLat, String asLng, String asAd, String asPhone,
			String asCategory, String user_id) {
		
		ApplyDto dto = new ApplyDto(0, asName, abNo, asLat, asLng, asAd, asPhone, asCategory, null, null, user_id);
		
		int result = sqlSession.insert("Apply", dto);
		
		if(result > 0)	{ //1이면 가입성공
			System.out.println("Apply db등록 성공");
			return "Apply-success";
		}
		else {
			System.out.println("Apply db등록 실패");
			return "Apply-failed";
		}
		
	}
	
	//입점문의 리스트(관리자)
	@Override 
	public ArrayList<ApplyDto> Applylist() {
		
		ArrayList<ApplyDto> result = (ArrayList) sqlSession.selectList("Applylist");
		System.out.println("DB 값 : " + result);
		
		return result;
	}
	
	//입점문의 리스트(사장꺼만 보이게)
	@Override
	public ArrayList<ApplyDto> MyApplylist(String user_id) {
		
		ApplyDto dto = new ApplyDto();
		dto.setUser_id(user_id);
		
		ArrayList<ApplyDto> result = (ArrayList) sqlSession.selectList("MyApplylist", dto);
		System.out.println("DB 값 : " + result);
		return result;
	}
		
	//입점문의 상세 리스트(사장,관리자)
	@Override
	public ArrayList<ApplyDetailDto> ApplyDetail(int asNo) {
		System.out.println("StoreDao 들어옴");
		ArrayList<ApplyDetailDto> result = (ArrayList) sqlSession.selectList("ApplyDetail", asNo);
		System.out.println("상세리스트 DB 값 : " + result);
		return result;
	}
	
	//입점문의 수정화면(사장)
	@Override
	public ArrayList<ApplyDto> ApplyContent(int asNo) {
		System.out.println("StoreDao들어옴");
		ArrayList<ApplyDto> result = (ArrayList) sqlSession.selectList("ApplyContent", asNo);
		System.out.println("DB 값 : " + result);
		return result;
		
	}
	
	//입점문의 수정처리(사장)
	@Override
	public void ApplyModify(int asNo, String asName, String abNo, String asLat, String asLng, String asAd, String asPhone,
			String asCategory) {
		
		ApplyDto dto = new ApplyDto(asNo, asName, abNo, asLat, asLng, asAd, asPhone, asCategory, null, null ,null);
		
		sqlSession.insert("ApplyModify", dto);
		System.out.println("apply db삽입 성공");
		
	}
	
	//입점문의 삭제(사장)
	@Override
	public void ApplyDelete(int asNo) {
		
		sqlSession.delete("ApplyDelete",asNo);
		System.out.println("입점문의 삭제 결과 : " + sqlSession.delete("ApplyDelete", asNo));
		
	}
	
	//=============================================================================================
	
	//메뉴 등록
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
	
	//메뉴 수정(내용만)
	@Override
	public void MenuModify(int mNo, String mName, int mPrice, String mInfo) {
		
		System.out.println("MenuModify dao들어옴");
		
		MenuDto dto = new MenuDto();
		dto.setmNo(mNo);
		dto.setmName(mName);
		dto.setmPrice(mPrice);
		dto.setmInfo(mInfo);
		
		sqlSession.insert("MenuModify", dto);
		
	}
	
	//메뉴이미지 수정화면(모달)
	@Override
	public ArrayList<MenuDto> MImgModify(int mNo) {
		System.out.println("MImgModify Dao들어옴");
		MenuDto dto = new MenuDto();
		dto.setmNo(mNo);
		ArrayList<MenuDto> result = (ArrayList) sqlSession.selectList("MImgModify", dto);
		System.out.println("DB 값 : " + result);
		return result;	
	}
	
	//메뉴이미지 수정
	@Override
	public void BMImgModify(int mNo, String mImg) {
		System.out.println("수정dao들어옴");
		MenuDto dto = new MenuDto();
		dto.setmNo(mNo);
		dto.setmImg(mImg);
		sqlSession.insert("BMImgModify", dto);
	}
	
	
	//대표메뉴 리스트
	@Override
	public ArrayList<MenuDto> MTList(int sNo) {
		System.out.println("MTListDao들어옴");
		MenuDto dto = new MenuDto();
		dto.setsNo(sNo);
		ArrayList<MenuDto> result = (ArrayList) sqlSession.selectList("MTList", dto);
		System.out.println("DB 값 : " + result);
		return result;	
	}
	
	//메인메뉴 리스트
	@Override
	public ArrayList<MenuDto> MMList(int sNo) {
		System.out.println("MMListDao들어옴");
		MenuDto dto = new MenuDto();
		dto.setsNo(sNo);
		ArrayList<MenuDto> result = (ArrayList) sqlSession.selectList("MMList", dto);
		System.out.println("DB 값 : " + result);
		return result;	
	}
	
	//사이드메뉴 리스트
	@Override
	public ArrayList<MenuDto> MSList(int sNo) {
		System.out.println("MSListDao들어옴");
		MenuDto dto = new MenuDto();
		dto.setsNo(sNo);
		ArrayList<MenuDto> result = (ArrayList) sqlSession.selectList("MSList", dto);
		System.out.println("DB 값 : " + result);
		return result;	
	}
	
	//메뉴삭제
	@Override
	public void MenuDelete(int sNo) {		
		sqlSession.delete("MenuDelete",sNo);
		System.out.println("입점문의 삭제 결과 : " + sqlSession.delete("MenuDelete", sNo));
	}
	
	//=========================================================================
	
	//Today 주문내역(사장)
	@Override
	public ArrayList<OrderDto> MyStoreOrder(OrderDto dto) {
		
		System.out.println("내가게 주문목록 dao 들어옴");
		
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("MyStoreOrder", dto);
		
		return result;
	}
	
	//Today 주문상태별 리스트(사장)
	@Override
	public ArrayList<OrderDto> StatusOrderList(OrderDto dto) {
		
		System.out.println("내가게 주문상태별 목록 dao 들어옴");
		
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("StatusOrderList", dto);
		
		return result;
		
	}

	//주문상세내역 - 주소(사장)
	@Override
	public ArrayList<OrderDto> OrderDetail1(OrderDto dto) {
		System.out.println("내가게 주문상세내역 목록 dao 들어옴");
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("OrderDetail1", dto);
		return result;
	}
	
	//주문상세내역 - 메뉴(사장)
	@Override
	public ArrayList<OrderDetailDto> OrderDetail2(OrderDetailDto dto) {
		System.out.println("내가게 주문상세내역 목록 dao 들어옴");
		ArrayList<OrderDetailDto> result = (ArrayList) sqlSession.selectList("OrderDetail2", dto);
		return result;
	}
	
	//주문내역 주문상태처리 수정(사장)
	@Override
	public void oStatusModify(OrderDto dto) {
		sqlSession.insert("oStatusModify", dto);	
	}
	
	//주문전체내역(사장)
	@Override
	public ArrayList<OrderDto> AllOrderList(OrderDto dto) {
		ArrayList<OrderDto> result = (ArrayList) sqlSession.selectList("AllOrderList", dto);
		return result;
	}
	//전체주문 날짜조회한 목록(사장)
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