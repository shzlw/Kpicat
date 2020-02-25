
CREATE DATABASE kpicat;

CREATE TABLE USER (
    account_key varchar(64) NOT NULL,
    api_key varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE COMPONENT (
    component_id varchar(64) NOT NULL,
    account_key varchar(64) NOT NULL,
    last_update varchar(64),
    data varchar(4096),
    primary key(component_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE API_METRIC (
    account_key varchar(64) NOT NULL,
    current_day date NOT NULL,
    api_count bigint default 0,
    primary key(account_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into USER values('c5746588-cc2d-4013-9816-af42274a4f46', '4dab3394-6629-4081-aef2-0f69bef03ddb');

insert into COMPONENT(component_id, account_key) values('914f2809-10c9-41d9-8433-45cca4dad428', 'c5746588-cc2d-4013-9816-af42274a4f46');


CREATE TABLE API_LOG(
    user_key varchar(64) NOT NULL,
    create_date DATETIME,
    component_id varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






