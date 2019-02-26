/*
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
 * 
 * Copyright (c) 1998-2006 The Regents of the University of California.
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

package gov.lanl.util.xpath.marcxml;

import java.util.Properties;

public class MarcXmlAbbrevProperties extends Properties {
    
    public MarcXmlAbbrevProperties() {
        loadDefaults();
    }
    
    public void loadDefaults() {
        this.put("profile.name", "marcxml");
        this.put("profile.namespace.1", "http://www.loc.gov/MARC21/slim");
        this.put("profile.namespace.prefix.1", "marc");
        this.put("profile.namespace.2", "http://www.w3.org/2001/XMLSchema-instance");
        this.put("profile.namespace.prefix.2", "xsi");
        this.put("profile.field.name.0", "title");
        this.put("profile.field.xpath.0", "//marc:record/marc:datafield[@tag='245']/marc:subfield[@code='a']");
        this.put("profile.field.name.1", "creator");
        this.put("profile.field.xpath.1", "//marc:record/marc:datafield[@tag='700'][contains(marc:subfield[@code='e'],'Author')]/./marc:subfield[@code='a']|//marc:record/marc:datafield[@tag='700'][contains(marc:subfield[@code='e'],'Inventor')]/./marc:subfield[@code='a']|//marc:record/marc:datafield[@tag='700'][contains(marc:subfield[@code='e'],'Patent')]/./marc:subfield[@code='a']|//marc:record/marc:datafield[@tag='720'][contains(marc:subfield[@code='e'],'Recipient')]/./marc:subfield[@code='a']|//marc:record/marc:datafield[@tag='720'][contains(marc:subfield[@code='e'],'Author')]/./marc:subfield[@code='a']|//marc:record/marc:datafield[@tag='720'][contains(marc:subfield[@code='e'],'Creator')]/./marc:subfield[@code='a']|//marc:record/marc:datafield[@tag='720'][contains(marc:subfield[@code='e'],'Patent')]/./marc:subfield[@code='a']");
        this.put("profile.field.name.2", "type");
        this.put("profile.field.xpath.2", "//marc:record/marc:datafield[@tag='655']/marc:subfield[@code='a']");
        this.put("profile.field.name.3", "date");
        this.put("profile.field.xpath.3", "//marc:record/marc:controlfield[@tag='008']");
        this.put("profile.field.name.4", "subject");
        this.put("profile.field.xpath.4", "//marc:record/marc:datafield[@tag='500']/marc:subfield[@code='a']");
        this.put("profile.field.name.5", "journal_volume");
        this.put("profile.field.xpath.5", "//marc:record/marc:datafield[@tag='363']/marc:subfield[@code='a']");
        this.put("profile.field.name.6", "pages");
        this.put("profile.field.xpath.6", "//marc:record/marc:datafield[@tag='363']/marc:subfield[@code='p']");
        this.put("profile.field.name.7", "numberofref");
        this.put("profile.field.xpath.7", "//marc:record/marc:datafield[@tag='504']/marc:subfield[@code='b']");
        this.put("profile.field.name.8", "affiliation_name");
        this.put("profile.field.xpath.8", "//marc:record/marc:datafield[@tag='710']/marc:subfield[@code='a']");
        this.put("profile.field.name.9", "id");
        this.put("profile.field.xpath.9", "//marc:record/marc:controlfield[@tag='001']");
        this.put("profile.field.name.10", "collection");
        this.put("profile.field.xpath.10", "//marc:record/marc:datafield[@tag='040']/marc:subfield[@code='a']");
        this.put("profile.field.name.11", "subset");
        this.put("profile.field.xpath.11", "//marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(),'CF') or contains(text(),'SL')]/../marc:subfield[@code='a'][contains(text(),'Adminmetadata : dc:accessRights')]");
        this.put("profile.field.name.12", "lapubtype");
        this.put("profile.field.xpath.12", "//marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(),'Publication_Type')]/../marc:subfield[@code='a'][contains(text(),'Adminmetadata : dc:accessRights')]");
      }
}