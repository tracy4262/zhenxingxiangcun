CREATE TABLE IF NOT EXISTS  `chat_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `from_account` int(11) DEFAULT NULL COMMENT '发送消息用户ID',
  `to_account` int(11) DEFAULT NULL COMMENT '接收消息用户ID',
  `content` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '消息内容',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `is_send` varchar(1) DEFAULT NULL COMMENT 'Y:已发送（在线消息），N:未发送（离线消息）',
  `chat_type` int(11) DEFAULT NULL COMMENT '消息类型：1 朋友分组，2 群组',
  `from_id` int(11) DEFAULT NULL COMMENT '消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='聊天记录表';