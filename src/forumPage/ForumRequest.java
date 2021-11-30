package forumPage;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.Comment;
import db.Post;
import utils.Request;

import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ForumRequest {
    public static Post[] getPosts(){
        var request = new Request<Void, Post[]>("/api/post?interest_id=" + ForumPage.interest);
        return request.GET(Post[].class);
    }

    public static Post addPost(Post newPost){
        var request = new Request<Post, Post>("/api/user/post", newPost);
        return request.POST(Post.class);
    }

    public static Post getPost(String post_id){
        var request = new Request<Void, Post>("/api/post/"+post_id);
        return request.GET(Post.class);
    }

    public static Post modifyPost(Post newPost){
        var request = new Request<Post, Post>("/api/post/" + newPost.getId(), newPost);
        return request.PUT(Post.class);

    }

    public static Post deletePost(Post newPost){
        var request = new Request<Post, Post>("/api/post/" + newPost.getId());
        return request.PUT(Post.class);

    }

    public static Comment deleteComment(Long comment_id){
        var request = new Request<Void, Comment>("/api/user/comment/" + comment_id);
        return request.DELETE(Comment.class);

    }
    public static Boolean checkOwner(String post_id){
        var request = new Request<Void, Boolean>("/api/post/"+post_id);
        return request.GET(Boolean.class);
    }

    public static Comment[] getComments(String post_id){
        var request = new Request<Void,Comment[]>("/api/post/" + post_id + "/comment");
        return request.GET(Comment[].class);
    }

    public static Comment addComment(String post_id, Comment comment){
        var request = new Request<Comment,Comment>("/api/user/comment?post_id=" + post_id, comment);
        return request.POST(Comment.class);
    }

    public static Boolean checkOwner(Long comment_id){
        var request = new Request<Void, Boolean>("/api/comment/"+comment_id);
        return request.GET(Boolean.class);
    }
}
