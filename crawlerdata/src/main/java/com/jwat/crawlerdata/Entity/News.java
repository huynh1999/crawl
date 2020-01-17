package com.jwat.crawlerdata.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity(name = "news")
@Table(name = "news")
public class News {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "title")
    String title;
    @Column(name = "thumbnail")
    String thumbnail;
    @Column(name = "shortdescription")
    String shortdescription;
    @Column(name = "content")
    String content;
    @Column(name = "categoryid")
    int categoryid;
    @Column(name = "createddate")
    Date createddate;
    @Column(name = "modifieddate")
    Date modifieddate ;
    @Column(name = "createdby")
    String createdby;
    @Column(name = "modifiedby")
    String modifiedby;
}
