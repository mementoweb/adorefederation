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

package gov.lanl.disseminator.util.pdf;

import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class BrandText extends java.lang.Object {

	/**
	 * @param intput
	 *            bytearray of input pdf document
	 * @param text
	 *            the text to be branded.
	 * @return new pdf document
	 */
	public byte[] brand(byte[] input, String text) {
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			int pages = 1;
			float x1 = 30f;
			float x2 = 280f;
			float x3 = 320f;
			float x4 = 565f;

			float[] y1 = new float[pages];
			float[] y2 = new float[pages];

			float height = (778f - (20f * (pages - 1))) / pages;

			y1[0] = 812f;
			y2[0] = 812f - height;

			for (int i = 1; i < pages; i++) {
				y1[i] = y2[i - 1] - 20f;
				y2[i] = y1[i] - height;
			}

			// we create a reader for a certain document
			PdfReader reader = new PdfReader(input);
			// we retrieve the total number of pages
			int n = reader.getNumberOfPages();

			// step 1: creation of a document-object
			Document document = new Document(PageSize.A4);
			// step 2: we create a writer that listens to the document
			PdfWriter writer = PdfWriter.getInstance(document, output);
			// step 3: we open the document
			HeaderFooter footer = new HeaderFooter(new Phrase(text), false);// henry
																			// hack
			footer.setBorder(Rectangle.NO_BORDER);// henry hack
			document.setFooter(footer);// henry hack

			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PdfImportedPage page;
			int rotation;
			int i = 0;
			int p = 0;
			// step 4: we add content
			while (i < n) {
				i++;
				Rectangle rect = reader.getPageSizeWithRotation(i);
				float factorx = (x2 - x1) / rect.width();
				float factory = (y1[p] - y2[p]) / rect.height();
				float factor = (factorx < factory ? factorx : factory);
				float dx = (factorx == factor ? 0f : ((x2 - x1) - rect.width()
						* factor) / 2f);
				float dy = (factory == factor ? 0f : ((y1[p] - y2[p]) - rect
						.height()
						* factor) / 2f);
				page = writer.getImportedPage(reader, i);
				rotation = reader.getPageRotation(i);
				if (rotation == 90 || rotation == 270) {
					cb.addTemplate(page, 0, -factor, factor, 0, x1 + dx, y2[p]
							+ dy + rect.height() * factor);
				} else {
					// cb.addTemplate(page, factor, 0, 0, factor, x1 + dx, y2[p]
					// + dy);
					factor = 9 / 10;
					cb.addTemplate(page, 1, 0, 0, 1, dx, 50);
				}
				// cb.setRGBColorStroke(0xC0, 0xC0, 0xC0);
				// cb.rectangle(x3 - 5f, y2[p] - 5f, x4 - x3 + 10f, y1[p] -
				// y2[p] + 10f);
				// for (float l = y1[p] - 19; l > y2[p]; l -= 16) {
				// cb.moveTo(x3, l);
				// cb.lineTo(x4, l);
				// }
				// cb.rectangle(x1 + dx, y2[p] + dy, rect.width() * factor,
				// rect.height() * factor);

				// cb.stroke();
				//System.out.println("Processed page " + i);
				p++;
				if (p == pages) {
					p = 0;
					document.newPage();
				}
			}
			// step 5: we close the document
			document.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return output.toByteArray();
	}

	public static void main(String[] args) throws Exception {
		BrandText render = new BrandText();
		FileInputStream fi = new FileInputStream(args[0]);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024 * 100];
		int size = 0;
		while ((size = fi.read(buffer)) != -1) {
			baos.write(buffer, 0, size);
		}

		FileOutputStream fo = new FileOutputStream(args[1]);
		fo.write(render.brand(baos.toByteArray(),
				"This PDF brought to you by the LANL Research Library "));
		fo.close();
	}
}
