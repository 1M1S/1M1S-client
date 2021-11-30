package forumPage;

import db.Post;
import utils.Request;

import javax.swing.table.DefaultTableModel;

public class ForumRequest {
    public static void getPosts(DefaultTableModel dtm, Integer interest){
        dtm.setRowCount(0); //테이블 초기화
        var request = new Request<Void, Post[]>("/api/post?interest_id=" + interest);
        Post[] result = request.GET(Post[].class);
        for(Post p : result){
            dtm.addRow(new Object[] {p.getId(), p.getInterest(), p.getTitle(), p.getContent()});
        }
    }

    //***********************************************************************************************************************************************************************
    //***********************************************************************************************************************************************************************
    //***********************************************************************************************************************************************************************

    //게시판 글 추가 함수
    public static void addPost(DefaultTableModel dtm, Post newPost, Integer interest){
        var request = new Request<Post, Void>("/api/post?interest_id="+interest, newPost);
        request.POST(Void.class);
        getPosts(dtm, interest);
    }

    public static void modifyPost(DefaultTableModel dtm, Post oldPost, Post newPost){
        var request = new Request<Post, Void>("/api/user/post/" + oldPost.getId(), newPost);
        request.PUT(Void.class);
    }
}
