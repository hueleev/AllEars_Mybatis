package guestMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;


public class MybatisDao extends MybatisConnector {
	private final String namespace = "ldg.mybatis";
	private static MybatisDao instance = new MybatisDao();
	public static MybatisDao getInstance() {return instance;}
	SqlSession sqlSession;
	public List<GuestDataBean> selectMsg() {
		sqlSession = sqlSession();
		System.out.println("selectmsg");
		try {return sqlSession.selectList(namespace + ".msgList");
	}finally {sqlSession.close();}}
	
	
	public List<GuestDataBean> selectMsg(int num) {
		sqlSession = sqlSession();
		System.out.println("selectmsg");
		Map map = new HashMap(); map.put("num", num);
		try { return sqlSession.selectList(namespace+".msgList",map);
	}finally {sqlSession.close();}}
	
	public List<GuestDataBean> selectMsg(String boardid) {
		sqlSession = sqlSession();
		Map map = new HashMap(); map.put("boardid", boardid);
		try { 
			return sqlSession.selectList(namespace+".msgList",map);
		}finally {sqlSession.close();
		
		
	}

}
}
