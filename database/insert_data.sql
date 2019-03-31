set search_path to news;

insert into users(username,password,fullname,status)
values('admin','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','admin',1);
insert into users(username,password,fullname,status)
values('nguyenvana','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','nguyen van a',1);

insert into new(code,thumbnail,title,content,shortDescription)
values('BAIVIETTHETHAO1','test','Bán kết world cup 1','Bán kết world cup 1','Bán kết world cup 1');
insert into new(code,thumbnail,title,content,shortDescription)
values('BAIVIETTHETHAO2','test2','Bán kết world cup 2','Bán kết world cup 2','Bán kết world cup 2');