package Userlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class UserlistDBMybatis extends MybatisConnector {
	private final String namespace = "ldg.mybatis";
	private static UserlistDBMybatis instance = new UserlistDBMybatis();
	
	private UserlistDBMybatis() {

	}

	public static UserlistDBMybatis getInstance() {
		return instance;
	}

	
	SqlSession sqlSession;

	
	/*public int getArticleCount(String boardid) {
		int x = 0;
		sqlSession = sqlSession();
		Map<String,String> map = new HashMap<String,String>();
		map.put("boardid", boardid);
		x = sqlSession.selectOne(namespace+".getArticleCount",map);
		sqlSession.close();
		return x;

	}
*/
	/*public List getArticles(int startRow, int endRow, String boardid) {
		
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("boardid", boardid);
		
		List li = sqlSession.selectList(namespace+".getArticles",map);
		sqlSession.close();
		
		
		return li;
	}*/
	
	public void insertUser(UserlistDataBean user) {
		sqlSession = sqlSession();

		
		int number=sqlSession.selectOne(namespace+".getNextNumber",user);
		
			user.setNum(number);
						
			
			sqlSession.insert(namespace+".insertUser",user);
			sqlSession.commit(); //DML¿Ã¥œ±Ó ~ !! ! !
			sqlSession.close();
			
			

	
		}
	
	public boolean confirmId(String userid) {
		sqlSession = sqlSession();
		Map<String,String> map = new HashMap<String,String>();
		map.put("userid", userid);
		boolean li=true;
		
		Map<String,String> map2=sqlSession.selectOne(namespace+".confirmId",map);
		
		if (map2!=null) {
			li=true;
		}else {
			li=false;
		}
		sqlSession.close();
		
		return li;
	}
	
	public int login(String userid, String passwd) {
		sqlSession = sqlSession();
		Map<String,String> map = new HashMap<String,String>();
		map.put("userid", userid);
		map.put("passwd", passwd);
		int chk=0;
		
		Map<String,String> map2=sqlSession.selectOne(namespace+".login", map);
		
		if (map2!=null && map2.containsValue(passwd)) {
			chk=1;
			sqlSession.close();
			return chk;
		}

		if (map2!=null) {
			chk=0;
			sqlSession.close();
			return chk;
		}else {
			chk=-1;
			sqlSession.close();
			return chk;
		}
	
	}
		
	
	public UserlistDataBean getUser2(String userid,String passwd) {
		sqlSession = sqlSession();
		Map<String,String> map = new HashMap<String,String>();
		map.put("userid", userid);
		map.put("passwd", passwd);
		UserlistDataBean user=sqlSession.selectOne(namespace+".getUser2", map);
		sqlSession.close();
		return user;
		
	}
	
	public int followCount(String userid) {
		sqlSession = sqlSession();
		Map<String,String> map = new HashMap<String,String>();
		map.put("myid", userid);
		
		
		int count=sqlSession.selectOne(namespace+".followCount", map);
		
		return count;
		
	}
	
	
	public int followerCount(String userid) {
		sqlSession = sqlSession();
		Map<String,String> map = new HashMap<String,String>();
		map.put("friendid", userid);
		
		int count=sqlSession.selectOne(namespace+".followerCount", map);

		return count;

	}
	
	public List followList(String userid) {
		
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("myid", userid);

		List li = sqlSession.selectList(namespace+".followList",map);
		sqlSession.close();
		
		
		return li;
	}

	public List followerList(String userid) {
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("friendid", userid);

		List li = sqlSession.selectList(namespace+".followerList",map);
		sqlSession.close();
		
		return li;

	}
	
	public List getUsers(int startRow,int endRow) {
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		List li = sqlSession.selectList(namespace+".getUsers",map);
		sqlSession.close();
		
		return li;
	}
	
	public int getUserCount() {
		sqlSession = sqlSession();

		
		int number=sqlSession.selectOne(namespace+".getUserCount");
		
		sqlSession.close();
		return number;
	}
	
/*	public BoardDataBean getArticle
	(int num , String boardid, String chk) {
		
		
		update board set readcount=readcount+1 where num = ? and boardid = ?
		select * from board where num = ? and boardid = ?
		
		
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("num", num);
		map.put("boardid", boardid);

		if (chk.equals("content")) {
			
			sqlSession.update(namespace+".addReadCount",map);
		}
		BoardDataBean article = sqlSession.selectOne(namespace+".getArticle",map);
		sqlSession.commit(); 
		sqlSession.close();
		
		
		return article;
		
	}*/
	
	/*public int updateArticle(BoardDataBean article) {
		
		//update board set writer=?,email=? subject=? ,content=? where num=? and passwd = ?
		sqlSession = sqlSession();

		int chk = sqlSession.update(namespace+".updateArticle",article);
		sqlSession.commit(); 
		sqlSession.close();
		
		return chk;
	}*/
	
	/*public int deleteArticle(int num, String passwd, 
			String boardid) throws Exception{
		
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("num", num);
		map.put("passwd", passwd);
		map.put("boardid", boardid);
		
		int chk=sqlSession.delete(namespace+".deleteArticle",map);
		sqlSession.commit();
		sqlSession.close();
		
		return chk;
	}*/
}