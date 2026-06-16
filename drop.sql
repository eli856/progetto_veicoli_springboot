
    set client_min_messages = WARNING;

    alter table if exists bici 
       drop constraint if exists FKiv6w39rnjduocl17c8dhoqoh4;

    alter table if exists bici 
       drop constraint if exists FK728jkilwcanp13x491m4pgc4f;

    alter table if exists bici 
       drop constraint if exists FKra4pq79y6t94qnygjmhto1d2f;

    alter table if exists macchina 
       drop constraint if exists FKlinll7qwn6u0njwsg3l9la5ue;

    alter table if exists moto 
       drop constraint if exists FKt01inn1jct6p6irfuar0pt15d;

    alter table if exists targa 
       drop constraint if exists FK3tdctv8wi569qk18vupvja5va;

    alter table if exists targa 
       drop constraint if exists FKqi8hmjvp97d07m030a351o6pk;

    alter table if exists veicoli 
       drop constraint if exists FKvoxq1l514latwhn6xi35cnm4;

    alter table if exists veicoli 
       drop constraint if exists FK493ennwvu9uona9a1xcl00j6v;

    drop table if exists bici cascade;

    drop table if exists categoria cascade;

    drop table if exists macchina cascade;

    drop table if exists messagi_sistema cascade;

    drop table if exists moto cascade;

    drop table if exists targa cascade;

    drop table if exists tipo_alimentazione cascade;

    drop table if exists tipo_freno cascade;

    drop table if exists tipo_sospensione cascade;

    drop table if exists veicoli cascade;
