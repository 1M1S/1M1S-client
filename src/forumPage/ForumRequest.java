package forumPage;

import db.Comment;
import db.Interest;
import db.Post;
import main.MainFrame;
import utils.Request;


public class ForumRequest {
    public static MainFrame mainFrame;

    public static Post[] getPosts(){
        mainFrame = ForumPage.mainFrame;
        var request = new Request<Void, Post[]>("/api/post?interest_id=" + ForumPage.interest.getId());
        Post[] res = request.GET(Post[].class);
        return res;
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
        System.out.println(newPost.getId());
        var request = new Request<Post, Post>("/api/user/post/" + newPost.getId(), newPost);
        return request.PUT(Post.class);
    }

    public static Post deletePost(Post newPost){
        var request = new Request<Post, Post>("/api/user/post/" + newPost.getId());
        return request.DELETE(Post.class);
    }

    public static Comment deleteComment(Long comment_id){
        var request = new Request<Void, Comment>("/api/user/comment/" + comment_id);
        return request.DELETE(Comment.class);
    }
    public static boolean checkOwner(String post_id){
        var request = new Request<Void, Post>("/api/post/"+post_id);
        return request.GET(Post.class) != null;
    }

    public static Comment[] getComments(String post_id){
        var request = new Request<Void,Comment[]>("/api/post/" + post_id + "/comment");
        return request.GET(Comment[].class);
    }

    public static Comment addComment(String post_id, Comment comment){
        var request = new Request<Comment,Comment>("/api/user/comment?post_id=" + post_id, comment);
        return request.POST(Comment.class);
    }

    public static boolean checkOwner(Long comment_id){
        var request = new Request<Void, Comment>("/api/comment/"+comment_id);
        return request.GET(Comment.class) != null;
    }

    public static Interest getInterest(Long interest_id){
        var request = new Request<Void, Interest>("/api/interest/"+interest_id);
        return request.GET(Interest.class);
    }
}
