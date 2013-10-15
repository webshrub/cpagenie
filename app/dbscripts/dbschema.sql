create table CG_AUTHORITY (
  ID integer not null auto_increment,
  AUTHORITY_TYPE varchar(255) not null,
  CREATION_TIME datetime not null,
  UPDATE_COMMENTS longtext,
  UPDATE_TIME datetime,
  primary key (ID)
);

alter table CG_AUTHORITY
  add unique index UIX_CGA_AUTHORITY_TYPE (AUTHORITY_TYPE);

create table CG_CAMPAIGN (
  ID integer not null auto_increment,
  COMPLETION_DATE datetime,
  COST_PER_LEAD double precision not null,
  CREATION_TIME datetime not null,
  DESCRIPTION longtext,
  EMAIL varchar(255) not null,
  END_DATE datetime not null,
  NAME varchar(255) not null,
  START_DATE datetime not null,
  STATUS integer not null,
  TOTAL_BUDGET double precision not null,
  UPDATE_COMMENTS longtext,
  UPDATE_TIME datetime,
  ADVERTISER_ID integer not null,
  RESPONSE_ID integer not null,
  primary key (ID)
);

alter table CG_CAMPAIGN
  add unique index UIX_CGC_NAME_ADVERTISER_ID (NAME, ADVERTISER_ID);

create table CG_CAMPAIGN_DELIVERY (
  ID integer not null auto_increment,
  DELIVERY_TIME datetime not null,
  STATUS integer not null,
  CAMPAIGN_ID integer not null,
  primary key (ID)
);

create table CG_CAMPAIGN_DELIVERY_LEAD (
  CAMPAIGN_DELIVERY_ID integer not null,
  LEAD_ID integer not null,
  primary key (CAMPAIGN_DELIVERY_ID, LEAD_ID)
);

create table CG_CAMPAIGN_FIELD (
  ID integer not null auto_increment,
  DESCRIPTION varchar(255),
  FIELD integer not null,
  FIELD_TYPE integer not null,
  FIELD_VALIDATION_TYPE integer not null,
  PARAMETER varchar(255) not null,
  CAMPAIGN_ID integer not null,
  primary key (ID)
);

alter table CG_CAMPAIGN_FIELD
  add unique index UIX_CGCF_FIELD_CAMPAIGN_ID (FIELD, CAMPAIGN_ID);

create table CG_CAMPAIGN_RESPONSE (
  ID integer not null auto_increment,
  FAILURE_RESPONSE longtext not null,
  RESPONSE_TYPE integer not null,
  SUCCESS_RESPONSE longtext not null,
  primary key (ID)
);

create table CG_IMPRESSION_REPORT (
  ID integer not null auto_increment,
  COST_PER_LEAD double precision not null,
  IMPRESSIONS integer not null,
  LEAD_COUNT integer not null,
  REVENUE double precision not null,
  RUN_TIME datetime not null,
  SUBMIT_COUNT integer not null,
  CAMPAIGN_ID integer not null,
  primary key (ID)
);

alter table CG_IMPRESSION_REPORT
  add unique index UIX_CGIR_RUN_TIME_CAMPAIGN_ID (RUN_TIME, CAMPAIGN_ID);

create table CG_LEAD (
  ID integer not null auto_increment,
  ADDRESS1 varchar(255),
  ADDRESS2 varchar(255),
  CAPTURE_TIME datetime not null,
  CITY varchar(255),
  COUNTRY varchar(255),
  CUSTOM1 longtext,
  CUSTOM10 longtext,
  CUSTOM2 longtext,
  CUSTOM3 longtext,
  CUSTOM4 longtext,
  CUSTOM5 longtext,
  CUSTOM6 longtext,
  CUSTOM7 longtext,
  CUSTOM8 longtext,
  CUSTOM9 longtext,
  DAY_OF_BIRTH integer,
  EMAIL varchar(255),
  FIRST_NAME varchar(255),
  HOME_PHONE varchar(255),
  IP_ADDRESS varchar(255) not null,
  LAST_NAME varchar(255),
  MOBILE_PHONE varchar(255),
  MONTH_OF_BIRTH integer,
  OTHER_PHONE varchar(255),
  PIN_CODE varchar(255),
  STATE varchar(255),
  STATUS integer not null,
  UPDATE_COMMENTS longtext,
  UPDATE_TIME datetime,
  USER_AGENT longtext,
  WORK_EXT varchar(255),
  WORK_PHONE varchar(255),
  YEAR_OF_BIRTH integer,
  CAMPAIGN_ID integer not null,
  SOURCE_ID integer not null,
  primary key (ID)
);

alter table CG_LEAD
  add unique index UIX_CGL_CT_IPA_CID_SID (CAPTURE_TIME, IP_ADDRESS, CAMPAIGN_ID, SOURCE_ID);

create table CG_PROFANE (
  ID integer not null auto_increment,
  PROFANE varchar(255) not null,
  primary key (ID)
);

alter table CG_PROFANE
  add unique index UIX_CGP_PROFANE (PROFANE);

create table CG_ADVERTISER (
  ID integer not null auto_increment,
  CREATION_TIME datetime not null,
  DESCRIPTION longtext,
  EMAIL varchar(255) not null,
  NAME varchar(255) not null,
  UPDATE_COMMENTS longtext,
  UPDATE_TIME datetime,
  VERTICAL_ID integer not null,
  primary key (ID)
);

alter table CG_ADVERTISER
  add unique index UIX_CGA_NAME_VERTICAL_ID (NAME, VERTICAL_ID);

create table CG_SOURCE (
  ID integer not null auto_increment,
  CREATION_TIME datetime not null,
  DESCRIPTION longtext,
  NAME varchar(255) not null,
  UPDATE_COMMENTS longtext,
  UPDATE_TIME datetime,
  primary key (ID)
);

alter table CG_SOURCE
  add unique index UIX_CGS_NAME (NAME);

create table CG_TRACKING (
  ID integer not null auto_increment,
  CAPTURE_TIME datetime not null,
  CUSTOM1 longtext,
  CUSTOM10 longtext,
  CUSTOM2 longtext,
  CUSTOM3 longtext,
  CUSTOM4 longtext,
  CUSTOM5 longtext,
  CUSTOM6 longtext,
  CUSTOM7 longtext,
  CUSTOM8 longtext,
  CUSTOM9 longtext,
  IP_ADDRESS varchar(255) not null,
  UPDATE_COMMENTS longtext,
  UPDATE_TIME datetime,
  USER_AGENT longtext,
  CAMPAIGN_ID integer not null,
  SOURCE_ID integer not null,
  primary key (ID)
);

alter table CG_TRACKING
  add unique index UIX_CGT_IPA_CID_SID_CT (IP_ADDRESS, CAMPAIGN_ID, SOURCE_ID, CAPTURE_TIME);

create table CG_USER (
  ID integer not null auto_increment,
  CREATION_TIME datetime not null,
  EMAIL varchar(255) not null,
  FIRST_NAME varchar(255) not null,
  LAST_NAME varchar(255) not null,
  PASSWORD varchar(255) not null,
  STATUS integer not null,
  UPDATE_COMMENTS longtext,
  UPDATE_TIME datetime,
  USERNAME varchar(255) not null,
  primary key (ID)
);

alter table CG_USER
  add unique index UIX_CGU_USERNAME (USERNAME);

create table CG_USER_AUTHORITY (
  USER_ID integer not null,
  AUTHORITY_ID integer not null,
  primary key (USER_ID, AUTHORITY_ID)
);

create table CG_USER_ADVERTISER (
  USER_ID integer not null,
  ADVERTISER_ID integer not null,
  primary key (USER_ID, ADVERTISER_ID)
);

create table CG_VERTICAL (
  ID integer not null auto_increment,
  CREATION_TIME datetime not null,
  DESCRIPTION longtext,
  NAME varchar(255) not null,
  UPDATE_COMMENTS longtext,
  UPDATE_TIME datetime,
  primary key (ID)
);

alter table CG_VERTICAL
  add unique index UIX_CGV_NAME (NAME);

alter table CG_CAMPAIGN
  add index IX_CGC_PID (ADVERTISER_ID),
  add constraint FK_CGC_PID
  foreign key (ADVERTISER_ID)
  references CG_ADVERTISER (ID);

alter table CG_CAMPAIGN
  add index IX_CGC_RID (RESPONSE_ID),
  add constraint FK_CGC_RID
  foreign key (RESPONSE_ID)
  references CG_CAMPAIGN_RESPONSE (ID);

alter table CG_CAMPAIGN_DELIVERY
  add index IX_CGCD_CID (CAMPAIGN_ID),
  add constraint FK_CGCD_CID
  foreign key (CAMPAIGN_ID)
  references CG_CAMPAIGN (ID);

alter table CG_CAMPAIGN_DELIVERY_LEAD
  add index IX_CGCDL_CDID (CAMPAIGN_DELIVERY_ID),
  add constraint FK_CGCDL_CDID
  foreign key (CAMPAIGN_DELIVERY_ID)
  references CG_CAMPAIGN_DELIVERY (ID);

alter table CG_CAMPAIGN_DELIVERY_LEAD
  add index IX_CGCDL_LID (LEAD_ID),
  add constraint FK_CGCDL_LID
  foreign key (LEAD_ID)
  references CG_LEAD (ID);

alter table CG_CAMPAIGN_FIELD
  add index IX_CGCF_CID (CAMPAIGN_ID),
  add constraint FK_CGCF_CID
  foreign key (CAMPAIGN_ID)
  references CG_CAMPAIGN (ID);

alter table CG_IMPRESSION_REPORT
  add index IX_CGIR_CID (CAMPAIGN_ID),
  add constraint FK_CGIR_CID
  foreign key (CAMPAIGN_ID)
  references CG_CAMPAIGN (ID);

alter table CG_LEAD
  add index IX_CGL_SID (SOURCE_ID),
  add constraint FK_CGL_SID
  foreign key (SOURCE_ID)
  references CG_SOURCE (ID);

alter table CG_LEAD
  add index IX_CGL_CID (CAMPAIGN_ID),
  add constraint FK_CGL_CID
  foreign key (CAMPAIGN_ID)
  references CG_CAMPAIGN (ID);

alter table CG_ADVERTISER
  add index IX_CGA_VID (VERTICAL_ID),
  add constraint FK_CGA_VID
  foreign key (VERTICAL_ID)
  references CG_VERTICAL (ID);

alter table CG_TRACKING
  add index IX_CGT_SID (SOURCE_ID),
  add constraint FK_CGT_SID
  foreign key (SOURCE_ID)
  references CG_SOURCE (ID);

alter table CG_TRACKING
  add index IX_CGT_CID (CAMPAIGN_ID),
  add constraint FK_CGT_CID
  foreign key (CAMPAIGN_ID)
  references CG_CAMPAIGN (ID);

alter table CG_USER_AUTHORITY
  add index IX_CGUA_UID (USER_ID),
  add constraint FK_CGUA_UID
  foreign key (USER_ID)
  references CG_USER (ID);

alter table CG_USER_AUTHORITY
  add index IX_CGUA_AID (AUTHORITY_ID),
  add constraint FK_CGUA_AID
  foreign key (AUTHORITY_ID)
  references CG_AUTHORITY (ID);

alter table CG_USER_ADVERTISER
  add index IX_CGUA_AID (ADVERTISER_ID),
  add constraint FK_CGUA_AID
  foreign key (ADVERTISER_ID)
  references CG_ADVERTISER (ID);

alter table CG_USER_ADVERTISER
  add index IX_CGUA_UID (USER_ID),
  add constraint FK_CGUA_UID
  foreign key (USER_ID)
  references CG_USER (ID);

INSERT INTO CG_AUTHORITY (ID, AUTHORITY_TYPE, CREATION_TIME, UPDATE_COMMENTS, UPDATE_TIME) VALUES
(1, 'ROLE_USER', now(), NULL, NULL),
(2, 'ROLE_ADVERTISER', now(), NULL, NULL),
(3, 'ROLE_MANAGER', now(), NULL, NULL),
(4, 'ROLE_ADMIN', now(), NULL, NULL);

INSERT INTO CG_USER (ID, CREATION_TIME, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, STATUS, UPDATE_COMMENTS, UPDATE_TIME, USERNAME) VALUES
(1, now(), 'admin@webshrub.com', 'Ahsan', 'Javed', '0a3a3d33b7f413bc4aa464538929ed54', 1, NULL, now(), 'admin');

INSERT INTO CG_USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES
(1, 1),
(1, 4),
(2, 1),
(2, 2),
(2, 3);

INSERT INTO CG_VERTICAL (ID, CREATION_TIME, DESCRIPTION, NAME, UPDATE_COMMENTS, UPDATE_TIME) VALUES
(1,now(),'INSURANCE', 'INSURANCE', '', now()),
(2,now(),'FINANCE', 'FINANCE', '', now());

INSERT INTO CG_SOURCE (ID, CREATION_TIME, DESCRIPTION, NAME, UPDATE_COMMENTS, UPDATE_TIME) VALUES
(1,now(),'EMAIL', 'EMAIL', '', now()),
(2,now(),'SEARCH', 'SEARCH', '', now()),
(3,now(),'DIRECT', 'DIRECT', '', now());
