package com.jwat.crawlerdata.Repository;

import com.jwat.crawlerdata.Entity.News;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class PostRepository {
    @PersistenceContext
    EntityManager entityManager;
    public List<String> getTitle(int idtopic)
    {
        List<String> list=entityManager.createQuery("select n.title from news n where n.categoryid=:id")
                .setParameter("id",idtopic)
                .getResultList();
        return list;
    }
    public void AddPost(News post)
    {

        entityManager.createNativeQuery("insert into news(title,thumbnail,shortdescription,content,categoryid) values (:title,:thumbnail,:shortdescription,:content,:categoryid)")
                .setParameter("title",post.getTitle())
                .setParameter("thumbnail",post.getThumbnail())
                .setParameter("shortdescription",post.getShortdescription())
                .setParameter("content",post.getContent())
                .setParameter("categoryid",post.getCategoryid())
                .executeUpdate();
    }
}
