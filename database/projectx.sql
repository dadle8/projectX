create database projectx;
use projectx;

create table user
(
	id int auto_increment,
    login varchar(32) not null,
    password varchar(32) not null,
    email varchar(32),
    loggedIn tinyint(1) not null,
    sessionId varchar(16),
    primary key(id)
);

create table messages
(
	id INT auto_increment,
    message VARCHAR(1024) not null,
    datemessage DATE not null,
    isread tinyint(1) not null,
    idfrom INT not null,
    idto INT not null,
    primary key(id),
    FOREIGN KEY (idfrom) REFERENCES user (id),
    FOREIGN KEY (idto) REFERENCES user (id)
);

create table geolocations
(
	id INT auto_increment,
	geolocationX float(10,6) not null,
    geolocationY float(10,6) not null,
    dategeolocation date not null,
    iduser INT,
    primary key(id),
    FOREIGN KEY (iduser) REFERENCES user (id)
);
