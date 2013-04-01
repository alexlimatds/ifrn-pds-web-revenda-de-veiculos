# Para executar a aplicação cliente de acesso ao SGBDR H2, execute o seguinte comando no console do play: h2-browser

# --- !Ups

create table fabricantes (id int identity, descricao varchar(40) not null);
insert into fabricantes (id, descricao) values (1, 'FIAT');
insert into fabricantes (id, descricao) values (2, 'VOLKSWAGEN');
insert into fabricantes (id, descricao) values (3, 'CHEVROLET');
insert into fabricantes (id, descricao) values (4, 'FORD');
insert into fabricantes (id, descricao) values (5, 'RENAULT');
insert into fabricantes (id, descricao) values (6, 'NISSAN');
insert into fabricantes (id, descricao) values (7, 'PEUGEOT');
insert into fabricantes (id, descricao) values (8, 'HONDA');
insert into fabricantes (id, descricao) values (9, 'TOYOTA');
insert into fabricantes (id, descricao) values (10, 'KIA');

create table tipos_veiculo (id int identity, descricao varchar(30) not null);
insert into tipos_veiculo (id, descricao) values (1, 'SEDAN');
insert into tipos_veiculo (id, descricao) values (2, 'HATCH');
insert into tipos_veiculo (id, descricao) values (3, 'PERUA');
insert into tipos_veiculo (id, descricao) values (4, 'JIPE');
insert into tipos_veiculo (id, descricao) values (5, 'BUGGY');
insert into tipos_veiculo (id, descricao) values (6, 'SUV');
insert into tipos_veiculo (id, descricao) values (7, 'FURGÃO');

create table modelos (id int identity, descricao varchar(30) not null, idfabricante int not null, idtipoveiculo int not null, foreign key (idfabricante) references fabricantes, foreign key (idtipoveiculo) references tipos_veiculo);
insert into modelos (id, descricao, idfabricante, idtipoveiculo) values (1, 'GOL', 2, 2);
insert into modelos (id, descricao, idfabricante, idtipoveiculo) values (2, 'KOMBI', 2, 3);
insert into modelos (id, descricao, idfabricante, idtipoveiculo) values (3, 'VOYAGE', 2, 1);
insert into modelos (id, descricao, idfabricante, idtipoveiculo) values (4, 'UNO', 1, 2);
insert into modelos (id, descricao, idfabricante, idtipoveiculo) values (5, 'PALIO', 1, 2);
insert into modelos (id, descricao, idfabricante, idtipoveiculo) values (6, 'SIENA', 1, 1);

create table formas_pagamento (id int identity, descricao varchar(40) not null);
insert into formas_pagamento (id, descricao) values (1, 'EM ESPÉCIE');
insert into formas_pagamento (id, descricao) values (2, 'FINANCIAMENTO');
insert into formas_pagamento (id, descricao) values (3, 'CARTA DE CRÉDITO');
insert into formas_pagamento (id, descricao) values (4, 'VEÍCULO');

create table usuarios (id int identity, nome varchar(40) not null, cpf varchar (11) not null, telefone varchar(20), login varchar(20) not null unique, senha varchar(20) not null, ativo boolean not null, gerente boolean not null);
insert into usuarios (id, nome, cpf, login, senha, ativo, gerente) values (1, 'PAULO', '11122233344', 'paulo', '123', true, true);
insert into usuarios (id, nome, cpf, login, senha, ativo, gerente) values (2, 'SILVA', '00099988877', 'silva', '123', true, false);

create table veiculos (id int identity, anofabricacao int not null, chassi varchar(30), placa varchar(7) not null, foto blob, cilindradas int, idmodelo int not null, foreign key (idmodelo) references modelos);
insert into veiculos (id, anofabricacao, placa, cilindradas, idmodelo) values (1, 2008, 'MMM5555', 1000, 4);
insert into veiculos (id, anofabricacao, placa, cilindradas, idmodelo) values (2, 2010, 'MMM3333', 1000, 1);

create table status_venda (id int not null primary key, descricao varchar(30) not null unique);
insert into status_venda (id, descricao) values (1, 'AGUARDANDO AUTORIZAÇÃO');
insert into status_venda (id, descricao) values (2, 'AUTORIZADA');
insert into status_venda (id, descricao) values (3, 'FINALIZADA');

create table vendas (id int identity, data date not null, desconto decimal(15, 2) not null, comissao decimal(5, 2) not null, obs varchar(300), idstatus int not null, idveiculo int not null, idvendedor int not null, idautorizador int, foreign key (idstatus) references status_venda, foreign key (idveiculo) references veiculos, foreign key (idvendedor) references usuarios, foreign key (idautorizador) references usuarios);

create table compras (id int identity, data date not null, preco decimal(15, 2), obs varchar(300), idveiculo int not null, foreign key (idveiculo) references veiculos);

create table partes_pagamento (id int identity, quantia decimal(15,2) not null, idformapagamento int not null, idvenda int not null, idcompra int, foreign key (idformapagamento) references formas_pagamento, foreign key (idvenda) references vendas, foreign key (idcompra) references compras);

# --- !Downs

DROP TABLE fabricantes;

DROP TABLE tipos_veiculo;

DROP TABLE modelos;

DROP TABLE formas_pagamento;

DROP TABLE usuarios;

DROP TABLE veiculos;

DROP TABLE status_venda;

DROP TABLE status_venda;

DROP TABLE vendas;

DROP TABLE compras;

DROP TABLE compras;