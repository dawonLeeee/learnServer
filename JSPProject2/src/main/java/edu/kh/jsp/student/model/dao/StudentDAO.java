package edu.kh.jsp.student.model.dao;

import static edu.kh.jsp.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jsp.common.JDBCTemplate;
import edu.kh.jsp.student.model.vo.Student;

public class StudentDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	
	public StudentDAO() {
		
		try {
			
			String filePath = 
					StudentDAO.class.getResource("/edu/kh/jsp/sql/student-sql.xml").getPath();
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/** 학생 전체 조회 DAO
	 * @param conn
	 * @return stdList
	 * @throws Exception
	 */
	public List<Student> selectAll(Connection conn) throws Exception{
		
		List<Student> stdList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectAll");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				stdList.add(new Student(
						rs.getString("STUDENT_NO"),
						rs.getString("STUDENT_NAME"),
						rs.getString("STUDENT_ADDRESS"),
						rs.getString("DEPARTMENT_NAME")
						));
			}
			
		} finally {
			close(rs);
			close(stmt);
			
		}
		
		
		return stdList;
	}




	/** 학과별 학생 조회 서비스
	 * @param conn
	 * @param deptName
	 * @return stdList
	 */
	public List<Student> selectByDept(Connection conn, String deptName) throws Exception{
		
		List<Student> stdList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectByDept");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptName);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				stdList.add(new Student(
						rs.getString("STUDENT_NO"),
						rs.getString("STUDENT_NAME"),
						rs.getString("STUDENT_ADDRESS"),
						rs.getString("DEPARTMENT_NAME")
						));
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return stdList;
	}
}
