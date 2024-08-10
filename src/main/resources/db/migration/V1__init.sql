create table if not exists company (
    ticker varchar(7) primary key,
    name text,
    sector text,
    industry text,
    exchange text
);
    
create table if not exists quote (
    id bigserial primary key,
    ticker varchar(7),
    mark timestamp,
    open decimal,
    close decimal,
    high decimal,
    low decimal,
    volume bigint,
    adjclose decimal,
    constraint fk_company_ticker
      foreign key(ticker) 
	  references company(ticker)
	  on delete cascade
);
