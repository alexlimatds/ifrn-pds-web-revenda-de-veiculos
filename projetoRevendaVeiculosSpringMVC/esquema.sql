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

create table VEICULOS (ID int auto_increment, ANO int not null, PLACA varchar(10) not null unique, 
	CHASSI varchar(20), CILINDRADAS int, FOTO blob, MIME_TYPE_FOTO varchar(30), ID_MODELO int not null, 
	primary key (ID), foreign key (ID_MODELO) references MODELOS);
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (1, 2010, 'MNT1020', 1000, 5);
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (2, 2014, 'MSX5016', 1600, 3);
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (3, 2009, 'NOP0120', 1000, 5);
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (4, 2012, 'MXR1611', 1600, 3);

create table COMPRAS (ID int auto_increment, DATA date not null, PRECO decimal(14,2) not null, OBS varchar, 
	ID_VEICULO int not null, primary key (ID), foreign key (ID_VEICULO) references VEICULOS);
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (1, '2013-01-10', 16500.00, 3);
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (2, '2013-02-05', 19000.00, 4);

--TODO: colunas vendedor e autorizador
create table VENDAS (ID int auto_increment, DATA date not null, DESCONTO decimal (14,2) not null, 
	COMISSAO decimal (4,2) not null, OBS varchar, STATUS int not null, ID_VEICULO int not null);
