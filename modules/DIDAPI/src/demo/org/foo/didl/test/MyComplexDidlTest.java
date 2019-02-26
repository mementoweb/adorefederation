/*
 * MyComplexDidlTest.java
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

package org.foo.didl.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

import org.foo.didl.MyComplexDidl;
import org.foo.didl.MyComplexComponent.COMPONENT_TYPE;

public class MyComplexDidlTest {

    private static String outfile;

    public static void main(String[] args) throws Exception {
        if (args.length > 0)
            outfile = args[0];
        MyComplexDidl com = new MyComplexDidl();
        com.setContentId("info:doi/10.1016/j.dyepig.2004.06.022");
        com.setDocumentId("info:lanl-repo/i/dd7b17ea-bddf-11d9-9de5-c11b6cd8559");
        com.addComponent(
                        COMPONENT_TYPE.MODSXML,
                        "info:lanl-repo/ds/99569f9a-23db-4eb2-a0d4-ec1ff34dae5f",
                        "application/xml",
                        null,
                        MyDidlUtils.fetch("http://www.loc.gov/standards/marcxml/Sandburg/sandburgmods.xml"));
        com.addComponent(
                        COMPONENT_TYPE.MARCXML,
                        "info:lanl-repo/ds/99569f9a-23db-4eb2-a0d4-ec1ff34dae5f",
                        "application/xml",
                        new URI("http://www.loc.gov/standards/marcxml/Sandburg/sandburg.xml"),
                        MyDidlUtils.fetch("http://www.loc.gov/standards/marcxml/Sandburg/sandburg.xml"));
        com.addComponent(COMPONENT_TYPE.RESOURCE,
                        "info:lanl-repo/ds/967a8345-4675-4f1a-aa0a-fbb4cdb527aa",
                        "application/pdf", new URI(
                        "http://www.niso.org/registration/MODSREGweb.pdf"),
                        null);
        if (outfile != null) {
            FileOutputStream fos = new FileOutputStream(new File(outfile));
            fos.write(com.getXML().getBytes());
         }
        MyComplexDidl com2 = new MyComplexDidl();
        com2.parse(new ByteArrayInputStream(com.getXML().getBytes()));
        String com2_xml = com2.getXML();
        System.out.println(com2_xml);
    }
}
