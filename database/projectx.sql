create database projectx;
use projectx;

create table person 
(
	id_person INT auto_increment,
    name varchar(45) not null,
    surname varchar(45) not null,
    login varchar(45) not null,
    password varchar(45) not null,
    lastdatelogin DATE,
    is_online INT,
	primary key(id_person)
);

create table geolocations
(
	id_geolocation INT auto_increment,
	geolocation geometry not null,
    date_geolocation date not null,
    id_person INT,
    primary key(id_geolocation),
    FOREIGN KEY (id_person) REFERENCES person (id_person)
);

create table messages
(
	id_message INT auto_increment,
    message VARCHAR(1024) not null,
    date_message DATE not null,
    is_read INT not null,
    id_from INT,
    id_to INT,
    primary key(id_message),
    FOREIGN KEY (id_from) REFERENCES person (id_person),
    FOREIGN KEY (id_to) REFERENCES person (id_person)
);