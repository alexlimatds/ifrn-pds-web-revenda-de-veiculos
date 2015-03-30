create table FABRICANTES (ID int auto_increment, DESCRICAO varchar(40) unique not null, primary key(ID));
insert into FABRICANTES (ID, DESCRICAO) values (1, 'CHEVROLET/GM');
insert into FABRICANTES (ID, DESCRICAO) values (2, 'FIAT');
insert into FABRICANTES (ID, DESCRICAO) values (3, 'FORD');
insert into FABRICANTES (ID, DESCRICAO) values (4, 'VOLKSWAGEN');
insert into FABRICANTES (ID, DESCRICAO) values (5, 'RENAULT');

create table TIPOS_VEICULO (ID int auto_increment, DESCRICAO varchar(40) unique not null, primary key(ID));
insert into TIPOS_VEICULO (ID, DESCRICAO) values (1, 'HATCH');
insert into TIPOS_VEICULO (ID, DESCRICAO) values (2, 'SEDAN');
insert into TIPOS_VEICULO (ID, DESCRICAO) values (3, 'PICKUP');
insert into TIPOS_VEICULO (ID, DESCRICAO) values (4, 'UTILITÁRIO');
insert into TIPOS_VEICULO (ID, DESCRICAO) values (5, 'SUV');
insert into TIPOS_VEICULO (ID, DESCRICAO) values (6, 'BUGGY');
insert into TIPOS_VEICULO (ID, DESCRICAO) values (7, 'PERUA');
insert into TIPOS_VEICULO (ID, DESCRICAO) values (8, 'MINIVAN');

create table MODELOS (ID int auto_increment, DESCRICAO varchar(40) not null, ID_FABRICANTE int not null, 
	ID_TIPO_VEICULO int not null, primary key(ID), foreign key (ID_FABRICANTE) references FABRICANTES, 
	foreign key (ID_TIPO_VEICULO) references TIPOS_VEICULO);
insert into MODELOS (ID, DESCRICAO, ID_FABRICANTE, ID_TIPO_VEICULO) values (1, 'CORSA', 1, 1);
insert into MODELOS (ID, DESCRICAO, ID_FABRICANTE, ID_TIPO_VEICULO) values (2, 'CORSA', 1, 2);
insert into MODELOS (ID, DESCRICAO, ID_FABRICANTE, ID_TIPO_VEICULO) values (3, 'GOL', 4, 1);
insert into MODELOS (ID, DESCRICAO, ID_FABRICANTE, ID_TIPO_VEICULO) values (4, 'VOYAGE', 4, 2);
insert into MODELOS (ID, DESCRICAO, ID_FABRICANTE, ID_TIPO_VEICULO) values (5, 'PALIO', 2, 1);
insert into MODELOS (ID, DESCRICAO, ID_FABRICANTE, ID_TIPO_VEICULO) values (6, 'PALIO WEEKEND', 2, 7);
insert into MODELOS (ID, DESCRICAO, ID_FABRICANTE, ID_TIPO_VEICULO) values (7, 'TRACKER', 1, 4);
