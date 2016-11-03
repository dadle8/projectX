create database projectx;
use projectx;

create table person 
(
	idperson INT auto_increment,
    name varchar(45) not null,
    surname varchar(45) not null,
    login varchar(45) not null,
    password varchar(45) not null,
    lastdatelogin DATE,
    isonline INT,
	primary key(idperson)
);

create table geolocations
(
	idgeolocation INT auto_increment,
	geolocation geometry not null,
    date_geolocation date not null,
    idperson INT,
    primary key(idgeolocation),
    FOREIGN KEY (idperson) REFERENCES person (idperson)
);

create table messages
(
	idmessage INT auto_increment,
    message VARCHAR(1024) not null,
    datemessage DATE not null,
    isread INT not null,
    idfrom INT,
    idto INT,
    primary key(idmessage),
    FOREIGN KEY (idfrom) REFERENCES person (idperson),
    FOREIGN KEY (idto) REFERENCES person (idperson)
);