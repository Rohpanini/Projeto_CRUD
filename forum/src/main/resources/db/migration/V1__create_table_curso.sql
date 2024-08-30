CREATE TABLE curso
(
    id bigint               not null auto_increment,
    nome varchar(50)        not null,
    categoria varchar(50)   not null,
    primary key (id)
);

INSERT INTO curso VALUES (1, 'Kotlin', 'Programação');
INSERT INTO curso VALUES (2, 'HTML', 'Front-end');