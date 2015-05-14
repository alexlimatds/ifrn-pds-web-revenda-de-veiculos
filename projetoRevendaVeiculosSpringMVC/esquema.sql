create table USUARIOS (ID int auto_increment, NOME varchar(40) not null, CPF varchar(15), 
	TELEFONE varchar(12), LOGIN varchar(15) not null unique, SENHA varchar(15) not null, 
	ATIVO boolean not null, GERENTE boolean not null, primary key (ID));
insert into USUARIOS (ID, NOME, LOGIN, SENHA, ATIVO, GERENTE) values (1, 'JOSÉ ANTÔNIO', 'barao', 
	'senha', true, true);
insert into USUARIOS (ID, NOME, LOGIN, SENHA, ATIVO, GERENTE) values (2, 'LUCENA', 'lucena', 
	'senha', true, false);

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

create table FORMAS_PAGAMENTO (ID int auto_increment, DESCRICAO varchar(30) not null unique, 
	primary key (ID));
insert into FORMAS_PAGAMENTO (ID, DESCRICAO) values (1, 'ESPÉCIE');
insert into FORMAS_PAGAMENTO (ID, DESCRICAO) values (2, 'CHEQUE');
insert into FORMAS_PAGAMENTO (ID, DESCRICAO) values (3, 'VEÍCULO');
insert into FORMAS_PAGAMENTO (ID, DESCRICAO) values (4, 'DEPÓSITO BANCÁRIO');

create table VEICULOS (ID int auto_increment, ANO int not null, PLACA varchar(10) not null unique, 
	CHASSI varchar(20), CILINDRADAS int, FOTO blob, MIME_TYPE_FOTO varchar(30), ID_MODELO int not null, 
	primary key (ID), foreign key (ID_MODELO) references MODELOS);

create table COMPRAS (ID int auto_increment, DATA date not null, PRECO decimal(14,2) not null, OBS varchar, 
	ID_VEICULO int not null, primary key (ID), foreign key (ID_VEICULO) references VEICULOS);

create table VENDAS (ID int auto_increment, DATA date not null, DESCONTO decimal (14,2) not null, 
	COMISSAO decimal (4,2) not null, OBS varchar, STATUS int not null, ID_VEICULO int not null, 
	ID_VENDEDOR int not null, ID_AUTORIZADOR int, primary key (ID), foreign key (ID_VEICULO) 
	references VEICULOS, foreign key (ID_VENDEDOR) references USUARIOS, foreign key 
	(ID_AUTORIZADOR) references USUARIOS);

create table PARTES_PAGAMENTO (ID int auto_increment, QUANTIA decimal (14,2) not null, 
	ID_FORMA_PAGAMENTO int not null, ID_VENDA int not null, primary key (ID), foreign key (ID_VENDA) 
	references VENDAS, foreign key (ID_FORMA_PAGAMENTO) references FORMAS_PAGAMENTO);

--Veículos apenas cadastrados
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (1, 2010, 'MNT1020', 1000, 5); --Palio
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (2, 2014, 'MSX5016', 1600, 3); --Gol

--Veículo comprado e vendido
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (3, 2009, 'NOP0120', 1000, 5); --Palio
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (1, '2013-01-10', 16500.00, 3);
insert into VENDAS (ID, DATA, DESCONTO, COMISSAO, STATUS, ID_VEICULO, ID_VENDEDOR, ID_AUTORIZADOR) 
	values (1, '2013-02-21', 0.00, 3.0, 3, 3, 2, 1);
insert into PARTES_PAGAMENTO (ID, QUANTIA, ID_FORMA_PAGAMENTO, ID_VENDA) values 
	(1, 20000, 2, 1);

--Veículo apenas comprado
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (4, 2012, 'MXR1611', 1600, 3); --Gol
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (2, '2013-02-05', 19000.00, 4);

--Veículo apenas comprado
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (5, 2010, 'MNT0123', 1000, 1); --Corsa
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (3, '2013-03-14', 11000.00, 5);

--Veículo comprado, vendido, comprado e vendido
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (6, 2011, 'NMN9911', 1000, 3); --Gol
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (4, '2013-03-18', 13500.00, 6);
insert into VENDAS (ID, DATA, DESCONTO, COMISSAO, STATUS, ID_VEICULO, ID_VENDEDOR, ID_AUTORIZADOR) 
	values (2, '2013-04-21', 0.00, 3.0, 3, 6, 2, 1);
insert into PARTES_PAGAMENTO (ID, QUANTIA, ID_FORMA_PAGAMENTO, ID_VENDA) values 
	(2, 17500.00, 2, 2);
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (5, '2014-01-05', 12000.00, 6);
insert into VENDAS (ID, DATA, DESCONTO, COMISSAO, STATUS, ID_VEICULO, ID_VENDEDOR, ID_AUTORIZADOR) 
	values (3, '2014-05-19', 0.00, 3.0, 3, 6, 2, 1);

--Veículo comprado e vendido
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (7, 2008, 'NNM1209', 1600, 3); --Gol
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (6, '2013-06-09', 15600.00, 7);
insert into VENDAS (ID, DATA, DESCONTO, COMISSAO, STATUS, ID_VEICULO, ID_VENDEDOR, ID_AUTORIZADOR) 
	values (4, '2013-09-25', 0.00, 3.0, 3, 6, 1, 1);
insert into PARTES_PAGAMENTO (ID, QUANTIA, ID_FORMA_PAGAMENTO, ID_VENDA) values 
	(3, 3000.00, 1, 4);
insert into PARTES_PAGAMENTO (ID, QUANTIA, ID_FORMA_PAGAMENTO, ID_VENDA) values 
	(4, 14500.00, 2, 4);

--Veículo vendido comprado e em processo de venda (venda autorizada)
insert into VEICULOS (ID, ANO, PLACA, CILINDRADAS, ID_MODELO) values (8, 2011, 'XMM9066', 1000, 1); --Corsa
insert into COMPRAS (ID, DATA, PRECO, ID_VEICULO) values (7, '2013-02-02', 14300.00, 8);
insert into VENDAS (ID, DATA, DESCONTO, COMISSAO, STATUS, ID_VEICULO, ID_VENDEDOR, ID_AUTORIZADOR) 
	values (5, '2013-04-17', 0.00, 3.0, 2, 7, 2, 1);
insert into PARTES_PAGAMENTO (ID, QUANTIA, ID_FORMA_PAGAMENTO, ID_VENDA) values 
	(5, 17500.00, 1, 5);
