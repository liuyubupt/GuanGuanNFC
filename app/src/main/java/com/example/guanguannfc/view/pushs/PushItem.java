package com.example.guanguannfc.view.pushs;

public class PushItem {

    private String title,author,content,pinlun;
    private int imageId;

    public PushItem(String[] array, int s){
        this.title=array[1];
        this.author=array[0];
        this.content=array[2];
        this.pinlun=array[3];
        this.imageId=s;
        //this.imageId=Integer.parseInt(array[3]);
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getContent(){
        return content;
    }
    public int getImageId(){ return imageId; }
    public String getPinlun(){return pinlun;}
}
