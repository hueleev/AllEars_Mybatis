/*package Userlist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import songBoard.SongDataBean;

import model.BoardDataBean;

public class MybatisDao extends MybatisConnector {
	private final String namespace = "ldg.mybatis";
	private static MybatisDao instance = new MybatisDao();
	public static MybatisDao getInstance() {return instance;}
	SqlSession sqlSession;
	public List<UserlistDataBean> selectUser() {
		sqlSession = sqlSession();
		System.out.println("selectuser");
		try {return sqlSession.selectList(namespace + ".userList");
	}finally {sqlSession.close();}}
	
	
	public List<UserlistDataBean> selectUser(int num) {
		sqlSession = sqlSession();
		System.out.println("selectuser");
		Map map = new HashMap(); map.put("num", num);
		try { return sqlSession.selectList(namespace+".userList",map);
	}finally {sqlSession.close();}}
	
	public List<UserlistDataBean> selectUser(String boardid) {
		sqlSession = sqlSession();
		Map map = new HashMap(); map.put("boardid", boardid);
		try { 
			return sqlSession.selectList(namespace+".userList",map);
		}finally {sqlSession.close();
	}
	}
	
	
	public List<EtcinfoDataBean> selectEtc() {
		sqlSession = sqlSession();
		System.out.println("selectetc");
		try {return sqlSession.selectList(namespace + ".etcList");
	}finally {sqlSession.close();}}
	
	
	public List<EtcinfoDataBean> selectEtc(int num) {
		sqlSession = sqlSession();
		System.out.println("selectetc");
		Map map = new HashMap(); map.put("num", num);
		try { return sqlSession.selectList(namespace+".etcList",map);
	}finally {sqlSession.close();}}
	
	public List<EtcinfoDataBean> selectEtc(String boardid) {
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("boardid", boardid);
		
		try { 
			return sqlSession.selectList(namespace+".etcList",map);
		}finally {sqlSession.close();
	}
	}

	public List<SongDataBean> selectSong() {
		sqlSession = sqlSession();
		System.out.println("selectsong");
		try {return sqlSession.selectList(namespace + ".songList");
	}finally {sqlSession.close();}}
	
	
	public List<SongDataBean> selectSong(int snum) {
		sqlSession = sqlSession();
		System.out.println("selectsong");
		Map map = new HashMap(); map.put("snum", snum);
		try { return sqlSession.selectList(namespace+".songList",map);
	}finally {sqlSession.close();}}
	
	public List<SongDataBean> selectSong(String sboardid) {
		sqlSession = sqlSession();
		Map map = new HashMap(); map.put("sboardid", sboardid);
		try { 
			return sqlSession.selectList(namespace+".songList",map);
		}finally {sqlSession.close();
	
	}

}
	
}
*/