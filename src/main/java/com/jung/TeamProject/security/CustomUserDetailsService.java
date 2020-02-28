package com.jung.TeamProject.security;

import java.util.ArrayList;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jung.TeamProject.dao.BDaoTemplate;
import com.jung.TeamProject.dao.UserDao;
import com.jung.TeamProject.dto.UserDto;
import com.jung.TeamProject.util.Constant;

//spring security�� ����ϸ� Ŭ���̾�Ʈ���� ��û(login)�ϸ� �̰����� ��û��
public class CustomUserDetailsService implements UserDetailsService {
	
	@Override //security���� ȣ���ϴ� �޼���
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Ŀ���� ���� �Է°� : " + username); //(1)
		
		BDaoTemplate dao = Constant.dao;
		UserDto dto = dao.login(username);
		System.out.println("Ŀ���� ���� ��ȯ�� : " + dto); //(6)
		
		if(dto == null) {
			System.out.println("���̵� ����");
			throw  new UsernameNotFoundException("No user found with username"); //���̵� ������ ���⼭ �� ������������ �̵�(�����Բ� �����ϱ�)
		}		
		
		String pw = dto.getUser_pw();
		String user_code = dto.getUser_code();

		System.out.println("ȸ���ڵ� : " + user_code);
		
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		
		//���Ѻο�
		if(user_code.equals("CUSTOM")) {
			System.out.println("CUSTOM");
			roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		else if(user_code.equals("BOSS")) {
			System.out.println("BOSS");
			roles.add(new SimpleGrantedAuthority("ROLE_BOSS"));
			
		}
		else if(user_code.equals("ADMIN")) {
			System.out.println("ADMIN");
			roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		UserDetails user = new User(username, pw, roles);
		
		System.out.println("���̵� : " + username);
		System.out.println("�н����� : " + pw);
		System.out.println("���� : " + roles);

		return user;
	}
}