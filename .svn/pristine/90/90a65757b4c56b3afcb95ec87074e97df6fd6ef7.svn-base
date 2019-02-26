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

package gov.lanl.xmltape;

import java.io.*;

/**
 * XMLTape Writer for tapes which are to be streamed to Standard Output
 */

public class StdoutTapeWriter implements TapeWriterInterface {
    SingleTapeWriter stw;

    /**
     * Constuctor initializes writer and sets default admin values
     * @throws java.io.IOException
     */
    public StdoutTapeWriter() throws java.io.IOException {
        stw = new SingleTapeWriter(new PrintWriter(System.out));
        stw.writeDefaultAdmin();
    }

    /**
     * Writes provided TapeRecord to stdout
     */
    public synchronized void write(TapeRecord taperecord)
            throws java.io.IOException {
        stw.writeRecord(taperecord);
    }

    /**
     * Sets TapeAdmin and writes tape admin to stdout
     */
    public synchronized void addAdmin(String admin) throws java.io.IOException {
        stw.writeTapeAdmin(admin);
    }

    /**
     * Closes currently initialized tape writer
     */
    public void close() throws java.io.IOException {
        stw.close();
    };
}
