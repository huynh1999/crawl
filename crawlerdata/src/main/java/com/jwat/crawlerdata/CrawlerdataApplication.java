package com.jwat.crawlerdata;

import com.jwat.crawlerdata.Page.vnexpress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class CrawlerdataApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext context=SpringApplication.run(CrawlerdataApplication.class, args);
//        PostRepository repository=context.getBean(PostRepository.class);
//        System.out.println(repository.getTitle(0));
        vnexpress vnexpress=context.getBean(com.jwat.crawlerdata.Page.vnexpress.class);
        Thread thread1=new Thread(vnexpress);
        thread1.start();
    }

}
