/*
 * Copyright (c) 2008  Los Alamos National Security, LLC.
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

package gov.lanl.disseminator.util;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Reader;

import java.io.IOException;

public class Stream2Array {

	public static final int BUFFER_SIZE = 1024 * 100;

	public static byte[] read(InputStream input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[BUFFER_SIZE];
		int size = 0;
		while ((size = input.read(buffer)) != -1) {
			baos.write(buffer, 0, size);
		}
		return baos.toByteArray();
	}

	public static String read(Reader reader) throws IOException {
		CharArrayWriter writer = new CharArrayWriter(1024);
		char[] buffer = new char[BUFFER_SIZE];
		int size = 0;
		while ((size = reader.read(buffer)) != -1) {
			writer.write(buffer, 0, size);
		}
		return writer.toString();
	}

}
