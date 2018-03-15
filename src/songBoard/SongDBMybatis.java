package songBoard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class SongDBMybatis extends MybatisConnector {
	private final String namespace = "ldg.mybatis";
	private static SongDBMybatis instance = new SongDBMybatis();
	
	private SongDBMybatis() {

	}

	public static SongDBMybatis getInstance() {
		return instance;
	}

	
	SqlSession sqlSession;


	
	
}