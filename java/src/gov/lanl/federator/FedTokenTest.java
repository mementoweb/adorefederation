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

package gov.lanl.federator;

import gov.lanl.util.oai.TokenModem;
import junit.framework.TestCase;

public class FedTokenTest extends TestCase {

    public void testTokenEncode() throws Exception {
        FedToken token1 = new FedToken(
                TokenModem
                        .decode("m=didl&f=0001-01-01T00%3A00%3A00Z&u=2004-11-09T20%3A42%3A45Z&i=http%3A%2F%2Fcox.lanl.gov%3A8000%2FSTB-RL%2Fxmltape1%2F1973_bibtape%7CcfEMx8LI"));

        FedToken token2 = new FedToken("0001-01-01T00:00:00Z",
                "2004-11-09T20:42:45Z", null, "didl",
                "http://cox.lanl.gov:8000/STB-RL/xmltape1/1973_bibtape",
                "cfEMx8LI");
        assertEquals(token1.tape, token2.tape);
        assertEquals(token1.tapetoken, token2.tapetoken);
        assertEquals(token1.tape,
                "http://cox.lanl.gov:8000/STB-RL/xmltape1/1973_bibtape");
        assertEquals(token1.tapetoken, "cfEMx8LI");
        assertEquals(token1, token2);
    }

    public void testTokenEncode2() throws Exception {

        // test token without tape resumptiontoken
        FedToken token1 = new FedToken(
                TokenModem
                        .decode("m=didl&f=0001-01-01T00%3A00%3A00Z&u=2004-11-09T20%3A42%3A45Z&i=http%3A%2F%2Fcox.lanl.gov%3A8000%2FSTB-RL%2Fxmltape1%2F1973_bibtape"));

        FedToken token2 = new FedToken("0001-01-01T00:00:00Z",
                "2004-11-09T20:42:45Z", null, "didl",
                "http://cox.lanl.gov:8000/STB-RL/xmltape1/1973_bibtape", null);
        assertTrue(token1.tape.equals(token2.tape));
        assertTrue(token1.tapetoken == null);
        assertTrue(token1.tape
                .equals("http://cox.lanl.gov:8000/STB-RL/xmltape1/1973_bibtape"));
        assertTrue(token1.equals(token2));

    }

}
