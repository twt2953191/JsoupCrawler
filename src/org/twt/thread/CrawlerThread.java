package org.twt.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.twt.entity.Note;
import org.twt.entity.NoteUserVO;
import org.twt.entity.User;
import org.twt.util.CrawlerUtils;

public class CrawlerThread implements Runnable {
	private Connection conn = null;
	private String flag;// 263表示小学 15表示初中
	private int pageIndex;

	public CrawlerThread(String flag) {
		super();
		this.flag = flag;
	}

	@Override
	public void run() {
		Throwable thrown = null;
		Document doc = null;
		try {
			for (int i = 1; i <= 500; i++) {
				// http://www.19lou.com/forum-15-500.html
				// http://www.19lou.com/forum-263-500.html
				String url = "http://www.19lou.com/forum-" + flag + "-" + i + ".html";
				pageIndex = i;
				try {
					Thread.sleep(888);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try {
					doc = CrawlerUtils.getHtml(url);
					if (doc == null) {
						System.out.println("step 1 : conne't connect [" + url + "] , doc is null !");
					} else {
						String[] arrStr = url.split("-");
						// 模块ID
						Integer modual_id = Integer.parseInt(arrStr[1]);
						// 模块名称
						String modual_name = doc.select("#header-nav").select("h2 em").text();
						// 帖子是第几页的
						Integer page_current = Integer.parseInt(arrStr[2].split("\\.")[0]);
						Elements items = doc.select("tbody tr");
						if (items == null || items.size() == 0) {
							System.out.println("step 2 : conn't find this item !");
						} else {
							NoteUserVO crawlerVO = crawlerNote(modual_id, modual_name, page_current, items);
							saveInMysql(crawlerVO);
						}
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}

	private NoteUserVO crawlerNote(Integer modual_id, String modual_name, Integer page_current, Elements items) {
		NoteUserVO vo = new NoteUserVO();
		HashMap<Integer, User> userMap = null;
		List<Note> noteList = null;
		userMap = new HashMap<Integer, User>();
		noteList = new ArrayList<Note>();
		for (Element e : items) {
			Element subject = e.select(".subject").first();
			Element titleElement = subject.select("a span").first();
			Element urlElement = subject.select("a").first();
			// 帖子的url
			String urlNote = urlElement.attr("href");
			// 帖子ID
			long noteid = Long.parseLong(urlNote.split("-")[3]);
			// 标题
			String title = titleElement.text();
			Element author = e.select(".author").first();
			// 发帖人id
			Integer authorId = Integer.parseInt(author.select("a").attr("href").split("-")[1]);
			// 作者名称
			String name = author.select("a span").text();
			// 作者来源
			String source1 = "2";
			// 发帖时间
			Integer create_date = Integer.parseInt(author.select("span.color9").text().replaceAll("-", ""));
			Element lastpost = e.select(".lastpost").first();
			// 最后回帖人id
			Integer lastpublishId = Integer.parseInt(lastpost.select("a").attr("href").split("-")[1]);
			// 最后回帖人姓名
			String lastpublishname = lastpost.select("a span").text();
			String lastpublish_time = lastpost.select(".numeral").text();// 最后回帖时间
			// 帖子来源
			Integer source2 = 2;
			Note note = new Note(noteid, modual_id, modual_name, title, urlNote, page_current, authorId, create_date,
					lastpublishId, lastpublish_time, source2);
			User user1 = new User(authorId, name, source1);
			User user2 = new User(lastpublishId, lastpublishname, source1);
			userMap.put(authorId, user1);
			userMap.put(lastpublishId, user2);
			noteList.add(note);
		}
		vo.setNoteList(noteList);
		vo.setUserMap(userMap);
		return vo;
	}

	private void saveInMysql(NoteUserVO vo) {
		conn = getMysqlConnection("127.0.0.1", 3306, "crawler_education", "root", "root");
		PreparedStatement ps = null;
		final String sql_user = "INSERT INTO crawler_education.user (userid, username, source) VALUES (?, ?, ?)";
		final String sql_note = "INSERT INTO crawler_education.note_list (noteid,modual_id,modual_name,title,url,page_current,author,create_date,lastpublish,lastpublish_time,source) VALUES(?,?,?,?,?, ?,?,?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql_user);
			HashMap<Integer, User> userMap = vo.getUserMap();
			for (Entry<Integer, User> entry : userMap.entrySet()) {
				User u = entry.getValue();
				// System.out.println(u.toString());
				ps.setInt(1, u.getId());
				ps.setString(2, u.getUsername());
				ps.setString(3, u.getSource());
				ps.addBatch();
			}
			ps.execute();
			conn.commit();
			ps.clearBatch();
			System.out.println("user in " + pageIndex + " has crawlered!");
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			ps = null;
			ps = conn.prepareStatement(sql_note);
			List<Note> noteList = vo.getNoteList();
			for (Note n : noteList) {
				// System.out.println(n.toString());
				ps.setLong(1, n.getNoteid());
				ps.setInt(2, n.getModual_id());
				ps.setString(3, n.getModual_name());
				ps.setString(4, n.getTitle());
				ps.setString(5, n.getUrl());
				ps.setInt(6, n.getPage_current());
				ps.setInt(7, n.getAuthor());
				ps.setInt(8, n.getCreate_date());
				ps.setInt(9, n.getLastpublish());
				ps.setString(10, n.getLastpublish_time());
				ps.setInt(11, n.getSource());
				ps.addBatch();
			}
			ps.execute();
			conn.commit();
			ps.clearBatch();
			System.out.println("note in " + pageIndex + " has crawlered!");
		} catch (SQLException e) {
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

	public CrawlerThread() {
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

	public static void main(String[] args) {
		CrawlerThread c = new CrawlerThread();
		System.out.println(c.getMysqlConnection("127.0.0.1", 1521, "crawler_education", "root", "root"));
	}
}
