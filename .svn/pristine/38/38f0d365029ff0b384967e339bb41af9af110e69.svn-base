/* ArcUtils -- ARC file access utilities
 * Copyright (c) 2004 Statsbiblioteket
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package gov.lanl.arc.dkImpl;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Class for reading entries out of ARC files, whether compressed or not.
 * Because the length of the entry must be found in uncompressed files by
 * parsing the metadata line, that line is not part of the stream. Its contents
 * (unparsed) can be found with the get* functions.
 */
public class ARCInputStream extends InputStream {
    // Note that we extend InputStream just for the interface, we actually
    // hold a separate inputstream that we read from. This is so that
    // we can a) be given an inputstream if so desired, and b) handle
    // compressed files.
    protected InputStream in;

    protected boolean compressed;

    protected String url, ip, date, mime, length;

    protected long bodybytes;

    /**
     * Creates a new ARCInputStream reading the specified file at the given
     * offset.
     * 
     * @param file
     *            An ARC file. If the filename ends with .gz, it is assumed to
     *            be compressed.
     * @param offset
     *            An offset into the ARC file to start reading at. An error will
     *            occur later if the offset is not the start of an ARC file
     *            entry.
     */
    public ARCInputStream(File file, long offset) throws IOException {
        RandomAccessFile infile = new RandomAccessFile(file, "r");
        this.setup(infile, file.getName().endsWith(".gz"), offset);
        // We know that current unzipped files don?t hang on to the infile.
        // Gzipped files are another matter, as we use getFD() to get a new
        // File off the R.A.F. We don't know if one or two close() calls
        // are the right thing to do, so we leave it open.
        if (!file.getName().endsWith(".gz"))
            infile.close();
    }

    /**
     * Create an ARCInputStream from a RandomAccessFile.
     * 
     * @param infile
     *            A file to read an ARC entry from. Note that this file might be
     *            in use as long as the ARCInputStream is in use, and so
     *            shouldn't be closed earlier.
     * @param compressed
     *            Whether the file is compressed (in chunks) or not
     * @param offset
     *            The raw offset to start reading from.
     * @throws IOException
     *             If the file is shorted than the offset, or if an underlying
     *             read error occurs while reading the header.
     */
    public ARCInputStream(RandomAccessFile infile, boolean compressed,
            long offset) throws IOException {
        this.setup(infile, compressed, offset);
    }

    private void setup(RandomAccessFile infile, boolean compressed, long offset)
            throws IOException {
        this.compressed = compressed;
        infile.seek(offset);
        if (infile.getFilePointer() != offset) {
            throw new IOException("Failed to seek to " + offset);
        }
        if (compressed) {
            in = new GZIPInputStream(new FileInputStream(infile.getFD()));

            String line;
            do {
                byte[] data = readRawLine(in);
                line = trimNewline(new String(data));
            } while (line.length() == 0);
            parseHeader(line);
        } else {
            // There doesn't seem to be a function to limit the length of
            // an inputstream, so we have to read the exact number of bytes
            // into an array and then use that for our inputstream.
            String line = infile.readLine();
            while (line.length() == 0)
                line = infile.readLine();
            parseHeader(line);
            byte[] bytes = new byte[(int) bodybytes];
            int bytesread = infile.read(bytes);
            if (bytesread != bytes.length) {
                System.err.println("Read only " + bytesread + " of "
                        + bytes.length + " bytes");
            }
            in = new ByteArrayInputStream(bytes);
        }
    }

    //Following code adapted from org.apache.commons.httpclient.HttpParser
    /**
     * Read a raw line (terminated by \n) from input. If the input ends before
     * any characters are read (including \n's), null is returned, indicating
     * end of file. Note that this assumes that the line terminator is \n.
     * 
     * @param inputStream
     *            An input stream.
     * @return An array of bytes read from the input stream, including \n.
     */
    public static byte[] readRawLine(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int ch;
        while ((ch = inputStream.read()) >= 0) {
            buf.write(ch);
            if (ch == '\n') {
                break;
            }
        }
        if (buf.size() == 0) {
            return null;
        }
        return buf.toByteArray();
    }

    /**
     * Read a line of bytes. This is handy because ARC file entries contain both
     * line-based text and binary parts. Note that it currently assumes that
     * lines end with \n or \r\n.
     * 
     * @return A line of text read from the inputStream, or null if the end of
     *         file has been reached.
     */
    public String readLine() throws IOException {
        byte[] rawdata = readRawLine(in);
        if (rawdata == null) {
            return null;
        }
        return trimNewline(new String(rawdata));
    }

    /**
     * Trim final newline characters off a string.
     * 
     * @param s
     *            A string ending in \n
     * @return The same string, without \n and preceeding \r
     */
    private String trimNewline(String s) {
        if (s.endsWith("\r\n"))
            return s.substring(0, s.length() - 2);
        if (s.endsWith("\n"))
            return s.substring(0, s.length() - 1);
        return s;
    }

    private static Matcher archead = Pattern.compile(
            "(^.*:[^ ]*)\\s([0-9.]+)\\s(\\d+)\\s(\\S+)\\s(\\d+)$").matcher("");

    private void parseHeader(String line) throws IOException {
        if (archead.reset(line).matches()) {
            url = archead.group(1);
            ip = archead.group(2);
            date = archead.group(3);
            mime = archead.group(4);
            length = archead.group(5);
        } else {
            throw new IOException("Malformed ARC header: `" + line + "'");
        }
        bodybytes = Long.parseLong(length);
    }

    /** Returns the URL found in the ARC header */
    public String getUrl() {
        return url;
    }

    /** Returns the IP found in the ARC header */
    public String getIp() {
        return ip;
    }

    /** Returns the date found in the ARC header */
    public String getDate() {
        return date;
    }

    /** Returns the mime found in the ARC header */
    public String getMime() {
        return mime;
    }

    /** Returns the length found in the ARC header */
    public String getLength() {
        return length;
    }

    /**
     * Reads all the data available on the input stream.
     * 
     * @return An array of bytes read from the stream. This may be quite large.
     */
    public byte[] readAll() throws IOException {
        /*
         * This is actually the fastest way to read, as the multibyte read
         * functions just call InputStream.read() repeatedly. Grrr...
         */
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) >= 0) {
            buf.write(ch);
        }
        return buf.toByteArray();
    }

    /* ******* Implementation of InputStream functions follow ********** */
    /**
     * Returns the number of bytes that can be read (or skipped over) from this
     * input stream without blocking by the next caller of a method for this
     * input stream.
     */
    public int available() throws IOException {
        return in.available();
    }

    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     */
    public void close() throws IOException {
        in.close();
    }

    /**
     * Marks the current position in this input stream.
     */
    public void mark(int readlimit) {
        in.mark(readlimit);
    }

    /**
     * Tests if this input stream supports the mark and reset methods.
     */
    public boolean markSupported() {
        return in.markSupported();
    }

    /**
     * Reads the next byte of data from the input stream.
     */
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads some number of bytes from the input stream and stores them into the
     * buffer array b.
     */
    public int read(byte[] b) throws IOException {
        return in.read(b);
    }

    /**
     * Reads up to len bytes of data from the input stream into an array of
     * bytes.
     */
    public int read(byte[] b, int off, int len) throws IOException {
        return in.read(b, off, len);
    }

    /**
     * Repositions this stream to the position at the time the mark method was
     * last called on this input stream.
     */
    public void reset() throws IOException {
        in.reset();
    }

    /**
     * Skips over and discards n bytes of data from this input stream.
     */
    public long skip(long n) throws IOException {
        return in.skip(n);
    }

}
