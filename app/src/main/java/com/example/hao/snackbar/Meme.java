package com.example.hao.snackbar;

/**
 * Created by Hao on 2017/12/20.
 */

public class Meme {
    private String name;
    private int imageId;

    public Meme(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

}
