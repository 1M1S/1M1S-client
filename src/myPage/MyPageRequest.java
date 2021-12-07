package myPage;

import db.Comment;
import db.Member;
import db.MemberInformation;
import db.Post;
import utils.Request;

public class MyPageRequest {
    public static MemberInformation getMe(){
        var request = new Request<Void, MemberInformation>("/auth/me");
        return request.GET(MemberInformation.class);
    }

    public static MemberInformation modifyMe(){
        var request = new Request<MemberInformation,MemberInformation>("/api/user/information", MyPage.me);
        return request.PUT(MemberInformation.class);
    }

    public static Member modifyMyPassword(){
        var request = new Request<Member, Member>("/auth/password");
        return request.PUT(Member.class);
    }

    public static Post[] getMyPosts(){
        var request = new Request<Void, Post[]>("/api/user/post");
        return request.GET(Post[].class);
    }

    public static Comment[] getMyComments(){
        var request = new Request<Void, Comment[]>("/api/user/comment");
        return request.GET(Comment[].class);
    }
}
