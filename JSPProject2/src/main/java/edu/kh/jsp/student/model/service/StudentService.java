package edu.kh.jsp.student.model.service;

//JDBCTemplate의 static field 메서드 호출시 클래스명 생략
import static edu.kh.jsp.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jsp.student.model.dao.StudentDAO;
import edu.kh.jsp.student.model.vo.Student;

public class StudentService {
	

	private StudentDAO dao = new StudentDAO();
	
	
	/** 학생 전체 조회 서비스
	 * @return stdList
	 * @throws Exception
	 */
	public List<Student> selectAll() throws Exception{

		Connection conn = getConnection();
		List<Student> stdList = dao.selectAll(conn);
		
		close(conn);
		
		return stdList;
	}


	/** 학과별 학생 조회 서비스
	 * @param deptName
	 * @return stdList
	 * @throws Exception
	 */
	public List<Student> selectByDept(String deptName) throws Exception{
		
		Connection conn = getConnection();
		List<Student> stdList = dao.selectByDept(conn, deptName);
		
		close(conn);
		
		return stdList;
	}

}
