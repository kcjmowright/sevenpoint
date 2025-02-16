CREATE TABLE IF NOT EXISTS company
(
    symbol      varchar(7) primary key,
    name        text not null,
    description text,
    sector      text,
    industry    text,
    exchange    text
);

CREATE TABLE IF NOT EXISTS quote
(
    id        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    symbol    varchar(7) not null,
    timestamp timestamp  not null,
    open      decimal,
    close     decimal,
    high      decimal,
    low       decimal,
    volume    bigint,
    adjclose  decimal,
    constraint fk_company_symbol
        foreign key (symbol)
            references company (symbol)
            on delete cascade,
    constraint u_quote_timestamp unique (timestamp)
);

CREATE TYPE assettype AS ENUM ('STOCK', 'CALL_OPTION', 'PUT_OPTION');

CREATE TABLE IF NOT EXISTS asset
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    bought        timestamp,
    sold          timestamp,
    symbol        text      not null,
    purchaseprice decimal,
    sellprice     decimal,
    quantity      decimal   not null,
    type          assettype not null
);

