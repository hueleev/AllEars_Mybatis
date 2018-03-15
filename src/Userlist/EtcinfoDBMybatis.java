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

public class EtcinfoDBMybatis extends MybatisConnector {
	private final String namespace = "ldg.mybatis";
	private static EtcinfoDBMybatis instance = new EtcinfoDBMybatis();
	
	private EtcinfoDBMybatis() {

	}

	public static EtcinfoDBMybatis getInstance() {
		return instance;
	}

	
	SqlSession sqlSession;
	public EtcinfoDataBean getEtc(String etcid) {
		sqlSession = sqlSession();
		Map<String,String> map = new HashMap<String,String>();
		map.put("etcid", etcid);
		
		EtcinfoDataBean etc=sqlSession.selectOne(namespace+".getEtc", map);
		sqlSession.close();
		
		return etc;
		
	}
	
	public List getTimeline(String userid) {
		sqlSession = sqlSession();
		Map map = new HashMap();
		map.put("myid", userid);

		List li = sqlSession.selectList(namespace+".getTimeline",map);
		sqlSession.close();
		
		return li;
		
	}

	
}