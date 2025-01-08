create table user
(
    user_id       int auto_increment comment '主键，自增用户唯一ID'
        primary key,
    username      varchar(50)                                      not null comment '用户名，唯一，用于登录',
    email         varchar(100)                                     not null comment '邮箱，唯一，用于登录和用户通知',
    password_hash varchar(255)                                     not null comment '密码的哈希值，用于安全登录',
    phone_number  varchar(15)                                      null comment '手机号码，用于验证或通知',
    profile_image varchar(255)                                     null comment '用户头像的URL路径',
    first_name    varchar(50)                                      null comment '用户名（名）',
    last_name     varchar(50)                                      null comment '用户名（姓）',
    date_of_birth date                                             null comment '生日',
    gender        enum ('MALE', 'FEMALE', 'OTHER')                 null comment '性别',
    address       varchar(255)                                     null comment '用户地址，用于送货或联系',
    city          varchar(50)                                      null comment '用户所在城市',
    country       varchar(50)                                      null comment '用户所在国家',
    postal_code   varchar(10)                                      null comment '邮编',
    created_at    timestamp              default CURRENT_TIMESTAMP null comment '账户创建时间',
    updated_at    timestamp              default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '账户最近一次更新时间',
    last_login    timestamp                                        null comment '最近登录时间，用于活动监控',
    is_active     tinyint(1)             default 1                 null comment '账户是否激活',
    is_verified   tinyint(1)             default 0                 null comment '账户是否通过邮箱或手机验证',
    user_role     enum ('ADMIN', 'USER') default 'USER'            null comment '角色：管理员、普通用户、版主',
    preferences   json                                             null comment '用户偏好设置（如语言、通知设置等）',
    bio           text                                             null comment '用户简介',
    is_deleted    tinyint(1)             default 0                 null comment '账户是否删除',
    constraint email
        unique (email),
    constraint username
        unique (username)
)
    comment '用户信息表';

create table community_interaction
(
    interaction_id int auto_increment comment '主键，自增互动ID'
        primary key,
    user_id        int                                  not null comment '用户ID，关联user表',
    content        text                                 not null comment '互动内容',
    type           enum ('POST', 'COMMENT', 'LIKE')     not null comment '互动类型：帖子、评论、点赞',
    target_id      int                                  null comment '目标ID（如帖子ID或评论ID）',
    created_at     timestamp  default CURRENT_TIMESTAMP null comment '互动创建时间',
    updated_at     timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '互动最近一次更新时间',
    is_deleted     tinyint(1) default 0                 null comment '互动是否删除',
    likes_count    int        default 0                 null,
    constraint community_interaction_ibfk_1
        foreign key (user_id) references user (user_id)
)
    comment '社区互动表，记录用户在社区的互动内容';

create index user_id
    on community_interaction (user_id);

create table download_history
(
    download_id   int auto_increment comment '主键，自增下载记录ID'
        primary key,
    user_id       int                                  not null comment '下载用户ID，关联user表',
    download_time timestamp  default CURRENT_TIMESTAMP null comment '下载时间',
    is_deleted    tinyint(1) default 0                 null comment '下载记录是否删除',
    source_url    varchar(255)                         null,
    constraint download_history_ibfk_1
        foreign key (user_id) references user (user_id)
)
    comment '下载记录表，记录用户的文件下载历史';

create index user_id
    on download_history (user_id);

create table file
(
    file_id     int auto_increment comment '主键，自增文件唯一ID'
        primary key,
    user_id     int                                  not null comment '上传用户ID，关联user表',
    file_name   varchar(255)                         not null comment '文件名',
    file_type   enum ('PDF', 'DOC', 'DOCX', 'OTHER') not null comment '文件类型',
    file_url    varchar(255)                         not null comment '文件存储的URL',
    upload_time timestamp  default CURRENT_TIMESTAMP null comment '文件上传时间',
    is_public   tinyint(1) default 0                 null comment '文件是否公开',
    keywords    json                                 null comment '文件关键词，供搜索使用',
    created_at  timestamp  default CURRENT_TIMESTAMP null comment '文件创建时间',
    updated_at  timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '文件最近一次更新时间',
    is_deleted  tinyint(1) default 0                 null comment '文件是否删除',
    constraint file_ibfk_1
        foreign key (user_id) references user (user_id)
)
    comment '文件管理表，存储用户上传文件的信息';

create index user_id
    on file (user_id);

create table question_bank
(
    bank_id     int auto_increment comment '主键，自增题库唯一ID'
        primary key,
    user_id     int                                  not null comment '上传用户ID，关联user表',
    title       varchar(255)                         not null comment '题库标题',
    description text                                 null comment '题库描述',
    is_public   tinyint(1) default 1                 null comment '题库是否公开',
    keywords    json                                 null comment '题库的关键词，供搜索使用',
    created_at  timestamp  default CURRENT_TIMESTAMP null comment '题库创建时间',
    updated_at  timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '题库最近一次更新时间',
    is_deleted  tinyint(1) default 0                 null comment '题库是否删除',
    cover_url   varchar(255)                         null,
    constraint question_bank_ibfk_1
        foreign key (user_id) references user (user_id)
)
    comment '题库信息表，存储题库信息';

create table question
(
    question_id    int auto_increment comment '主键，自增题目唯一ID'
        primary key,
    bank_id        int                                                                            not null comment '题库ID，关联question_bank表',
    question_text  text                                                                           not null comment '题目内容',
    question_type  enum ('SINGLE_CHOICE', 'MULTIPLE_CHOICE', 'FILL_IN_THE_BLANK', 'SHORT_ANSWER') not null comment '题目类型',
    options        json                                                                           null comment '选择题的选项，以JSON格式存储',
    correct_answer json                                                                           null comment '正确答案，以JSON格式存储',
    difficulty     enum ('EASY', 'MEDIUM', 'HARD')                                                null comment '题目难度',
    created_at     timestamp  default CURRENT_TIMESTAMP                                           null comment '题目创建时间',
    updated_at     timestamp  default CURRENT_TIMESTAMP                                           null on update CURRENT_TIMESTAMP comment '题目最近一次更新时间',
    is_deleted     tinyint(1) default 0                                                           null comment '题目是否删除',
    constraint question_ibfk_1
        foreign key (bank_id) references question_bank (bank_id)
)
    comment '题目表，存储题库中的各题目和答案信息';

create index bank_id
    on question (bank_id);

create index user_id
    on question_bank (user_id);

create table study_report
(
    report_id    int auto_increment comment '主键，自增报告唯一ID'
        primary key,
    user_id      int                                  not null comment '用户ID，关联user表',
    report_data  json                                 not null comment '学习报告内容，以JSON格式存储',
    generated_at timestamp  default CURRENT_TIMESTAMP null comment '报告生成时间',
    is_deleted   tinyint(1) default 0                 null comment '报告是否删除',
    constraint study_report_ibfk_1
        foreign key (user_id) references user (user_id)
)
    comment '学习报告表，存储用户的学习统计报告';

create index user_id
    on study_report (user_id);

create table user_question_progress
(
    progress_id    int auto_increment comment '主键，自增进度记录ID'
        primary key,
    user_id        int                                  not null comment '用户ID，关联user表',
    question_id    int                                  not null comment '题目ID，关联question表',
    attempt_number int        default 1                 null comment '第几次尝试',
    is_correct     tinyint(1)                           null comment '用户答题是否正确',
    attempt_time   timestamp  default CURRENT_TIMESTAMP null comment '答题时间',
    user_answer    json                                 null comment '用户的答案，以JSON格式存储',
    is_deleted     tinyint(1) default 0                 null comment '进度记录是否删除',
    constraint user_question_progress_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint user_question_progress_ibfk_2
        foreign key (question_id) references question (question_id)
)
    comment '用户做题记录表，记录用户做题进度和答题信息';

create index question_id
    on user_question_progress (question_id);

create index user_id
    on user_question_progress (user_id);

create table user_wrong_question
(
    wrong_id      int auto_increment comment '主键，自增错题记录ID'
        primary key,
    user_id       int                                                                     not null comment '用户ID，关联user表',
    question_id   int                                                                     not null comment '题目ID，关联question表',
    added_at      timestamp                                     default CURRENT_TIMESTAMP null comment '错题添加时间',
    review_status enum ('NOT_REVIEWED', 'REVIEWED', 'MASTERED') default 'NOT_REVIEWED'    null comment '复习状态',
    is_deleted    tinyint(1)                                    default 0                 null comment '错题记录是否删除',
    constraint user_wrong_question_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint user_wrong_question_ibfk_2
        foreign key (question_id) references question (question_id)
)
    comment '错题本表，记录用户的错题和复习状态';

create index question_id
    on user_wrong_question (question_id);

create index user_id
    on user_wrong_question (user_id);

