package Userlist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserlistDBBean {
	private static UserlistDBBean instance = new UserlistDBBean();
	private UserlistDBBean () {
		
	}
	
	public static UserlistDBBean getInstance() {
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
	
	
	public void insertUser(UserlistDataBean user) {
		String sql="";
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
			pstmt
			=con.prepareStatement("select boardser1.nextval from dual");
			rs=pstmt.executeQuery();
			
			if (rs.next())
				number = rs.getInt(1)+1;
			else
				number =1;
			
			int num = user.getNum();
			
			
			sql = "insert into userlist(num,userid,passwd,username,displayname,position,gender,hp,address,email,reg_date,bio) values(?,?,?,?,?,?,?,?,?,?,sysdate,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, user.getUserid());
			pstmt.setString(3, user.getPasswd());
			pstmt.setString(4, user.getUsername());
			pstmt.setString(5, user.getDisplayname());
			pstmt.setString(6, user.getPosition());
			pstmt.setString(7, user.getGender());
			pstmt.setString(8, user.getHp());
			pstmt.setString(9, user.getAddress());
			pstmt.setString(10, user.getEmail());
			pstmt.setString(11, user.getBio());
			pstmt.executeUpdate();

			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}finally {
			close(con,rs,pstmt);
		}
		
	}
	
	public int login(String userid, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select passwd FROM userlist WHERE userid=?";
		
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			System.out.println("id:"+userid);
			System.out.println("pass:"+passwd);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(passwd)) {
					return 1; //로그인 성공
				} else {
					System.out.println("비밀번호가 틀립니다.");
					return 0;
				}
			}
			System.out.println("가입되지 않은 아이디입니다.");
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //�뜲�씠�꽣踰좎씠�뒪�삤瑜�!
	}
	
	public boolean confirmId(String userid) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			 conn = getConnection();
			String sql = "select userid from userlist where userid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		
		return result;
	}
	

	

	
	public int getUserCount() {
		int x=0;
		String sql="select nvl(count(*),0) from userlist";
		Connection con=getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number =0;
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {x=rs.getInt(1);}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(con,rs,pstmt);
		}
		
		return x;
	}
	
	public List getUsers(int startRow,int endRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List userList = null;
		String sql = "";
		
		try {
			conn=getConnection();
			sql = "select * from "
					+ "( select rownum rnum,a.* "
					+ "from (select num,userid,passwd,username,displayname,"
					+ "position,gender,hp,address,email,reg_date,bio from userlist order by num desc) "
					+ " a ) where rnum between ? and ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				userList = new ArrayList();
				do {
					UserlistDataBean user = new UserlistDataBean();
					user.setNum(rs.getInt("num"));
					user.setUserid(rs.getString("userid"));
					user.setPasswd(rs.getString("passwd"));
					user.setUsername(rs.getString("username"));
					user.setDisplayname(rs.getString("displayname"));
					user.setPosition(rs.getString("position"));
					user.setGender(rs.getString("gender"));
					user.setHp(rs.getString("hp"));
					user.setAddress(rs.getString("address"));
					user.setEmail(rs.getString("email"));
					user.setReg_date(rs.getTimestamp("reg_date"));
					user.setBio(rs.getString("bio"));
					userList.add(user);
				}while (rs.next());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {close(conn,rs,pstmt);}
		
		return userList;
	}
	
	public UserlistDataBean getUser(int num,String chk) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserlistDataBean user = null;
		String sql="";
		try {
			conn = getConnection();
			
			sql = "select * from userlist where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				user = new UserlistDataBean();
				user.setNum(rs.getInt("num"));
				user.setUserid(rs.getString("userid"));
				user.setUsername(rs.getString("username"));
				user.setDisplayname(rs.getString("displayname"));
				user.setPosition(rs.getString("position"));
				user.setGender(rs.getString("gender"));
				user.setHp(rs.getString("hp"));
				user.setAddress(rs.getString("address"));
				user.setEmail(rs.getString("email"));
				user.setReg_date(rs.getTimestamp("reg_date"));
				user.setBio(rs.getString("bio"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
		}
		return user;
	}
	
	public int deleteUser(String userid,String passwd) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2=null;
		ResultSet rs = null;
		String sql = "delete from userlist where userid=? and passwd=?";
				
		
		int x=-1;
		
		try {conn = getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, passwd);
			x=pstmt.executeUpdate();

			if (x==1) {
				pstmt2=conn.prepareStatement("delete from follow where friendid=? or myid=?");
				pstmt2.setString(1, userid);
				pstmt2.setString(2, userid);
				pstmt2.executeUpdate();
			}
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
			close(conn,rs,pstmt2);

		}
		return x;
	}
	
	
	public int deleteUser2(String userid,String passwd) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2=null;
		ResultSet rs = null;
		String sql = "delete from userlist where userid=?";
		
		int x=-1;
		
		try {conn = getConnection();
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, userid);
		x=pstmt.executeUpdate();
		
			if (x==1) {
			pstmt2=conn.prepareStatement("delete from follow where friendid=? or myid=?");
			pstmt2.setString(1, userid);
			pstmt2.setString(2, userid);
			pstmt2.executeUpdate();
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
			close(conn,rs,pstmt2);
		}return x;
	}
	
	public UserlistDataBean getUser2(String userid,String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserlistDataBean user = null;
		String sql="";
		try {
			conn = getConnection();
			
			sql = "select * from userlist where userid=? and passwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, passwd);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				user = new UserlistDataBean();
				user.setNum(rs.getInt("num"));
				user.setUserid(rs.getString("userid"));
				user.setPasswd(rs.getString("passwd"));
				user.setUsername(rs.getString("username"));
				user.setDisplayname(rs.getString("displayname"));
				user.setPosition(rs.getString("position"));
				user.setGender(rs.getString("gender"));
				user.setHp(rs.getString("hp"));
				user.setAddress(rs.getString("address"));
				user.setEmail(rs.getString("email"));
				user.setReg_date(rs.getTimestamp("reg_date"));
				user.setBio(rs.getString("bio"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
		}
		return user;
	}
	
	public int updateUser(UserlistDataBean user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int chk=0;
		try {
			conn =getConnection();
			String sql = "update userlist set passwd=?,displayname=?,position=?,hp=?,address=?,email=?,bio=? where num=?";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPasswd());
			pstmt.setString(2, user.getDisplayname());
			pstmt.setString(3, user.getPosition());
			pstmt.setString(4, user.getHp());
			pstmt.setString(5, user.getAddress());
			pstmt.setString(6, user.getEmail());
			pstmt.setString(7, user.getBio());
			pstmt.setInt(8, user.getNum());
			chk=pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,null,pstmt);
		}
		
		return chk;
	
	}
	
	public boolean followchk(String sessionid, String userid) {
		boolean chk=false;
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			String sql = "select * from follow where myid=? and friendid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sessionid);
			pstmt.setString(2, userid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				chk=true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
		}
		
		return chk;
	}
	
	public boolean follow(String sessionid, String userid) {
		boolean chk=followchk(sessionid,userid);
		boolean chkfollow=false;
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		String sql="";
		conn=getConnection();
		
		try {
			if(chk) {
				
				sql="delete from follow where myid=? and friendid=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, sessionid);
				pstmt.setString(2, userid);
				pstmt.executeUpdate();
				chkfollow=false;
				
			}else {
				sql = "insert into follow(myid,friendid) values(?,?)";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, sessionid);
				pstmt.setString(2, userid);
				pstmt.executeUpdate();
				chkfollow=true;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
		}
		
		return chkfollow;
	}
	
	public int followCount(String userid) {
		int x=0;
		
		Connection con=getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number =0;
		
		try {
			String sql="select nvl(count(*),0) from follow where myid=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if(rs.next()) {x=rs.getInt(1);}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(con,rs,pstmt);
		}
		
		return x;
	}
	
	public int followerCount(String userid) {
		int x=0;
		
		Connection con=getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number =0;
		
		try {
			String sql="select nvl(count(*),0) from follow where friendid=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if(rs.next()) {x=rs.getInt(1);}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(con,rs,pstmt);
		}
		
		return x;
	}
	
	
	public List followList(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List followList = null;
		String sql = "";
		;
		
		try {
			conn=getConnection();
//			sql = "select myid,friendid from follow where myid=?";
			
			sql="select u.* from userlist u, follow f where u.userid=f.friendid and f.myid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				
				followList = new ArrayList();
				do {

					UserlistDataBean user = new UserlistDataBean();
					user.setNum(rs.getInt("num"));
					user.setUserid(rs.getString("userid"));
					user.setDisplayname(rs.getString("displayname"));
					followList.add(user);
				}while (rs.next());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {close(conn,rs,pstmt);}
		
		return followList;
	}
	
	public List followerList(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List followerList = null;
		String sql = "";
		;
		
		try {
			conn=getConnection();
//			sql = "select myid,friendid from follow where myid=?";
			
			sql="select u.* from userlist u, follow f where u.userid=f.myid and f.friendid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				
				followerList = new ArrayList();
				do {

					UserlistDataBean user = new UserlistDataBean();
					user.setNum(rs.getInt("num"));
					user.setUserid(rs.getString("userid"));
					user.setDisplayname(rs.getString("displayname"));
					followerList.add(user);
				}while (rs.next());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {close(conn,rs,pstmt);}
		
		return followerList;
	}
	
}
