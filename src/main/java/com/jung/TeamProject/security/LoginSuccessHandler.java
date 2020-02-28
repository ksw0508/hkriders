package com.jung.TeamProject.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.jung.TeamProject.dao.BDaoTemplate;
import com.jung.TeamProject.dto.UserDto;
import com.jung.TeamProject.util.Constant;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		String username = request.getParameter("user_id");
		System.out.println("username : " + username);	
		
		BDaoTemplate dao = Constant.dao;
		UserDto dto = dao.login(username);
		
		String authlist = dto.getUser_code();
		
		String url = "home"; //�⺻ �α��� ������ �̵��ϴ� ������
		
		System.out.println("ȸ�������� : " + authlist);
		
		if(authlist.equals("BOSS")) {
			System.out.println("����� �α��� ������������ �̵�");
			url = "BossHome";
		}
		if(authlist.equals("ADMIN")) {
			System.out.println("����� �α��� ������������ �̵�");
			url = "ManagerHome";
		}
		
		response.sendRedirect(url);
		
	}

}
