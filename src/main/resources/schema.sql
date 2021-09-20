drop table if exists Taco_Order_Tacos;
drop table if exists Taco_Order;
drop table if exists Taco_Ingredients;
drop table if exists Taco;
drop table if exists Ingredient;

create table if not exists Ingredient
(
    id   varchar(4)  PRIMARY KEY,
    name varchar(25) not null,
    type varchar(10) not null
);
create table if not exists Taco
(
    id        bigserial PRIMARY KEY,
    name      varchar(50) not null,
    createdAt timestamp   not null
);
create table if not exists Taco_Ingredients
(
    taco_id        bigint,
    ingredients_id varchar(4)
);
alter table Taco_Ingredients
    add primary key (taco_id, ingredients_id),
    add foreign key (taco_id) references Taco (id);

alter table Taco_Ingredients
    add foreign key (ingredients_id) references Ingredient (id);

CREATE TABLE if not exists "user"
(
    id           BIGSERIAL PRIMARY KEY ,
    username     VARCHAR(255) NULL,
    password     VARCHAR(255) NULL,
    fullname     VARCHAR(255) NULL,
    street       VARCHAR(255) NULL,
    city         VARCHAR(255) NULL,
    state        VARCHAR(255) NULL,
    zip          VARCHAR(255) NULL,
    phone_number VARCHAR(255) NULL
);

create table if not exists Taco_Order
(
    id             bigserial PRIMARY KEY ,
    deliveryName   varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity   varchar(50) not null,
    deliveryState  varchar(2)  not null,
    deliveryZip    varchar(10) not null,
    ccNumber       varchar(16) not null,
    ccExpiration   varchar(5)  not null,
    ccCVV          varchar(3)  not null,
    placedAt       timestamp   not null,
    user_id        bigint
);
alter table Taco_Order
    add foreign key (user_id) references "user" (id);

create table if not exists Taco_Order_Tacos
(
    order_id bigint,
    tacos_id bigint
);
alter table Taco_Order_Tacos
    add primary key (order_id, tacos_id),
    add foreign key (order_id) references Taco_Order (id),
    add foreign key (tacos_id) references Taco (id);
