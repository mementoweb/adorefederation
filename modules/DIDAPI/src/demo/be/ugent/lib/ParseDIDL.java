/*
 * ParseDIDL.java
 * 
 * Created on April 05, 2006, 7:28 AM
 * 
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */ 

package be.ugent.lib;

import info.repo.didl.DIDLType;
import info.repo.didl.impl.serialize.DIDLDeserializer;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The class reads a DIDL into memory
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class ParseDIDL {
    private DIDLType didl;

    /** Creates a new instance of ParseDIDL */
    public ParseDIDL() {
    }

    public void parse(File f) throws IOException, DIDLSerializationException {
        DIDLDeserializerType deserializer = new DIDLDeserializer();

        didl = (DIDLType) deserializer.read(new FileInputStream(f));
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("usage: ParseDIDL file");
            System.exit(1);
        }

        ParseDIDL p = new ParseDIDL();
        p.parse(new File(args[0]));
    }
}