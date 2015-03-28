create table FABRICANTES (ID int auto_increment, DESCRICAO varchar(40) unique not null, primary key(ID));
insert into FABRICANTES (DESCRICAO) values ('CHEVROLET/GM');
insert into FABRICANTES (DESCRICAO) values ('FIAT');
insert into FABRICANTES (DESCRICAO) values ('FORD');
insert into FABRICANTES (DESCRICAO) values ('VOLKSWAGEN');
insert into FABRICANTES (DESCRICAO) values ('RENAULT');

create table TIPOS_VEICULO (ID int auto_increment, DESCRICAO varchar(40) unique not null, primary key(ID));
insert into TIPOS_VEICULO (DESCRICAO) values ('HATCH');
insert into TIPOS_VEICULO (DESCRICAO) values ('SEDAN');
insert into TIPOS_VEICULO (DESCRICAO) values ('PICKUP');
insert into TIPOS_VEICULO (DESCRICAO) values ('UTILITÁRIO');
insert into TIPOS_VEICULO (DESCRICAO) values ('SUV');
insert into TIPOS_VEICULO (DESCRICAO) values ('BUGGY');
insert into TIPOS_VEICULO (DESCRICAO) values ('PERUA');
insert into TIPOS_VEICULO (DESCRICAO) values ('MINIVAN');