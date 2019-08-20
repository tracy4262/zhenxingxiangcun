package com.ovit.nswy.relationship.model;

/**
 * 聊天消息数据
 * 
 * @author huangcheng
 * @since 2017-07-17
 */
public class ChatMessage {

	/** 消息来源用户名(如："纸飞机") */
	private String username;

	/** 消息来源用户头像(如："http://tp1.sinaimg.cn/1571889140/180/40030060651/1") */
	private String avatar;

	/** 消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）(如："100000") */
	private String id;

	/** 聊天窗口来源类型，从发送消息传递的to里面获取(如："friend") */
	private String type;

	/** 消息内容(如："嗨，你好！本消息系离线消息。") */
	private String content;

	/** 消息id，可不传。除非你要对消息进行一些操作（如撤回）(如："0") */
	private int cid;

	/** 是否我发送的消息，如果为true，则会显示在右方(如："false") */
	private boolean mine;
	//private String name;
	/** 消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题(如："100000") */
	private String fromid;

	/** 服务端动态时间戳 (如："1467475443306") */
	private long timestamp;

	public ChatMessage() {
		super();
	}

	public ChatMessage(String username, String avatar, String id, String type, String content, int cid, boolean mine,
					   /*String name,*/String fromid, long timestamp) {
		super();
		this.username = username;
		this.avatar = avatar;
		this.id = id;
		this.type = type;
		this.content = content;
		this.cid = cid;
		this.mine = mine;
		//this.name=name;
		this.fromid = fromid;
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public String getFromid() {
		return fromid;
	}

	public void setFromid(String fromid) {
		this.fromid = fromid;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
