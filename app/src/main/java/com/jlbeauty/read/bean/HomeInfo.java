package com.jlbeauty.read.bean;

import java.util.List;

/**
 * Created by geek on 2016/8/2.
 */
public class HomeInfo {
    public String type;
    public String catalogName;//特别推荐
    public String typeCode;//
    public List<HomeItem> catalogData;//数据集合

    public class HomeItem {
        public int itemId;//  "itemId": 797 , //文章ID
        public String title;//标题
        public String time;//  "time":"2016-7-14 10:37:58",
        public String isRecommend;//   "isRecommend":否.
        public String description;//  "description":"小诸葛金服注册及服务协议",
        public String author;// "author":"小诸葛金服",
        public String backupCoverUrl;// "backupCoverUrl":"upload/image/91dcc995-0c80-4281-88c3-4d57400f7838.png",
        public String coverUrl;//  "coverUrl":"upload/image/5ebe2085-6669-4321-a5b4-38a00d1c8ea4.png",
        public String content;//  "content":"</span></p><p><br/></p>",
        public String redirectUrl;//   "redirectUrl":null
    }

}
