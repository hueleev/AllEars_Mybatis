package songBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDBBean {
	private static SongDBBean instance = new SongDBBean();
	private SongDBBean () {
		
	}
	
	public static SongDBBean getInstance() {
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
	
	public void insertSong(SongDataBean song) {
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
			
			int num = song.getSnum();

			
			sql = "insert into songBoard(sboardid, snum, stitle, genre, cfilename, cfilesize,"
					+ "sfilename,sfilesize,sbio,sreg_date) "
					+ "values(?,?,?,?,?,?,?,?,?,sysdate)";

		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, song.getSboardid());
		pstmt.setInt(2, number);
		pstmt.setString(3, song.getStitle());
		pstmt.setString(4, song.getGenre());
		pstmt.setString(5, song.getCfilename());
		pstmt.setInt(6, song.getCfilesize());
		pstmt.setString(7, song.getSfilename());
		pstmt.setInt(8, song.getSfilesize());
		pstmt.setString(9, song.getSbio());
		pstmt.executeUpdate();
		
		}catch(SQLException e1) {
			e1.printStackTrace();
		}finally {
			
			close(con,rs,pstmt);
		}
		
	}
	
	public int getSongCount(String sboardid) {
		int x=0;
		String sql="select nvl(count(*),0) from songboard where sboardid =?";
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, sboardid);
		
		rs=pstmt.executeQuery();
		if(rs.next()) {x=rs.getInt(1);}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con,rs,pstmt);
		}
		return x;
		
	}
	
	public List getSongs(int startRow, int endRow,String sboardid) {
	
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List songList = null;
			String sql = "";
			
			try { 		
				conn=getConnection();
				sql = " select * from "
						+ "( select rownum rnum,a.* "
						+ "from (select * from songboard where sboardid = ? order by snum desc) "
						+ " a ) where rnum between ? and ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, sboardid);
				pstmt.setInt(2,startRow);
				pstmt.setInt(3, endRow);;
				rs=pstmt.executeQuery();

				if (rs.next()) {
					songList = new ArrayList();
					do {
						SongDataBean song = new SongDataBean();
						song.setSboardid(rs.getString("sboardid"));
						song.setSnum(rs.getInt("snum"));
						song.setStitle(rs.getString("stitle"));
						song.setGenre(rs.getString("genre"));
						song.setCfilename(rs.getString("cfilename"));
						song.setCfilesize(rs.getInt("cfilesize"));
						song.setSfilename(rs.getString("sfilename"));
						song.setSfilesize(rs.getInt("sfilesize"));
						song.setSbio(rs.getString("sbio"));
						song.setSreg_date(rs.getTimestamp("sreg_date"));
						songList.add(song);
					} while (rs.next());
					
				}
	
			}catch(Exception e) {
				e.printStackTrace();
			}finally {close(conn,rs,pstmt);}
			
			return songList;
		}
	
	public SongDataBean getSong(int snum,String sboardid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SongDataBean song = null;
		String sql="";
		try {
			conn = getConnection();
			
			sql="select * from songboard where snum = ? and sboardid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, snum);
			pstmt.setString(2, sboardid);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				song = new SongDataBean();
				song.setSboardid(rs.getString("sboardid"));
				song.setSnum(rs.getInt("snum"));
				song.setStitle(rs.getString("stitle"));
				song.setGenre(rs.getString("genre"));
				song.setCfilename(rs.getString("cfilename"));
				song.setCfilesize(rs.getInt("cfilesize"));
				song.setSfilename(rs.getString("sfilename"));
				song.setSfilesize(rs.getInt("sfilesize"));
				song.setSbio(rs.getString("sbio"));
				song.setSreg_date(rs.getTimestamp("sreg_date"));

			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
		}
		return song;
		
	}
	
	public List getPage(String sboardid) {
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List songList = null;
		String sql = "";
		
		try { 		
			conn=getConnection();
			sql = " select * from songboard where sboardid = ? order by snum desc";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sboardid);

			rs=pstmt.executeQuery();

			if (rs.next()) {
				songList = new ArrayList();
				do {
					SongDataBean song = new SongDataBean();
					song.setSboardid(rs.getString("sboardid"));
					song.setSnum(rs.getInt("snum"));
					song.setStitle(rs.getString("stitle"));
					song.setGenre(rs.getString("genre"));
					song.setCfilename(rs.getString("cfilename"));
					song.setCfilesize(rs.getInt("cfilesize"));
					song.setSfilename(rs.getString("sfilename"));
					song.setSfilesize(rs.getInt("sfilesize"));
					song.setSbio(rs.getString("sbio"));
					song.setSreg_date(rs.getTimestamp("sreg_date"));
					songList.add(song);
				} while (rs.next());
				
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {close(conn,rs,pstmt);}
		
		return songList;
	}
	
	public int updateSong(SongDataBean song) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int chk=0;
		try {
			conn =getConnection();
			String sql = "update songboard set stitle=?,genre=?,sbio=? where snum=?";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, song.getStitle());
			pstmt.setString(2, song.getGenre());
			pstmt.setString(3, song.getSbio());
			pstmt.setInt(4, song.getSnum());
			chk=pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn,null,pstmt);
		}
		
		return chk;
	
	}
	
	public int deleteSong(String sboardid,int snum) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "delete from songboard where sboardid=? and snum=?";
				
		
		int x=-1;
		
		try {conn = getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, sboardid);
			pstmt.setInt(2, snum);
			x=pstmt.executeUpdate();

	
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			close(conn,rs,pstmt);
	

		}
		return x;
	}
	
	
	
	
	
}
