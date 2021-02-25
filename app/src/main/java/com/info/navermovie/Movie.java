package com.info.navermovie;

public class Movie {//스프링 dto 이걸 공부해보자.. 스프링 프레임 워크 공부하기+ 크롤링@@@

    String title;
    String link;
    String image;
    String subtitle;
    String pubDate;
    String director;
    String actor;
    float userRating;

    //생성자초기화는 왜? getter, setter?, 저 밑에 public 저건 뭐임..?
    //Movie movie = new Movie(title, link, image, pubDate, director, actor, userRating);


    public Movie(String title, String link, String image, String pubDate, String director, String actor, String userRating) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
        this.userRating = Float.parseFloat(userRating);//문자열을 숫자로
    }
}
