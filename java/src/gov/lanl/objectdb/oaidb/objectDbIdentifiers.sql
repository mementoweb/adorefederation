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
 
/* MySQL Object Database OpenURL Support Creation Script */

/** Clean-up existing table */
drop table if exists identifiers;

/** Initially, create tables without index */
create table identifiers (identifier varchar(200), digest_id char(32), PRIMARY KEY (identifier, digest_id));

/** Optional: Create Index to optimize performance */
//create index ind_identifier on identifiers (identifier);

/** Optional: Load Index into memory */
//load index into cache identifiers;