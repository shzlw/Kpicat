
drop DATABASE kpicat;

CREATE DATABASE kpicat;

use kpicat;

CREATE TABLE USER (
	user_id varchar(32) NOT NULL,
    username varchar(32) NOT NULL,
    password varchar(64) NOT NULL,
    email varchar(256),
    corp_name varchar(32) NOT NULL,
    role_id varchar(32),
    sys_role varchar(16) NOT NULL,
    account_key varchar(32) NOT NULL,
    api_key varchar(32),
    mobile_key varchar(32),
    session_key varchar(32),
    membership varchar(16),
    reset_pass_code varchar(32),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key(user_id),
    UNIQUE KEY user_unique (username, account_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE USER_ROLE (
    role_id varchar(32) NOT NULL,
    account_key varchar(32) NOT NULL,
    name varchar(32) NOT NULL,
    primary key(role_id),
    UNIQUE KEY user_role_unique (account_key, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE COMPONENT (
    component_id varchar(32) NOT NULL,
    account_key varchar(32) NOT NULL,
    row_id varchar(32) NOT NULL,
    col int NOT NULL,
    type varchar(24),
    last_update varchar(32),
    data text,
    primary key(component_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE PAGE (
    page_id varchar(32) NOT NULL,
    account_key varchar(32) NOT NULL,
    name varchar(32) NOT NULL,
    title_color varchar(16),
    bg_color varchar(16),
    description varchar(256),
    primary key(page_id),
    UNIQUE KEY page_unique (account_key, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE PAGE_ROW (
    row_id varchar(32) NOT NULL,
    page_id varchar(32) NOT NULL,
    sequence int NOT NULL,
    col_num int NOT NULL,
    primary key(row_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE PAGE_ROLE (
    page_id varchar(32) NOT NULL,
    role_id varchar(32) NOT NULL,
    primary key(page_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ACCOUNT_CONFIG (
    account_key varchar(32) NOT NULL,
    page_bg_color varchar(12),
    sidebar_bg_color varchar(12),
    sidebar_font_color varchar(12),
    toolbar_bg_color varchar(12),
    toolbar_font_color varchar(12),
    splash_bg_color varchar(12),
    splash_font_color varchar(12),
    splash_image varchar(64),
    splash_text varchar(64),
    primary key(account_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE API_METRIC (
    account_key varchar(32) NOT NULL,
    current_day date NOT NULL,
    api_count bigint default 0,
    primary key(account_key, current_day)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






