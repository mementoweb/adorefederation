/*
 * MyContent.java
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

package org.foo.didl.content;

/**
 * MyContent is an example of the DIDL Content Model usage. Each content type
 * is comprised of three components; a data object, a serializer, and a 
 * deserializer.  The class is an example of the data object.  The serializer
 * and deserializers are located in the org.foo.didl.serialize package, named 
 * MyContentSerializer and MyContentDeserializer, respectively.  The three 
 * components may also be implemented in a single class.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */

public class MyContent {
    /** MyContent XML Namespace */
    public static final String NAMESPACE = "http://mysimplecontent.com/ns/MySimpleContent/";
    /** MyContent XML Namespace Prefix*/
    public static final String PREFIX = "cn";
    
    private String id;
    private String copyright;
    private String usage;
    private String resourceUri;

    /** Gets the copyright statement */
    public String getCopyright() {
        return copyright;
    }

    /** Sets the copyright statement */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /** Gets the content identifier */
    public String getId() {
        return id;
    }

    /** Sets the content identifier */
    public void setId(String id) {
        this.id = id;
    }

    /** Gets the resource uri */
    public String getResourceUri() {
        return resourceUri;
    }

    /** Sets the resource uri */
    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    /** Gets the usage statement */
    public String getUsage() {
        return usage;
    }

    /** Sets the usage statement */
    public void setUsage(String usage) {
        this.usage = usage;
    }
}
