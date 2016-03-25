package org.twt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.twt.entity.User;
/**
 * 
 * @ClassName: TrimRepetition 
 * @Description: 去除重复的用户数据
 * @author TWT
 * @date 2016年3月24日 上午11:53:11 
 *
 */
public class TrimRepetition {
	private static Connection conn = null;

	public static Connection getMysqlConnection(String host, int port, String dbName, String username,
			String password) {
		if (conn != null)
			return conn;
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?user=" + username + "&" + "password="
				+ password + "&useUnicode=true&characterEncoding=UTF8";
		// &autoReconnect=true&failOverReadOnly=false&maxReconnects=10
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("加载数据库驱动失败!" + e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("获取数据库连接失败!" + e);
		}
		return conn;
	}

	public static void main(String[] args) {
		Map<Integer, User> map = new HashMap<Integer, User>();
		Connection connection = getMysqlConnection("127.0.0.1", 3306, "crawler_education", "root", "root");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlGet = "SELECT userid,username,source FROM crawler_education.user_02";
		String sqlSet = "INSERT INTO crawler_education.user (userid, username, source) VALUES (?,?,?)";
		int count = 0;
		try {
			ps = connection.prepareStatement(sqlGet);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				int userid = rs.getInt("userid");
				user.setId(userid);
				user.setUsername(rs.getString("username"));
				user.setSource(rs.getString("source"));
				map.put(userid, user);
				count++;
			}
			System.out.println("before : the number of user is " + count);
			System.out.println("after : the number of user is " + map.size());
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			ps = connection.prepareStatement(sqlSet);
			for (Entry<Integer, User> entry : map.entrySet()) {
				int id = entry.getKey();
				User u = entry.getValue();
				ps.setInt(1, u.getId());
				ps.setString(2, u.getUsername());
				ps.setString(3,u.getSource());
				if(ps.execute())
					System.out.println("id : "+id+" - the record has been save in mysql !");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
