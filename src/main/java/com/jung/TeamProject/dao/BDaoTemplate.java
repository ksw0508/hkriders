package com.jung.TeamProject.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import com.jung.TeamProject.dto.UserDto;
import com.jung.TeamProject.util.Constant;

@Repository
public class BDaoTemplate {
	
	JdbcTemplate template;
	SqlSession sqlSession;
	
	@Autowired  //servlet-context.xml bean bound
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	   	   
	PlatformTransactionManager transactionManager;   
	
	@Autowired 
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
	   this.transactionManager = transactionManager;
	}
	
	public BDaoTemplate() {
	   this.template = Constant.template;
	   this.transactionManager = Constant.transactionManager;      
	}
	
	public UserDto login(String username) {
		
		System.out.println("dao �α��� �Է°� : " + username); //(2)
		String sql = "SELECT USER_ID, USER_PW, USER_CODE FROM USER_INFO WHERE USER_ID='"+ username +"'";
		System.out.println(sql);
		UserDto uId = null;
		System.out.println("���� �۵� �� : " + uId); //(3)
		
		try {
			
			return template.queryForObject(sql, new BeanPropertyRowMapper<UserDto>(UserDto.class));
		
		} 
		catch (EmptyResultDataAccessException e) {

			return uId; //���̵� ���°�� null
			
		}
		
	}
	
	public String findId(UserDto dto) {
		String resultId;
		
		System.out.println("dao������ ���۰� : " + dto.getUser_email());
		String uEmail = dto.getUser_email();
				
		String sql = "SELECT USER_ID FROM USER_INFO WHERE USER_EMAIL = '"+ uEmail +"'";
		System.out.println(sql);
		
		dto = template.queryForObject(sql, new BeanPropertyRowMapper<UserDto>(UserDto.class));
		resultId = dto.getUser_id();
		System.out.println("id ��°� : " + resultId);
		
		return resultId;
	}
	
	public int findPw(UserDto dto) {
		
		String id = dto.getUser_id();
		String email = dto.getUser_email();
		System.out.println("dao������ id / email : " + id + "/" + email);
		
		String sql = "SELECT COUNT(USER_PW) FROM USER_INFO WHERE USER_ID = '"+ id +"'AND USER_EMAIL = '"+ email +"'";
		System.out.println(sql);
		int result = template.queryForObject(sql, Integer.class);
				
		System.out.println("findPw ����� : " + result);
		
		return result;
		
	}
	
	public void send_mail(UserDto dto, String div, String pw) throws Exception {
		// Mail Server ����
		String charSet = "utf-8";
		String hostSMTP = "smtp.gmail.com";
		String hostSMTPid = "rmb0058";
		String hostSMTPpwd = "vpdlxm58sw";

		// ������ ��� EMail, ����, ����
		String fromEmail = "admin@hkriders.co.kr";
		String fromName = "������";
		String subject = "";
		String msg = "";

		// ȸ������ ���� ����
		subject = "HK���̴��� �ӽ� ��й�ȣ �Դϴ�.";
		msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		msg += "<h3 style='color: blue;'>";
		msg += dto.getUser_id() + "���� �ӽ� ��й�ȣ �Դϴ�. ��й�ȣ�� �����Ͽ� ����ϼ���.</h3>";
		msg += "<p>�ӽ� ��й�ȣ : ";
		msg += pw + "</p></div>";

		// �޴� ��� E-Mail �ּ�
		String mail = dto.getUser_email();
		String id = dto.getUser_id();
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(587);

			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			email.setTLS(true);
			email.addTo(mail, id);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			System.out.println("���Ϲ߼� ���� : " + e);
		}
	}
	
	public ArrayList<UserDto> modi1(UserDto dto) {
		
		ArrayList<UserDto> dtos = new ArrayList<UserDto>();
		String uId = dto.getUser_id();
		
		System.out.println("modi1 �Է� id�� : " + uId);
				
		String sql = "SELECT USER_NAME, USER_SEX, USER_EMAIL, USER_PHONE, USER_PROFILE FROM USER_INFO WHERE USER_ID = '"+ uId +"'";
		System.out.println(sql);
		
		dto = template.queryForObject(sql, new BeanPropertyRowMapper<UserDto>(UserDto.class));
		dtos.add(dto);
		return dtos;
	}
	
}
