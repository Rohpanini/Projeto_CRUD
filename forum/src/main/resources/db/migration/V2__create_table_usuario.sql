CREATE TABLE usuario
(
    id bigint           not null auto_increment,
    nome varchar(50)    not null,
    email varchar(50)   not null,
    primary key(id)
);

INSERT INTO usuario VALUES (1, 'Ana', 'Ana@gmail.com');