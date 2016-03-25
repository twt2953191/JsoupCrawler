package org.twt.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CrawlerUtils {
	public static Document getHtml(String url) throws IOException {
		Document doc = null;
		doc = Jsoup.connect(url).timeout(3000).ignoreContentType(true)
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
				.header("Host", "www.19lou.com")
				.header("Cookie",
						"_Z3nY0d4C_=37XgPK9h-%3D1920-1920-1920-955; PHPSESSID=a420ab54588f34d1e5c5b2a5a86ce29b; sys=33620234.20480.0000; _DM_S_=94fd2f1ea15fa1eb29c64c7f557ad6b1; f8big=ip49; JSESSIONID=C7F50A940D466F5AB494130372599DEF; _dm_userinfo=%7B%22uid%22%3A0%2C%22category%22%3A%22%22%2C%22sex%22%3A%22%22%2C%22frontdomain%22%3A%22www.19lou.com%22%2C%22stage%22%3A%22%22%2C%22ip%22%3A%22125.120.149.201%22%2C%22city%22%3A%22%E6%B5%99%E6%B1%9F%3A%E6%9D%AD%E5%B7%9E%22%7D; _DM_SID_=2e5f4513e10032b6a56f948a95354ffd; screen=1903; _dm_tagnames=%5B%7B%22k%22%3A%22%E5%B0%8F%E5%AD%A6%E6%95%99%E8%82%B2%22%2C%22c%22%3A1%7D%2C%7B%22k%22%3A%22%E7%96%AB%E8%8B%97%22%2C%22c%22%3A1%7D%2C%7B%22k%22%3A%22%E5%B0%8F%E5%AD%A6%22%2C%22c%22%3A1%7D%5D; pm_count=%7B%22pc_hangzhou_cityEnterMouth_advmodel_adv_210x200_2%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_210x200_1%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_210x200_4%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_210x200_3%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_210x200_6%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_210x401_1%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_210x200_7%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_330x401_3%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_330x200_1%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_330x401_1%22%3A1%2C%22pc_hangzhou_cityEnterMouth_advmodel_adv_330x401_2%22%3A1%2C%22pc_allCity_threadView_button_adv_300x200%22%3A1%2C%22pc_allCity_threadView_button_adv_190x205_1%22%3A1%2C%22pc_hangzhou_forumthread_button_adv_180x180_4%22%3A1%2C%22pc_allCity_threadView_streamer_adv_800x90_2%22%3A1%2C%22pc_hangzhou_forumthread_button_adv_180x180_3%22%3A1%2C%22pc_hangzhou_forumthread_button_adv_180x180_2%22%3A1%2C%22pc_hangzhou_forumthread_button_adv_180x180_1%22%3A1%7D; dayCount=%5B%7B%22id%22%3A5222%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8220%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8377%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8201%2C%22count%22%3A1%7D%2C%7B%22id%22%3A7713%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8338%2C%22count%22%3A1%7D%2C%7B%22id%22%3A7819%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8866%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8097%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8346%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8749%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8873%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8018%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8241%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8743%2C%22count%22%3A1%7D%2C%7B%22id%22%3A7911%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8326%2C%22count%22%3A1%7D%2C%7B%22id%22%3A8864%2C%22count%22%3A1%7D%5D; Hm_lvt_5185a335802fb72073721d2bb161cd94=1458719501; Hm_lpvt_5185a335802fb72073721d2bb161cd94=1458719507")
				.get();
		return doc;
	}
}
