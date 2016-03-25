package org.twt;

import org.twt.thread.CrawlerThread02;
/**
 * 
 * @ClassName: StartMain02 
 * @Description: 爬用户和帖子里面的发帖人和信息 
 * @author TWT
 * @date 2016年3月24日 上午9:10:31 
 *
 */
public class StartMain02 {
	public static void main(String[] args) {
		 Runnable r1 = new CrawlerThread02(15);
		 new Thread(r1).start();
		 Runnable r2 = new CrawlerThread02(263);
		 new Thread(r2).start();
	}
}
