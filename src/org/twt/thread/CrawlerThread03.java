package org.twt.thread;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.twt.entity.User;
import org.twt.util.CrawlerUtils;

public class CrawlerThread03 implements Runnable {
	ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	private Connection conn = null;
	private List<User> list = new ArrayList<User>();

	public CrawlerThread03(List<User> list) {
		this.list = list;
	}

	@Override
	public void run() {
		try {
			for (User u : list) {
				String userid = u.getId() + "";
				String url = "http://www.19lou.com/user/profile-" + userid + "-1.html";
				// 睡一会儿 防止太快被封IP
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Document doc = null;
				try {
					doc = CrawlerUtils.getHtml(url);

				} catch (IOException e) {
					e.printStackTrace();
				}
				if (doc == null)
					continue;
				String imgUrl = doc.select(".uinfo-mod.clearall.uinfo-tahometips").select("img").attr("src");
				System.out.println(Thread.currentThread().getName() + " : " + imgUrl);
				saveUserImg(userid + "", imgUrl);
				System.out.println("Thread " + Thread.currentThread().getName() + " catch the photo of the user["
						+ userid + "] has completed !");
				if (tl.get() == null) {
					conn = getMysqlConnection("127.0.0.1", 3306, "crawler_education", "root", "root");
					tl.set(conn);
				}
				update("update user set hasimg = 1 where userid = ? ", userid, tl.get());
			}
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	private void update(String string, String userid, Connection mysqlConnection) {
		PreparedStatement ps = null;
		try {
			ps = mysqlConnection.prepareStatement(string);
			ps.setInt(1, Integer.parseInt(userid));
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 
	 * @Title: saveUserImg @Description: 通过httpclient下载用户头像 @author
	 *         TWT @param @param userid @param @param imgUrl 参数说明 @return void
	 *         返回类型 @throws
	 */
	public void saveUserImg(String userid, String imgUrl) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		try {
			HttpGet httpget = new HttpGet(imgUrl);
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
					.build();
			httpget.setConfig(requestConfig);

			String first_dir = "000";
			String second_dir = "00";
			String third_dir = "00";
			String file_num = "";

			if (userid.length() > 6) {

				if (userid.length() == 8)
					userid = "0" + userid;
				if (userid.length() == 7)
					userid = "00" + userid;

				first_dir = userid.substring(0, userid.length() - 6);
				second_dir = userid.substring(3, userid.length() - 4);
				third_dir = userid.substring(5, userid.length() - 2);
				file_num = userid.substring(7, userid.length());

			} else if (userid.length() <= 6 && userid.length() > 4) {

				if (userid.length() == 5)
					userid = "0" + userid;

				second_dir = userid.substring(0, userid.length() - 4);
				third_dir = userid.substring(2, userid.length() - 2);
				file_num = userid.substring(4, userid.length());

			} else if (userid.length() <= 4 && userid.length() > 2) {

				if (userid.length() == 3)
					userid = "0" + userid;

				third_dir = userid.substring(0, userid.length() - 2);
				file_num = userid.substring(2, userid.length());
			} else {

				if (userid.length() == 1)
					userid = "0" + userid;

				file_num = userid;

			}

			File file_first = new File("D://php//wamp//www//discuz//upload//uc_server//data//avatar//" + first_dir);
			File file_second = new File(
					"D://php//wamp//www//discuz//upload//uc_server//data//avatar//" + first_dir + "//" + second_dir);
			File file_third = new File("D://php//wamp//www//discuz//upload//uc_server//data//avatar//" + first_dir
					+ "//" + second_dir + "//" + third_dir);

			if (!file_first.exists()) {
				file_first.mkdirs();
			}
			if (!file_second.exists()) {
				file_second.mkdirs();
			}
			if (!file_third.exists()) {
				file_third.mkdirs();
			}

			String dir = "D://php//wamp//www//discuz//upload//uc_server//data//avatar//" + first_dir + "//" + second_dir
					+ "//" + third_dir + "//";

			String filename = file_num + "_avatar_middle.jpg";

			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);

			byte[] result = EntityUtils.toByteArray(response.getEntity());
			BufferedOutputStream bw = null;
			try {
				// 创建文件对象
				File f = new File(dir + filename);
				// 创建文件路径
				if (!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				// 写入文件
				bw = new BufferedOutputStream(new FileOutputStream(dir + filename));
				bw.write(result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (bw != null)
						bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public Connection getMysqlConnection(String host, int port, String dbName, String username, String password) {
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

}
