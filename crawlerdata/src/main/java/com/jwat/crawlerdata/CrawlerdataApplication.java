package com.jwat.crawlerdata;

import com.jwat.crawlerdata.Entity.News;
import com.jwat.crawlerdata.Page.DanTri;
import com.jwat.crawlerdata.Page.vnexpress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import sun.awt.windows.ThemeReader;

import javax.xml.bind.util.JAXBSource;
import java.io.IOException;
import java.io.PrintStream;

@SpringBootApplication
public class CrawlerdataApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext context=SpringApplication.run(CrawlerdataApplication.class, args);
        DanTri danTri=context.getBean(DanTri.class);
        Thread thread2=new Thread(danTri);
        thread2.start();


//        vnexpress vnexpress=context.getBean(com.jwat.crawlerdata.Page.vnexpress.class);
//        Thread thread1=new Thread(vnexpress);
//        thread1.start();
    }

}
