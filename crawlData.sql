/* create database crawlData; */
use crawlData;
create table crawlData.News(
	id int primary key auto_increment,
    title nvarchar(1000),
    urlImage nvarchar(1000),
    link nvarchar(1000),
    category nvarchar(1000),
    times date,
    des nvarchar(1000),
    UNIQUE KEY `title` (`title`)
);

create table crawlData.demuc(
	id int primary key auto_increment,
    name nvarchar(1000),
    link nvarchar(1000),
	UNIQUE KEY `name` (`name`),
	UNIQUE KEY `link` (`link`)
);

create table crawlData.baidang(
	id int primary key auto_increment,
    tieude nvarchar(1000),
    link nvarchar(1000),
    idDemuc int,
 UNIQUE KEY `tieude` (`tieude`),
	FOREIGN KEY (idDemuc) REFERENCES demuc(id)
);

select * from demuc;