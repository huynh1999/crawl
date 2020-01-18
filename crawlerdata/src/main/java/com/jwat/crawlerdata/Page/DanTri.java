package com.jwat.crawlerdata.Page;

import com.jwat.crawlerdata.Entity.News;
import com.jwat.crawlerdata.Repository.PostRepository;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class DanTri implements Runnable {
    @Autowired
    PostRepository repository;
    List<String> titleSport;
    List<String>titleEntertain;
    List<String>titleEducation;
    public void getTitle()
    {
        titleSport=repository.getTitle(0);
        titleEntertain=repository.getTitle(1);
        titleEducation=repository.getTitle(2);
    }
    boolean checkTitle(News post)
    {
        if(post.getCategoryid()==0){
            if(titleSport.contains(post.getTitle()))return false;
        }
        if(post.getCategoryid()==1){
            if(titleEntertain.contains(post.getTitle()))return false;
        }
        if(post.getCategoryid()==2){
            if(titleEducation.contains(post.getTitle()))return false;
        }
        return true;
    }
    public News getPost(String url) throws IOException {
        News post=new News();
        Document document= Jsoup.connect(url).get();
        post.setContent(document.select("div[id=divNewsContent]").toString());
        if(post.getContent().equals(""))post.setContent(document.select("div.detail-content").toString());
        document.select("h2").select("span").remove();
        document.select("h2").select("a").remove();
        post.setShortdescription(document.select("h2").text());
        post.setTitle(document.select("h1").text());
        return post;
    }
    public void getSportpost() throws IOException {
        Document document = Jsoup.connect("https://dantri.com.vn/the-thao.htm").get();
        Elements elements=document.select("div[data-boxtype*=position]");
        for (Element e:elements
        ) {
            String url= e.select("a").attr("href");
            if(!url.contains("http"))url="https://dantri.com.vn"+url;
            News post=getPost(url);
            post.setThumbnail(e.selectFirst("a").select("img").attr("src"));
            post.setCategoryid(0);
            if(checkTitle(post))
                repository.AddPost(post);
            else System.out.println("Trùng title");
        }
    }
    public void getEntertainmentpost() throws IOException {
        Document document = Jsoup.connect("https://dantri.com.vn/giai-tri.htm").get();
        Elements elements=document.select("div[data-boxtype*=position]");
        for (Element e:elements
        ) {
            String url= e.select("a").attr("href");
            if(!url.contains("http"))url="https://dantri.com.vn"+url;
            News post=getPost(url);
            post.setThumbnail(e.selectFirst("a").select("img").attr("src"));
            post.setCategoryid(1);
            if(checkTitle(post))
                repository.AddPost(post);
            else System.out.println("Trùng title");
        }
    }
    public void getEducationpost() throws IOException {
        Document document = Jsoup.connect("https://dantri.com.vn/giao-duc-khuyen-hoc.htm").get();
        Elements elements=document.select("div[data-boxtype*=position]");
        for (Element e:elements
        ) {
            String url= e.select("a").attr("href");
            if(!url.contains("http"))url="https://dantri.com.vn"+url;
            News post=getPost(url);
            post.setThumbnail(e.selectFirst("a").select("img").attr("src"));
            post.setCategoryid(2);
            if(checkTitle(post))
                repository.AddPost(post);
            else System.out.println("Trùng title");
        }
    }
    public void getData() throws IOException {
        getTitle();
        System.out.println("Sport is crawler from Dan tri");
        getSportpost();
        System.out.println("Entertainment is crawler from Dan tri");
        getEntertainmentpost();
        System.out.println("Education is crawler from Dan tri");
        getEducationpost();
    }
    @SneakyThrows
    @Override
    public void run() {
        while (true)
        {
            System.out.println("Thread Start.......");
            getData();
            Thread.sleep(600000);
        }
    }
}
