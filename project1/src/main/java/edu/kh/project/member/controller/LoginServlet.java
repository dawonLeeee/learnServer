package edu.kh.project.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.kh.project.member.model.service.MemberService;
import edu.kh.project.member.model.vo.Member;

@WebServlet("/member/login")
public class LoginServlet extends HttpServlet{

//	login페이지로 응답(forward)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		login.jsp로 요청을 발송할 requestDispatcher를 얻어옴->바로 전달
		req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		MemberService service = new MemberService();
		
		try {
			
			String inputEmail = req.getParameter("inputEmail");
			String inputPw = req.getParameter("inputPw");
			
//			member객체에 파라미터 저장
			Member member = new Member();
			member.setMemberEmail(inputEmail);
			member.setMemberPw(inputPw);
			
			Member loginMember = service.login(member);
			
			

			//forward를 하는 경우(적절하지 못한 예시)
//				- 요청을 다른 Servlet/JSP로 위임
	//				-> 어떤 요청이 위임됐는지를 알아야 되기 때문에
	//					주소창에 요청주소가 계속 남아있다
//			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/common/main.jsp");
//			req.setAttribute("loginMember", loginMember);
//			dispatcher.forward(req, resp);
			
//			*** redirect(재요청) ***
//			- servlet이 다시 다른 주소를 요청함(이건 제 일이 아니니깐 다른 사람한테 넘겨드릴게요)
//				cf) 카페 -> 커피주문 -> 캐셔 -> 바리스타(forward)
//				cf) 카페 -> 김밥주문 -> 캐셔 -> 옆집으로 가세요(redirect)
//			- 요청에 대한 응답 화면을 직접 만드는 것이 아닌 다른 응답화면을 구현하는 Servlet을 요청하여
//				대신 화면을 만들게 하는 것
			
			// 메인페이지로 redirect
//				->메인 페이지를 요청한 것이기 때문에 주소창에 주소가 메인페이지 주소로 변함
			// rquest scope에 속성을 추가해도 rediredct를 하는 경우 request가 다시 만들어져 유지되지 않음
			req.setAttribute("loginMember", loginMember);
			
			
			
			// 해결방법 : Session scope 이용
			
			// 1) HttpSession 객체 얻어오기
			HttpSession session = req.getSession();
			
			String path = null; // 로그인 성공/실패에 따라 이동할 경로를 저장할 변수
			
//			로그인 성공시
			if(loginMember != null) {
				
				path = "/"; // 메인페이지
				
				
				// 2) Session scope에 속성 추가하기
				session.setAttribute("loginMember", loginMember);
				// 3) session 만료시간 설정(초단위 지정)
				// (클라이언트가 새로운 요청을 할 때마다 초기화)
				// 이걸 지정 안하면 세션 무제한
				session.setMaxInactiveInterval(60*60); // 3600초==1시간
				
				
//				---------------------------------------------------
				// 아이디 저장(Cookie)
				
				/* Cookie란:
				 * - 클라이언트 측(브라우저)에서 관리하는 파일
				 * - 쿠키파일에 등록된 주소 요청시마다 자동으로 요청에 첨부되어 서버로 전달됨
				 * - 서버로 전달된 쿠키에 값 추가, 수정, 삭제 등을 진행한 후 다시 클라이언트에게 반환
				 * */
				
				// 1) 아이디 저장 체크박스가 체크되었는지 확인
				// 2) 쿠키 객체 생성
				// 	- 생성된 쿠키 객체를 resp를 이용해 클라이언트에게 전달하면 
				// 		클라이언트 컴퓨터에 파일 형태로 저장됨
				Cookie cookie = new Cookie("saveId", inputEmail);
				if(req.getParameter("saveId") != null) { // 체크된 경우
					
					// 3) 쿠키가 유지될 수 있는 유효기간 설정
					cookie.setMaxAge(60*60*24*30); // 30일
					
				} else { // 체크되지 않은 경우
					
					// 4) 쿠키의 유효기간을 0초로 설정
					// == 클라이언트에 저장된 saveId 쿠키를 삭제하는 코드
					// (같은 key)값의 쿠키가 저장되면 덮어쓰기가 일어남)
					cookie.setMaxAge(0);
				}
				
				// 5) 생성된 쿠키가 적용되어질 요청 주소를 지정
				cookie.setPath("/"); // 메인페이지 주소(http://localhost/)
									// == 메인페이지의 하위 주소 모두 적용
				
				// 6) 설정 완료된 쿠키 객체를 클라이언트에게 전달
				resp.addCookie(cookie);
				
				
				
//				---------------------------------------------------
				
			} else { // 로그인 실패시
				
				//현재 요청 이전의 페이지 주소
				path = req.getHeader("referer");
				
//				request에 저장하면 세션 만료되면 req 다 날아가서 session에 저장.
				session.setAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
			}
			
			
			// 로그인 성공시) path가 "/"인 경우 메인페이지로 리다이렉트(메인페이지 재요청)
			resp.sendRedirect(path);
			/*
			 * 	forward <-> redirect 차이점
			 * 				
			 * 	forward
			 * - 주소 변화 x
			 * - JSP 경로 작성
			 * - req, resp가 유지된다
			 * 
			 * 	redirect
			 * - 주소창 변화 o (/member/login -> /)
			 * - 요청주소 작성
			 * - req, resp가 유지되지 않는다
			 * 
			 * */			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

	}
}
