package com.ovit.nswy.member.model;

/**
 * 标准
 *
 * @author haoWen
 * @create 2018-03-14 14:34
 **/

public class StandardComment {
    private int commentId;
    private int standardId;
    private String commentAccount;
    private String accountDisplayName;
    private String content;
    private int commentThumbnum;
    private String createTime;
    private int postId;
    private int postNum;
    private String avatar;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }

    public String getCommentAccount() {
        return commentAccount;
    }

    public void setCommentAccount(String commentAccount) {
        this.commentAccount = commentAccount;
    }

    public String getAccountDisplayName() {
        return accountDisplayName;
    }

    public void setAccountDisplayName(String accountDisplayName) {
        this.accountDisplayName = accountDisplayName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentThumbnum() {
        return commentThumbnum;
    }

    public void setCommentThumbnum(int commentThumbnum) {
        this.commentThumbnum = commentThumbnum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "StandardComment{" +
                "commentId=" + commentId +
                ", standardId=" + standardId +
                ", commentAccount='" + commentAccount + '\'' +
                ", accountDisplayName='" + accountDisplayName + '\'' +
                ", content='" + content + '\'' +
                ", commentThumbnum=" + commentThumbnum +
                ", createTime='" + createTime + '\'' +
                ", postId=" + postId +
                ", postNum=" + postNum +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
