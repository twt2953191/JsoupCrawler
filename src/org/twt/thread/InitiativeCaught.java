package org.twt.thread;

public class InitiativeCaught {
	public void threadDeal(Runnable r, Throwable t) {
		if (t == null) {
//			System.out.println("there is a exception in : " + r.getClass().getName() + t.getMessage());
			t.printStackTrace();
		}else{
			System.out.println("it has crawlered !");
		}
	}
}
