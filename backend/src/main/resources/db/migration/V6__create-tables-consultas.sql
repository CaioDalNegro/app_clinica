create table consulta (
    id bigint not null auto_increment,
    id_paciente bigint not null,
    id_medico bigint not null,
    data_hora_consulta datetime not null,

    primary key (id)
);
