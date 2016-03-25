package org.twt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.twt.entity.User;
import org.twt.thread.CrawlerThread03;

/**
 * 
 * @ClassName: StartMain03
 * @Description: 爬用户头像
 * @author TWT
 * @date 2016年3月24日 上午9:10:57
 */
public class StartMain03 {
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
		List<User> list = new ArrayList<User>();
		Connection connection = getMysqlConnection("127.0.0.1", 3306, "crawler_education", "root", "root");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlGet = "SELECT userid,username,source FROM crawler_education.user where hasimg IS NULL";
		try {
			ps = connection.prepareStatement(sqlGet);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				int userid = rs.getInt("userid");
				user.setId(userid);
				user.setUsername(rs.getString("username"));
				user.setSource(rs.getString("source"));
				list.add(user);
			}
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (connection != null)
				connection.close();
			int allCount = list.size();
			System.out.println("all user numebr is " + allCount);
			// 将开启4个线程进行用户头像的爬取
			int threadCount = 8;
			int every = allCount / threadCount;
			// System.out.println(every);
			for (int i = 0; i < threadCount; i++) {
				int fromIndex = i * every;
				int toIndex = 0;
				if (i != threadCount - 1) {
					toIndex = (i + 1) * every;
				} else {
					toIndex = allCount;
				}
				System.out.println(fromIndex + " to " + (toIndex + 1));
				List<User> tempList = new ArrayList<User>();
				tempList = list.subList(fromIndex, toIndex);
				System.out.println("tempList size:" + tempList.size());
				Runnable r = new CrawlerThread03(tempList);
				new Thread(r).start();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
