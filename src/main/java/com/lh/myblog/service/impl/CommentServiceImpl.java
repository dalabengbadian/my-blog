package com.lh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.myblog.bean.Comment;
import com.lh.myblog.mapper.CommentMapper;
import com.lh.myblog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    /**
     * 评论展示功能，需要将多级父子关系转换为二级关系
     * 根据blogId查出所有评论，然后调用下面的方法，得到最顶级的父评论及它们内部封装的子评论
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listByBlogId(Long blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",blogId).orderByDesc("create_time");
        List<Comment> comments = list(wrapper);
        List<Comment> comments1 = addReplyComments(comments);
        return comments1;
    }

    /**
     * 封装所有comment的parentComment和replyComments（这一步也可以在数据库交互时完成，如mybatis的映射mapper文件中使用resultMap）
     * @param comments
     * @return
     */
    public List<Comment> addReplyComments(List<Comment> comments){
        //新增一个集合，只存放顶级父评论
        ArrayList<Comment> comments1 = new ArrayList<>();
        for (Comment comment : comments) {
            //筛选有父评论的评论
            if (comment.getParentCommentId() != null){
                for (Comment comment1 : comments) {
                    if (comment1.getId() == comment.getParentCommentId()){
                        comment.setParentComment(comment1);
                        comment1.getReplyComments().add(comment);
                        break;
                    }
                }
            }else {
                comments1.add(comment);
            }
        }
        eachComments(comments1);
        return comments1;
    }

    //临时集合，用于临时保存需要封装到最顶级父评论的评论
    private List<Comment> tempComments = new ArrayList<>();

    /**
     * 遍历所有顶级父评论，获取他们的子评论集合，调用递归方法，一级一级的将所有子评论封装进临时集合，最后再赋值给顶级父评论
     * @param comments
     */
    public void eachComments(List<Comment> comments){
        for (Comment comment : comments) {
                List<Comment> replyComments = comment.getReplyComments();
                recursively(replyComments);
                comment.setReplyComments(tempComments);
                //清空临时集合
                tempComments = new ArrayList<>();
        }
    }

    /**
     * 递归调用，一层一层的遍历每一个子评论的子评论集合，并放入临时集合中。
     * @param replyComments
     */
    public void recursively(List<Comment> replyComments){
        for (Comment replyComment : replyComments) {
                tempComments.add(replyComment);
            if (replyComment.getReplyComments().size() > 0) {
                List<Comment> replyComments1 = replyComment.getReplyComments();
                recursively(replyComments1);
            }
        }
    }
}
