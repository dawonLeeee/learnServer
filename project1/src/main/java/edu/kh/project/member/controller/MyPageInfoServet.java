package edu.kh.project.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.kh.project.member.model.service.MemberService;
import edu.kh.project.member.model.vo.Member;


@WebServlet("/member/myPage/info")
public class MyPageInfoServet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/views/member/myPage-info.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//인코딩 필터로 문자인코딩 처리
		// ->filter사용으로 코드 필요없음
		
		//파라미터 얻어오기
		String memberNickname = req.getParameter("memberNickname");
		String memberTel = req.getParameter("memberTel");
		String[] arr = req.getParameterValues("memberAddress");
		
		String memberAddress = null;
		if(!arr[0].equals("") & !arr[1].equals("")) {
			memberAddress = String.join(",,", arr);
		}
		
//		** 로그인한 회원의 정보 얻어오기
		HttpSession session = req.getSession();
		
//		세션에 저장된 로그인 Member 객체의 참조주소를 복사(얕은복사)
//		-->loginMember는 세션에 저장된 객체!
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		int memberNo = loginMember.getMemberNo();
		
		
//		Member 객체를 생성해 업데이트에 필요한 값 셋팅
		Member member = new Member();
		member.setMemberNickname(memberNickname);
		member.setMemberTel(memberTel);
		member.setMemberAddress(memberAddress);
		member.setMemberNo(memberNo);
		
		try {
			
			int result = new MemberService().updateMember(member);
			
			
			if(result > 0) {
				session.setAttribute("message", "내 정보 수정 완료");
				
				//DB 데이터 수정 성공시
				//Session에 담긴 로그인 회원 정보도 같이 수정 필요(데이터 동기화)
				loginMember.setMemberNickname(memberNickname);
				loginMember.setMemberTel(memberTel);
				loginMember.setMemberAddress(memberAddress);
				
				
			} else {
				session.setAttribute("message", "내 정보 수정이 실패하였습니다");
			}
			
//			성공/실패 관계 없이 내 정보 페이지 재요청
			resp.sendRedirect("/member/myPage/info");
			
			
		} catch(Exception e) {
			e.printStackTrace();
			
			String errorMessage = "내 정보 수정 중 문제가 발생했습니다";
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("e", e);
			
			String path = "/WEB-INF/view/common/error.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
			
			
			/*
			 * 이 구문이 반복된다고 해서 따로 메서드를 빼는 것은 좋지 않다.
			 * 왜? req, resp같은 controller 객체는 controller를 벗어나는 것을 권장하지 않음
			 * */
		}
	}
}
