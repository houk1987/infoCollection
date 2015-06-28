package org.infoCollection.service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.hk.tools.htmlParserTools.HtmlParserTools;
import org.hk.tools.httpclientTools.HttpClientTools;

public class TyCollection {
	private HttpClientTools clientTools;

	private HtmlParserTools htmlParserTools;

	int count;

	int sum;

	private ExecutorService pool;

	private static final String p = "http://bbs.tianya.cn";

	{
		clientTools = new HttpClientTools();
		htmlParserTools = new HtmlParserTools();
		pool = Executors.newCachedThreadPool();
	}

	public void colletion(String url) {
		if (url != null) {
			System.out.println(p + url);
			String html = clientTools.executeGet(p + url);
			LinkedList<String> postUrls = getAllPostUrls(html);
			if (postUrls != null) {
				for (final String postUrl : postUrls) {

					pool.execute(new Runnable() {

						public void run() {
							   
								//System.out.println(p + postUrl);
								
								//System.out.println(html);
								
									//HttpClientTools httpClientTools=new HttpClientTools();
									String html = clientTools.executeGet(p+postUrl);
									count++;
								
								
							
						}
					});
				}
			}
			sum++;
			System.out.println(sum);
			String nextPageUrl = nextPageUrl(html);
			if(nextPageUrl == null){
				html = clientTools.executeGet(p + url);
				nextPageUrl = nextPageUrl(html);
			}
			colletion(nextPageUrl);
		} else {
			pool.shutdown();  
	        while (true) {  
	            if (pool.isTerminated()) {  
	            	System.out.println(count);
	                System.out.println("结束了！");  
	                break;  
	            }  
	            try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  

	        }
		}
	}

	private LinkedList<String> getAllPostUrls(String html) {
		final List<Element> elements = htmlParserTools.getElements(new Source(
				html), "a href=\"/post-" + "1089");
		LinkedList<String> postUrls = null;
		if (elements != null) {
			postUrls = new LinkedList<String>();
			for (Element element : elements) {
				postUrls.add(element.getAttributes().getValue("href"));
			}
		}
		return postUrls;
	}

	private String nextPageUrl(String html) {
		if (html != null && !html.isEmpty()) {
			Element element = htmlParserTools.getElement(new Source(html),
					"a href=\"/list.jsp?item=" + "1089" + "&nextid=");
			if (element != null) {
				return element.getAttributes().getValue("href");
			}
		}
		return null;
	}

	public static void main(String[] args) {
		TyCollection tyCollection = new TyCollection();
//		 tyCollection.colletion("/list-333-1.shtml");
//		tyCollection.colletion("/list-culture-1.shtml");
		tyCollection.colletion("/list-1089-1.shtml");
	}

}
