public class testNewsCrawler extends BreadthCrawler {


    public testNewsCrawler(String crawlPath, boolean autoParse) {
    super(crawlPath, autoParse);
    /*start page*/
    this.addSeed("http://news.hfut.edu.cn");

    /*fetch url like http://news.hfut.edu.cn/show-xxxxxxhtml*/
    this.addRegex("http://news.hfut.edu.cn/.*html");
    /*do not fetch jpg|png|gif*/
    this.addRegex("-.*\\.(jpg|png|gif).*");
    /*do not fetch url contains #*/
    this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, Links next) 
    {
	    String url = page.getUrl();
	    /*if page is news page*/
	    if (Pattern.matches("http://news.hfut.edu.cn/.*html", url))
	    {
	        /*we use jsoup to parse page*/
	        Document doc = page.getDoc();
	
	        /*extract title and content of news by css selector*/
	        String title =doc.title();
	        String date=null;
	        try {
				date=ContentExtractor.getNewsByDoc(doc).getTime();
			} catch (Exception e) {
				e.printStackTrace();
			}
	       
	
	        System.out.println("\nURL:" + url);
	        System.out.println("\ntitle:" + title);
	        System.out.println("\ntime:" + date);
	
	 
	    }
    }

    public static void main(String[] args) throws Exception {
    	testNewsCrawler crawler = new testNewsCrawler("testNews", true);
	    crawler.setThreads(50);
	    crawler.setTopN(5000);
	    crawler.setResumable(true);
	    crawler.addForcedSeed("http://news.hfut.edu.cn");
	    /*start crawl with depth of 4*/
	    crawler.start(5);
    }

	

}
