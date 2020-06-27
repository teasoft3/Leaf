package com.sankuai.inf.leaf.segment.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

//import org.apache.ibatis.mapping.Environment;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.apache.ibatis.transaction.TransactionFactory;
//import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.teasoft.bee.osql.Condition;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.bee.osql.transaction.Transaction;
import org.teasoft.honey.osql.core.BeeFactory;
import org.teasoft.honey.osql.core.ConditionImpl;
import org.teasoft.honey.osql.core.SessionFactory;

import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.pool.DruidDataSource;
import com.sankuai.inf.leaf.segment.dao.IDAllocDao;
//import com.sankuai.inf.leaf.segment.dao.IDAllocMapper;
import com.sankuai.inf.leaf.segment.model.LeafAlloc;
//import com.sankuai.inf.leaf.segment.dao.IDAllocMapper;

public class IDAllocDaoImpl implements IDAllocDao {

//    SqlSessionFactory sqlSessionFactory;
//	BeeFactory beeFactory=new BeeFactory();
	SuidRich suidRich=BeeFactory.getHoneyFactory().getSuidRich();

    public IDAllocDaoImpl(DataSource dataSource1) throws SQLException{
/*        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(IDAllocMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);*/

    	 BeeFactory.getInstance().setDataSource(dataSource1);
    }

    @Override
    public List<LeafAlloc> getAllLeafAllocs() {
//        SqlSession sqlSession = sqlSessionFactory.openSession(false);
//        try {
//            return sqlSession.selectList("com.sankuai.inf.leaf.segment.dao.IDAllocMapper.getAllLeafAllocs");
//        } finally {
//            sqlSession.close();
//        }
    	
      return suidRich.select(new LeafAlloc());
    }

    @Override
    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        try {
//        	sqlSession.update("com.sankuai.inf.leaf.segment.dao.IDAllocMapper.updateMaxId", tag);
//        	LeafAlloc result = sqlSession.selectOne("com.sankuai.inf.leaf.segment.dao.IDAllocMapper.getLeafAlloc", tag);
//            sqlSession.commit();
//            return result;
//        } finally {
//            sqlSession.close();
//        }

    	LeafAlloc result =null;
		Transaction transaction = SessionFactory.getTransaction();
		try {
			transaction.begin();
			
//		    "UPDATE leaf_alloc SET max_id = max_id + step WHERE biz_tag = #{tag}"
			LeafAlloc leafAlloc=new LeafAlloc();
			leafAlloc.setBizTag(tag);
	    	Condition condition=new ConditionImpl();
	    	condition.setAdd("maxId", "step"); 
	    	suidRich.update(leafAlloc, "maxId", condition);
	    	
//	    	"SELECT biz_tag, max_id, step FROM leaf_alloc WHERE biz_tag = #{tag}"
	    	result = suidRich.selectOne(leafAlloc);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}	
    	
        return result;
    }

    @Override
    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        try {
//            sqlSession.update("com.sankuai.inf.leaf.segment.dao.IDAllocMapper.updateMaxIdByCustomStep", leafAlloc);
//            LeafAlloc result = sqlSession.selectOne("com.sankuai.inf.leaf.segment.dao.IDAllocMapper.getLeafAlloc", leafAlloc.getKey());
//            sqlSession.commit();
//            return result;
//        } finally {
//            sqlSession.close();
//        }
    	
    	LeafAlloc result =null;
		Transaction transaction = SessionFactory.getTransaction();
		try {
			transaction.begin();
			
	    	Condition condition=new ConditionImpl();
	    	condition.setAdd("maxId", leafAlloc.getStep());
	    	suidRich.update(leafAlloc, "maxId", condition);
	    	result = suidRich.selectOne(leafAlloc);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}	
    	
        return result;
    }

    @Override
    public List<String> getAllTags() {
//        SqlSession sqlSession = sqlSessionFactory.openSession(false);
//        try {
//            return sqlSession.selectList("com.sankuai.inf.leaf.segment.dao.IDAllocMapper.getAllTags");
//        } finally {
//            sqlSession.close();
//        }
    	
    	LeafAlloc leafAlloc=new LeafAlloc();
    	List<String[]> list=suidRich.selectString(leafAlloc);
    	return _transfer(list);
    }
    
    private List<String> _transfer(List<String[]> list){
    	if(list==null) return null;
    	List<String> result=new ArrayList<>();
    	for (int i = 0; i < list.size(); i++) {
    		result.add(list.get(i)[0]);
		}
    	return result;
    }
}
