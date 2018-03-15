package Userlist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EtcinfoDBBean {
	private static EtcinfoDBBean instance = new EtcinfoDBBean();
	private EtcinfoDBBean () {
		
	}
	
	public static EtcinfoDBBean getInstance() {
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

	
	public boolean chkid(String etcid){
		String sql="";
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean chkid=false;
		
		try {
			sql="select * from etcinfo where etcid=?";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, etcid);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				chkid=true;
			}
			} catch (SQLException e) {
				e.printStackTrace();
		}finally {
			close(con,rs,pstmt);
		}
		
		return chkid;
		
	}
	
	
	public void insertEtc(EtcinfoDataBean etc) {
		String sql="";
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
			
			
			sql = "insert into etcinfo(etcid,profilename,profilesize,facelink,instalink,soundlink) values(?,?,?,?,?,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, etc.getEtcid());
			pstmt.setString(2, etc.getProfilename());
			pstmt.setInt(3, etc.getProfilesize());
			pstmt.setString(4, etc.getFacelink());
			pstmt.setString(5, etc.getInstalink());
			pstmt.setString(6, etc.getSoundlink());
			pstmt.executeUpdate();

			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}finally {
			close(con,rs,pstmt);
		}
		
	}
	
	public EtcinfoDataBean getEtc(String etcid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EtcinfoDataBean etc = null;
		String sql="";
		try {
			conn = getConnection();
			
			sql = "select * from etcinfo where etcid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, etcid);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				etc = new EtcinfoDataBean();
				etc.setEtcid(rs.getString("etcid"));
				etc.setProfilename(rs.getString("profilename"));
				etc.setProfilesize(rs.getInt("profilesize"));
				etc.setFacelink(rs.getString("facelink"));
				etc.setInstalink(rs.getString("instalink"));
				etc.setSoundlink(rs.getString("soundlink"));
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
		}
		return etc;
	}
	
	public int updateEtc(EtcinfoDataBean etc) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int chk=0;
		try {
			conn =getConnection();
			String sql = "update etcinfo set etcid=?,profilename=?,profilesize=?,facelink=?,instalink=?,soundlink=? where etcid=?";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, etc.getEtcid());
			pstmt.setString(2, etc.getProfilename());
			pstmt.setInt(3, etc.getProfilesize());
			pstmt.setString(4, etc.getFacelink());
			pstmt.setString(5, etc.getInstalink());
			pstmt.setString(6, etc.getSoundlink());
			pstmt.setString(7, etc.getEtcid());
			chk=pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,null,pstmt);
		}
		
		return chk;
	
	}
	
	public int deleteImg(EtcinfoDataBean etc) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int chk=0;
		try {
			conn =getConnection();
			String sql = "update etcinfo set profilename=' ' ,profilesize=0 where etcid=?";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, etc.getEtcid());
			chk=pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,null,pstmt);
		}
		
		return chk;
	
	}
	
/*	public HashMap<String, String> getProfile(){
		HashMap<String,String> getProfile = new HashMap<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		
		try {
			conn=getConnection();
			String sql="select etcid, profilename from etcinfo";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();

			if (rs.next()) {
			getProfile.put(rs.getString("etcid"), rs.getString("profilename"));
			
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn,rs,pstmt);
		}
		
		return  getProfile;
	}
	*/
	
	public List getTimeline(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List timelineList = null;
		String sql = "";
		;
		
		try {
			conn=getConnection();
//			sql = "select myid,friendid from follow where myid=?";
			
			
			sql="select e.profilename, f.friendid, u.displayname, u.position, s.* from etcInfo e, follow f, userlist u, songboard s"
					+ " where u.userid=f.friendid and e.etcid=f.friendid and s.sboardid=f.friendid and f.myid=? order by s.snum desc";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				
				timelineList = new ArrayList();

				do {

					TimeDataBean time = new TimeDataBean();
					time.setFriendid(rs.getString("friendid"));
					time.setProfilename(rs.getString("profilename"));
					time.setDisplayname(rs.getString("displayname"));
					time.setPosition(rs.getString("position"));
					time.setStitle(rs.getString("stitle"));
					time.setSgenre(rs.getString("genre"));
					time.setCfilename(rs.getString("cfilename"));
					time.setSfilename(rs.getString("sfilename"));
					time.setSbio(rs.getString("sbio"));
					time.setSreg_date(rs.getTimestamp("sreg_date"));
					timelineList.add(time);

				}while (rs.next());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {close(conn,rs,pstmt);}
		
		return timelineList;
	}
	
}
	