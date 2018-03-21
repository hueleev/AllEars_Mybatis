package guestMsg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;


public class GuestDBMybatis extends MybatisConnector {
	private final String namespace = "ldg.mybatis";
	private static GuestDBMybatis instance = new GuestDBMybatis();
	
	private GuestDBMybatis() {

	}

	public static GuestDBMybatis getInstance() {
		return instance;
	}

	
	SqlSession sqlSession;

	public void insertMsg(GuestDataBean msg) {
		sqlSession = sqlSession();

		
		int number=sqlSession.selectOne(namespace+".getNextNumber",msg);
		
			if (msg.getGnum()!=0) {
				//���
				sqlSession.update(namespace+".updateRe_step",msg);
				msg.setRe_level(msg.getRe_level()+1);
				msg.setRe_step(msg.getRe_step()+1);
				
			}else {
				//����
				msg.setRef(number);
				msg.setRe_level(0);
				msg.setRe_step(0);
			}

			msg.setGnum(number);
			
			sqlSession.insert(namespace+".insertMsg",msg);
			sqlSession.commit(); //DML�̴ϱ� ~ !! ! !
			sqlSession.close();
	}
	
	public int getMsgCount(String gboardid) {
		
		int x = 0;
		sqlSession = sqlSession();
		Map<String,String> map = new HashMap<String,String>();
		map.put("gboardid", gboardid);
		x = sqlSession.selectOne(namespace+".getMsgCount",map);
		sqlSession.close();
		return x;
		
	}
	
	public List getMsgs(int startRow, int endRow,String gboardid) {
		
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("gboardid", gboardid);
		
		List li = sqlSession.selectList(namespace+".getMsgs",map);
		sqlSession.close();
		
		
		return li;
	}
	
	public GuestDataBean getMsg(int gnum,String gboardid,String chk) {
		
		
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("gnum", gnum);
		map.put("gboardid", gboardid);

		
		GuestDataBean msg = sqlSession.selectOne(namespace+".getMsg",map);
		sqlSession.commit(); 
		sqlSession.close();
		
		return msg;
	}
	
	public int deleteMsg(int gnum,String gboardid) throws Exception {
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("gnum", gnum);
		map.put("gboardid", gboardid);
		
		int chk=sqlSession.delete(namespace+".deleteMsg",map);
		sqlSession.commit();
		sqlSession.close();
		
		return chk;
		
	}
	
	public int updateMsg(GuestDataBean msg) {
		
		sqlSession = sqlSession();

		int chk = sqlSession.update(namespace+".updateMsg",msg);
		sqlSession.commit(); 
		sqlSession.close();
		
		return chk;
		
	}
}