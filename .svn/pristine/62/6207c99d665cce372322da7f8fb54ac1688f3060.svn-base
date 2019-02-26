USE service_registry;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE if exists OAI_RECORD_SET_MAP;
DROP TABLE if exists OAI_RECORDS;
DROP TABLE if exists OAI_SETS;
DROP TABLE if exists OAI_SCHEMAS;
DROP TABLE if exists OAI_CROSSWALKS;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `OAI_RECORDS` (
  `identifier` varchar(150) NOT NULL,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` varchar(20) default NULL,
  `metadata` text,
  `schemaId` bigint(20) default NULL,
  `ACKOPTLOCK` bigint(20) default NULL,
  `digest_id` char(32) default NULL,
  PRIMARY KEY  (`identifier`),
  KEY `idx_oai_records_timestamp` (`timestamp`),
  UNIQUE KEY `idx_oai_records_digest_id` (`digest_id`)
) ENGINE=InnoDB;

delimiter | 
CREATE TRIGGER addMD5 BEFORE INSERT ON OAI_RECORDS 
FOR EACH ROW 
BEGIN 
SET NEW.digest_id = MD5(NEW.identifier); 
END;
|
delimiter ;

CREATE TABLE OAI_SETS (
       set_spec         VARCHAR(50) NOT NULL
     , set_name         VARCHAR(50)
     , set_description  VARCHAR(100)
     , PRIMARY KEY (set_spec)
) ENGINE=InnoDB;

CREATE TABLE OAI_RECORD_SET_MAP (
       set_spec         VARCHAR(50) NOT NULL
     , record_id        VARCHAR(150) NOT NULL
     , PRIMARY KEY (set_spec, record_id)
     , INDEX (set_spec)     
) ENGINE=InnoDB;

CREATE TABLE OAI_SCHEMAS (
       schemaId        BIGINT NOT NULL
     , name            VARCHAR(50)
     , title           VARCHAR(255)
     , prefix          VARCHAR(20)
     , oai_prefix      VARCHAR(25)
     , identifier      VARCHAR(200)
     , namespace_url   VARCHAR(200)
     , schema_url      VARCHAR(200)
     , namespaces      TEXT
     , castor_mapping  VARCHAR(200)
     , PRIMARY KEY (schemaId)
) ENGINE=InnoDB;

CREATE TABLE OAI_CROSSWALKS (
       crosswalk_id     BIGINT NOT NULL
     , start_schema     BIGINT NOT NULL
     , end_schema       BIGINT NOT NULL
     , xsl              TEXT
     , PRIMARY KEY (crosswalk_id)
     , INDEX (start_schema)
     , INDEX (end_schema)
) ENGINE=InnoDB;

