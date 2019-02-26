/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
 *
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 */

package gov.lanl.arc.dkImpl;


/**
 * Sample Application to search arc file index file
 * @author ludab
 */
public class ARCFileReader {
    
    String[] line;
    String identifier;
    String indexFile;
    String arcFile;
    
    public static void main(String args[]) {
        try {
            ARCFileReader arc = new ARCFileReader();
            arc.setArcFile("/Volumes/UserData/rchute/dev/tmp/eprint/data/20050719114757/tape_36a7e190-8716-4259-af7c-e6286a348ba9/EPRINT_9d4c9413-4143-4854-a977-dbbc66e79cfd.arc");
            arc.setIdentifier("info:lanl-repo/ds/05986b2d-1234-4c57-aca3-8bbaacd1b302");
            arc.setIndexFile("/Volumes/UserData/rchute/dev/tmp/eprint/data/20050719114757/tape_36a7e190-8716-4259-af7c-e6286a348ba9/EPRINT_9d4c9413-4143-4854-a977-dbbc66e79cfd.cdx");
            String[] line = arc.getIndex();
            System.out.println(line[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateIndex() throws Exception {
        ExtractCDX.extractFromArc(arcFile);
    }
    
    public String[] getIndex(){
        BinSearch bs = new BinSearch();
        String[] line = bs.doSearch(identifier, indexFile);
        return line;
    }
    
    /**
     * @return Returns the identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier The identifier to set.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return Returns the indexFile.
     */
    public String getIndexFile() {
        return indexFile;
    }

    /**
     * @param indexFile The indexFile to set.
     */
    public void setIndexFile(String indexFile) {
        this.indexFile = indexFile;
    }

    public String getArcFile() {
        return arcFile;
    }

    public void setArcFile(String arcFile) {
        this.arcFile = arcFile;
    }
}
