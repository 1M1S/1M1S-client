package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;

@Getter
@AllArgsConstructor
public enum Images {
    DefaultBackGround(new ImageIcon("Images\\DefaultBackGround.png")),
    InterestSelectBackGround(new ImageIcon("Images\\SurveyBackGround1.png")),
    SurveyBackGround(new ImageIcon("Images\\SurveyBackGround2.png")),
    JoinFormBackGround(new ImageIcon("Images\\JoinFormBackGround.png")),
    BookMarkImage1(new ImageIcon("Images\\BookMarkImage1.png")),
    BookMarkImage2(new ImageIcon("Images\\BookMarkImage1.png")),
    RollBackButton(new ImageIcon("Images\\RollBackButton.png")),
    CommentAddButton(new ImageIcon("Images\\commentAddButton.png")),
    EmployCategory(new ImageIcon("Images\\employ.png")),
    ExerciseCategory(new ImageIcon("Images\\exercise.png")),
    ProgrammingCategory(new ImageIcon("Images\\programming.png")),
    MyPageBackGround(new ImageIcon("Images\\MyPageBackGround.png")),
    ForumRollbackButton(new ImageIcon("Images\\forumRollbackButton.png")),
    ConsultingBackground(new ImageIcon("Images\\consultingBackground.png")),
    MainPageBackGround(new ImageIcon("Images\\mainBackGround.png")),
    ForumBackGround1(new ImageIcon("Images\\forumBackGround1.png")),
    ForumBackGround2(new ImageIcon("Images\\forumBackGround2.png"));
    private final ImageIcon imageIcon;
}
