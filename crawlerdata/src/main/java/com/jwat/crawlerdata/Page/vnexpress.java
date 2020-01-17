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
public class vnexpress implements Runnable {
    @Autowired
    PostRepository repository;
    List<String>titleSport;
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
        document.select("span.location-stamp").remove();
        Elements elements=document.select("img[data-original]");
        for (Element e:elements
        ) {
            e.attr("src",e.attr("data-original"));
        }
        post.setTitle(document.select("h1").text());
        String desc=document.select("section.sidebar_1").select("*.description").text();
        if(desc.equals(""))desc=document.select("h2.short_intro").text();
        if(desc.equals(""))desc=document.select("h2").text();
        post.setShortdescription(desc);
        post.setContent(document.select("article.fck_detail").toString());
        if(post.getContent().equals(""))post.setContent(document.select("div.fck_detail").toString());
        return post;
    }
    public void getSportpost() throws IOException {
        Document document=Jsoup.connect("https://vnexpress.net/the-thao").get();
        Elements elements=document.select("article.list_news").select("div.thumb_art");
        List<News>posts=new ArrayList<>();
        for (Element e:elements
             ) {
            String url=e.select("a").attr("href");
            News post=getPost(url);
            post.setCategoryid(0);
            post.setThumbnail(e.select("a").select("img").attr("data-original"));
            if(post.getThumbnail().equals(""))post.setThumbnail(e.select("a").select("img").attr("src"));
            if(checkTitle(post))
                repository.AddPost(post);
            else System.out.println("Trùng title");
        }
    }
    public void getEntertainmentpost() throws IOException {
        Document document=Jsoup.connect("https://vnexpress.net/giai-tri").get();
        Elements elements=document.select("article.list_news").select("div.thumb_art");
        List<News>posts=new ArrayList<>();
        for (Element e:elements
        ) {
            String url=e.select("a").attr("href");
            News post=getPost(url);
            post.setCategoryid(1);
            post.setThumbnail(e.select("a").select("img").attr("data-original"));
            if(post.getThumbnail().equals(""))post.setThumbnail(e.select("a").select("img").attr("src"));
            if(checkTitle(post))
                repository.AddPost(post);
            else System.out.println("Trùng title");
        }

    }
    public void getEducationpost() throws IOException {
        Document document=Jsoup.connect("https://vnexpress.net/giao-duc").get();
        Elements elements=document.select("article.list_news").select("div.thumb_art");
        for (Element e:elements
        ) {
            String url=e.select("a").attr("href");
            News post=getPost(url);
            post.setCategoryid(2);
            post.setThumbnail(e.select("a").select("img").attr("data-original"));
            if(post.getThumbnail().equals(""))post.setThumbnail(e.select("a").select("img").attr("src"));
            if(checkTitle(post))
            repository.AddPost(post);
            else System.out.println("Trùng title");
        }

    }
    public void getData() throws IOException {
        getTitle();
        System.out.println("Sport is crawler");
        getSportpost();
        System.out.println("Entertainment is crawler");
        getEntertainmentpost();
        System.out.println("Education is crawler");
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
