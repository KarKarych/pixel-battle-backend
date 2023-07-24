create extension if not exists "uuid-ossp";

create table if not exists users
(
  id               uuid primary key        default uuid_generate_v4(),
  login            varchar unique not null,
  email            varchar unique,
  password_hash    varchar        not null,
  captured_cells   int            not null default 0
);

create table if not exists user_authorities
(
  id        uuid primary key default uuid_generate_v4(),
  user_id   uuid    not null references users (id),
  authority varchar not null
);

create table if not exists deactivated_tokens
(
  id         uuid primary key default uuid_generate_v4(),
  expires_at timestamp not null check ( expires_at > current_timestamp )
);

create table if not exists fields
(
  row_coordinate    int        not null not null,
  column_coordinate int        not null not null,
  color             varchar(7) not null not null,
  owner_id          uuid,
  foreign key (owner_id) references users (id),
  primary key (row_coordinate, column_coordinate)
);

create table if not exists teams
(
  id             uuid primary key default uuid_generate_v4(),
  name           varchar                    not null,
  captured_cells int              default 0 not null
);

create table if not exists teams_users
(
    team_id uuid not null,
    user_id uuid not null,
    primary key (team_id, user_id),
    foreign key (team_id) references teams (id),
    foreign key (user_id) references users (id)
);

create or replace function update_teams_captured_cells_after_update_users()
    returns trigger as
$$
begin
    update teams t
    set captured_cells = captured_cells + (new.captured_cells - old.captured_cells)
    from teams_users tu
    where tu.user_id = new.id
      and tu.team_id = t.id;
    return new;
end;
$$ language plpgsql;
create or replace trigger update_teams_captured_cells_after_update_users
    after update
    on users
    for each row
    when ( old.captured_cells != new.captured_cells )
execute procedure update_teams_captured_cells_after_update_users();

create or replace function set_captured_cells_after_insert_or_delete_into_teams_users()
    returns trigger as
$$
declare
begin
    if tg_op = 'INSERT' then
        update teams t
        set captured_cells = captured_cells + (select u.captured_cells
                                               from users u
                                               where u.id = new.user_id)
        where t.id = new.team_id;
    elsif tg_op = 'DELETE' then
        update teams t
        set captured_cells = captured_cells - (select u.captured_cells
                                               from users u
                                               where u.id = old.user_id)
        where t.id = old.team_id;
    end if;
    return new;
end;
$$ language plpgsql;
create or replace trigger set_captured_cells_after_insert_or_delete_into_teams_users
    after insert or delete
    on teams_users
    for each row
execute procedure set_captured_cells_after_insert_or_delete_into_teams_users();