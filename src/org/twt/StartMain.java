package org.twt;

import org.twt.thread.CrawlerThread;

/**
 * 
 * @ClassName: StartMain
 * @Description: 爬用户和帖子列表
 * @author TWT
 * @date 2016年3月24日 上午9:10:05
 *
 */
public class StartMain {
	public static void main(String[] args) {
		// http://www.19lou.com/forum-15-500.html
		// http://www.19lou.com/forum-263-500.html
		String flag1 = "15";
		String flag2 = "263";
		Runnable r1 = new CrawlerThread(flag1);
		new Thread(r1).start();
		
		Runnable r2 = new CrawlerThread(flag2);
		new Thread(r2).start();
	}
}
