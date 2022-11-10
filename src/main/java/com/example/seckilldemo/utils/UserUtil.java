package com.example.seckilldemo.utils;

import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.vo.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {
    private static void createUser(int count) throws ClassNotFoundException, SQLException, IOException {
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setLoginCount(1);
            user.setId(13000000000L + i);
            user.setNickname("user" + i);
            user.setSlat("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSlat()));
            users.add(user);
            user.setRegisterDate(new Date());
        }
//        System.out.println("create user");
//        Connection conn = getConn();
//
//        String sql = "insert into t_user(login_count,nickname,register_date,slat,password,id) value (?,?,?,?,?,?)";
//        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            preparedStatement.setInt(1, user.getLoginCount());
//            preparedStatement.setString(2, user.getNickname());
//            preparedStatement.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
//            preparedStatement.setString(4, user.getSlat());
//            preparedStatement.setString(5, user.getPassword());
//            preparedStatement.setLong(6, user.getId());
//            preparedStatement.addBatch();
//        }
//        preparedStatement.executeBatch();
//        preparedStatement.clearParameters();
//        conn.close();
        //登录,生成userTicket
        String urlString = "http://localhost:8080/login/doLogin";
        File file = new File("C:\\Users\\DELL\\Desktop\\seckill_demo-master\\document\\config.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStream out = urlConnection.getOutputStream();
            String param = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFromPass("123456");
            out.write(param.getBytes());
            out.flush();
            InputStream inputStream = urlConnection.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] bf = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bf)) >= 0) {
                bout.write(bf, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            System.out.println(response);
            ObjectMapper mapper = new ObjectMapper();
            RespBean respBean = mapper.readValue(response, RespBean.class);
            String userTicket = (String) respBean.getObj();
            System.out.println("create userTicket:" + user.getId());
            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file:" + user.getId());
        }
        raf.close();
        System.out.println("over");
    }

    private static Connection getConn() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://8.130.103.104:3306/seckill?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String userName = "root";
        String password = "123456";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, userName, password);
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        createUser(1000);
    }
}
