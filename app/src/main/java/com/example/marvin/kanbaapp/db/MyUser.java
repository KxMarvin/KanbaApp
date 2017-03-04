package com.example.marvin.kanbaapp.db;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Marvin! on 2017/2/24.
 */

public class MyUser extends BmobUser {
    private BmobFile image;

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
