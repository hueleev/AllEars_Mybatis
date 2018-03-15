package guestMsg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;










public class GuestDBBean {
	private static GuestDBBean instance = new GuestDBBean();
	private GuestDBBean () {
		
	}
	public static GuestDBBean getInstance() {
		return instance;
	}
	
	
	public static Connection getConnection(){
		Connection con = null;
		try {
			String jdbcUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
			String dbId = "scott";
			String dbPass = "tiger";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(jdbcUrl,dbId,dbPass);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public void close(Connection con,ResultSet rs, PreparedStatement pstmt) {
			if (rs!=null)
				try {
					rs.close();
				}catch(SQLException ex) { }
		if (pstmt !=null) try {pstmt.close();
		}catch(SQLException ex) {}
		if(con != null)
			try {
				con.close();
			}catch(SQLException ex) {
		}
		
	}
	
	public void insertMsg(GuestDataBean msg) {
		String sql="";
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		try {
			pstmt
			=con.prepareStatement("select boardser.nextval "
						+ "from dual");
			rs = pstmt.executeQuery();
			if (rs.next())
				number = rs.getInt(1)+1;
			else
				number = 1;
			
			int gnum = msg.getGnum();
			int ref = msg.getRef();
			int re_step = msg.getRe_step();
			int re_level = msg.getRe_level();
			if(gnum!=0)
			{sql = "update guestmsg set re_step=re_step+1 where ref= ? and re_step>? and gboardid=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_step);
			pstmt.setString(3, msg.getGboardid());
			pstmt.executeUpdate();
			re_step = re_step + 1;
			re_level = re_level + 1;
			} else {
				ref = number; re_step=0; re_level =0;
			}
			sql = "insert into guestmsg(gnum,writer,gtitle,"
					+" gcontent,greg_date,";
			sql+="ref,re_step,re_level,gboardid,gemail) "
					+ "values(?,?,?,?,sysdate,?,?,?,?,?)";

		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, number);
		pstmt.setString(2, msg.getWriter());
		pstmt.setString(3, msg.getGtitle());
		pstmt.setString(4, msg.getGcontent());
		pstmt.setInt(5, ref);
		pstmt.setInt(6, re_step);
		pstmt.setInt(7, re_level);
		pstmt.setString(8, msg.getGboardid());
		pstmt.setString(9, msg.getGemail());
		pstmt.executeUpdate();
		
		}catch(SQLException e1) {
			e1.printStackTrace();
		}finally {
			close(con,rs,pstmt);
		}
		
	
	}
	
	public int getMsgCount(String gboardid) {
		int x=0;
		String sql="select nvl(count(*),0) from guestmsg where gboardid =?";
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, gboardid);
		
		rs=pstmt.executeQuery();
		if(rs.next()) {x=rs.getInt(1);}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con,rs,pstmt);
		}
		return x;
		
	}
	
	public List getMsgs(int startRow, int endRow,String gboardid) {

		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List msgList = null;
		String sql = "";
		
		try { 		
			conn=getConnection();
			sql = " select * from "
					+ "( select rownum rnum,a.* "
					+ "from (select gnum,writer,gemail,gtitle,gcontent,"
					+ "greg_date,ref,re_step,re_level "
					+ "from guestmsg where gboardid = ? order by ref desc, re_step) "
					+ " a ) where rnum between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, gboardid);
			pstmt.setInt(2,startRow);
			pstmt.setInt(3, endRow);;
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				msgList = new ArrayList();
				do {
					GuestDataBean msg = new GuestDataBean();
					msg.setGnum(rs.getInt("gnum"));
					msg.setWriter(rs.getString("writer"));
					msg.setGemail(rs.getString("gemail"));
					msg.setGtitle(rs.getString("gtitle"));
					msg.setGcontent(rs.getString("gcontent"));
					msg.setGreg_date(rs.getTimestamp("greg_date"));
					msg.setRef(rs.getInt("ref"));
					msg.setRe_step(rs.getInt("re_step"));
					msg.setRe_level(rs.getInt("re_level"));
					msgList.add(msg);
				} while (rs.next());
				
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {close(conn,rs,pstmt);}
		
		return msgList;
	}
	
	public GuestDataBean getMsg(int gnum,String gboardid,String chk) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GuestDataBean msg = null;
		String sql="";
		try {
			conn = getConnection();
			
	
			
			sql="select * from guestmsg where gnum = ? and gboardid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gnum);
			pstmt.setString(2, gboardid);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				msg = new GuestDataBean();
				msg.setGnum(rs.getInt("gnum"));
				msg.setGboardid(rs.getString("gboardid"));
				msg.setGtitle(rs.getString("gtitle"));
				msg.setGemail(rs.getString("gemail"));
				msg.setGcontent(rs.getString("gcontent"));
				msg.setGreg_date(rs.getTimestamp("greg_date"));
				msg.setRef(rs.getInt("ref"));
				msg.setRe_step(rs.getInt("re_step"));
				msg.setRe_level(rs.getInt("re_level"));
				msg.setWriter(rs.getString("writer"));

			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
		}
		return msg;
		
	}
	
	public int deleteMsg(int gnum,String gboardid) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "delete from guestmsg where gnum=?";
		int x= -1;
		try {conn = getConnection();
		pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, gnum);
		x=pstmt.executeUpdate();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
		}return x;
	}
	
	public int updateMsg(GuestDataBean msg) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int chk=0;
		try {
		conn = getConnection();
		String sql="update guestmsg set gtitle=?,gemail=?,gcontent=? where gnum=?";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, msg.getGtitle());
		pstmt.setString(2, msg.getGemail());
		pstmt.setString(3, msg.getGcontent());
		pstmt.setInt(4, msg.getGnum());

		chk=pstmt.executeUpdate(); //업데이트 확인
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,null,pstmt);
		}
		
		return chk;
		
	}
	
}
