/*
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
 * 
 * Copyright © 1998-2005 The Regents of the University of California.
 * 
 * Unless otherwise indicated, this information has been authored by an employee 
 * or employees of the University of California, operator of the Los Alamos National
 * Laboratory under Contract No. W-7405-ENG-36 with the U.S. Department of Energy. 
 * The U.S. Government has rights to use, reproduce, and distribute this information. 
 * The public may copy and use this information without charge, provided that this 
 * Notice and any statement of authorship are reproduced on all copies. Neither the 
 * Government nor the University makes any warranty, express or implied, or assumes 
 * any liability or responsibility for the use of this information.
 * 
 */
 
/* MySQL Repo OAIDB Database Creation Script */

Create table metadata_record
(
  digest_id  CHAR(32)  NOT NULL ,
  oai_id     VARCHAR(200) NOT NULL,
  oai_date   DATETIME  NOT NULL,
  size       MEDIUMINT UNSIGNED NOT NULL,
  data       MEDIUMBLOB NOT NULL,
  PRIMARY KEY (digest_id),
  INDEX (oai_date)
)
  TYPE = MyISAM;

Create table  record_sets
(
  digest_id  CHAR (32),  
  set_id   SMALLINT(5), 
  INDEX (digest_id),
  INDEX (set_id)  
)
  TYPE = MyISAM
  ROW_FORMAT = fixed;
 
Create  UNIQUE  index  idx_all on record_sets  (digest_id,set_id);
   
Create table sets
( 
  set_id  SMALLINT(5) NOT NULL  AUTO_INCREMENT,
  set_specs VARCHAR (255) NOT NULL UNIQUE ,
  set_name VARCHAR (50),
  set_desc  VARCHAR (255),
  PRIMARY KEY (set_id)
)
  TYPE = MyISAM;

Create table metadata_format
(
  metadata_prefix VARCHAR (30),
  namespace VARCHAR (255),
  schema_location  VARCHAR (255)
)
  TYPE = MyISAM;

