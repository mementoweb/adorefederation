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
 
/* Postgres Repo OAIDB Database Creation Script */

-- Table: metadata_format

CREATE TABLE metadata_format
(
  metadata_prefix varchar(30),
  namespace varchar(255),
  schema_location varchar(255)
  CONSTRAINT "prefix_PK" PRIMARY KEY (metadata_prefix)
);

-- Table: record_sets

CREATE TABLE record_sets
(
  digest_id char(32),
  set_id int4,
  CONSTRAINT record_sets_digest_id_key UNIQUE (digest_id, set_id)
);

CREATE INDEX setid_idx
  ON record_sets
  USING btree
  (set_id);

-- Table: sets
   
CREATE TABLE sets
(
  set_id SERIAL,
  set_specs varchar(255) NOT NULL,
  set_name varchar(50),
  set_desc varchar(255),
  CONSTRAINT sets_pkey PRIMARY KEY (set_id),
  CONSTRAINT sets_set_specs_key UNIQUE (set_specs)
);

-- Table: metadata_record

CREATE TABLE metadata_record
(
  digest_id char(32) NOT NULL,
  oai_id varchar(200) NOT NULL,
  oai_date timestamp NOT NULL,
  size int4 NOT NULL,
  data bytea NOT NULL,
  CONSTRAINT metadata_record_pkey PRIMARY KEY (digest_id)
);

CREATE INDEX oaidate_idx
  ON metadata_record
  USING btree
  (oai_date);

-- Function: utc_timestamp

CREATE FUNCTION UTC_TIMESTAMP() RETURNS timestamp AS $$
    SELECT current_timestamp at time zone 'utc';
$$ LANGUAGE SQL;
