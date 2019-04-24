create table leo_travel_system_city
(
  id            varchar(255)             not null
    primary key,
  name          varchar(255)             not null,
  province_id   varchar(255)             not null,
  introduce     varchar(2000)            not null,
  cover         varchar(255)             null,
  grade         double(4, 1) default 0.0 null,
  commend_count int          default 0   null comment '评论数',
  favour_count  int          default 0   null comment '点赞数',
  like_count    int          default 0   null comment '想去数',
  grade_count   int          default 0   null comment '评分人数',
  gone_count    int          default 0   null comment '去过人数'
);

create table leo_travel_system_comment
(
  id                varchar(255)                        not null
    primary key,
  user_id           varchar(255)                        not null,
  city_id           varchar(255)                        not null,
  content           varchar(2000)                       not null,
  to_user_id        varchar(255)                        null,
  favour_count      int       default 0                 null,
  parent_comment_id varchar(255)                        null comment '根评论',
  send_date         timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table leo_travel_system_image
(
  id      varchar(255) not null
    primary key,
  city_id varchar(255) not null,
  user_id varchar(255) not null,
  image1  varchar(255) not null,
  image2  varchar(255) not null,
  image3  varchar(255) not null,
  image4  varchar(255) not null,
  image5  varchar(255) not null
);

create table leo_travel_system_log
(
  id             varchar(255) not null
    primary key,
  user_id        varchar(255) not null,
  login_time     datetime     not null on update CURRENT_TIMESTAMP,
  logout_time    datetime     null on update CURRENT_TIMESTAMP,
  login_ip       varchar(255) not null,
  login_city     varchar(255) null,
  login_province varchar(255) null,
  login_country  varchar(255) null
);

create table leo_travel_system_other
(
  id      varchar(255)             not null
    primary key,
  name    varchar(255) default null,
  user_id varchar(255)             not null,
  city_id varchar(255)             not null,
  type    int                      not null,
  grade   double(3, 1) default 0.0 null
);

create table leo_travel_system_province
(
  id         varchar(255)         not null
    primary key,
  name       varchar(255)         not null,
  grade      double(11) default 0 null,
  city_count int        default 0 null,
  introduce  varchar(2000)        not null,
  cover      varchar(255)         null,
  reason     varchar(100)         null
);

create table leo_travel_system_recommend
(
  id            varchar(255)  not null
    primary key,
  user_id       varchar(255)  not null,
  city_name     varchar(255)  not null,
  city_image    varchar(255)  not null,
  reason        varchar(2000) not null,
  province_name varchar(255)  not null
);

create table leo_travel_system_search
(
  id      varchar(255) not null
    primary key,
  content varchar(255) not null
);

create table leo_travel_system_user
(
  id              varchar(255)         not null
    primary key,
  username        varchar(255)         not null,
  password        varchar(255)         not null,
  nickname        varchar(255)         not null,
  avatar          varchar(255)         null,
  title_name      varchar(255)         null comment '用户称号',
  follow_count    int        default 0 null,
  recommend_count int        default 0 null,
  commend_count   int        default 0 null,
  open_id         varchar(255)         null,
  level           int        default 0 null,
  deleted         tinyint(1) default 0 null comment '是否删除该用户',
  add_time        datetime             not null on update CURRENT_TIMESTAMP,
  update_time     datetime             null on update CURRENT_TIMESTAMP,
  mobile          varchar(255)         null,
  gender          tinyint              not null
);

create table leo_travel_system_user_city_rel
(
  id        varchar(255)         not null
    primary key,
  user_id   varchar(255)         not null,
  city_id   varchar(255)         not null,
  grade     double     default 0 null,
  is_favour tinyint(1) default 0 null,
  is_like   tinyint(1) default 0 null,
  is_gone   tinyint(1) default 0 null comment '是否去过'
);

create table leo_travel_system_user_comment_rel
(
  id                varchar(255)                           not null
    primary key,
  user_id           varchar(255)                           not null,
  favour_comment_id varchar(255) default ''                not null,
  favour_date       datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table leo_travel_system_user_rel
(
  id                varchar(255)                       not null
    primary key,
  user_id           varchar(255)                       not null,
  attention_user_id varchar(255)                       not null comment '关注的用户id',
  attention_date    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '关注日期'
);

