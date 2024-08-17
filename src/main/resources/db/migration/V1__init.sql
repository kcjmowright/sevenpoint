create table if not exists company (
    symbol varchar(7) primary key,
    name text not null,
    description text,
    sector text,
    industry text,
    exchange text
);
    
create table if not exists quote (
    id bigserial primary key,
    symbol varchar(7) not null,
    mark timestamp not null,
    open decimal,
    close decimal,
    high decimal,
    low decimal,
    volume bigint,
    adjclose decimal,
    constraint fk_company_symbol
      foreign key(symbol) 
	  references company(symbol)
	  on delete cascade,
    constraint u_quote_mark unique(mark)
);
