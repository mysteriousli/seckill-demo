package education.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.seckilldemo.mapper.BlogMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

@MapperScan("com.example.demo.mapper")
public class Main {
    public static void main(String[] args) {
        DataSource dataSource = getBlogDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        for (int i = 0; i <5 ; i++) {
            new Thread(() -> {
                SqlSession sqlSession = sqlSessionFactory.openSession();
                BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
                System.out.println(blogMapper.selectBlog());
            }, String.valueOf(i)).start();
        }
    }
    public static DataSource getBlogDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3307/seckill?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
        return druidDataSource;
    }
}
