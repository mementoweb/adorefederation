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

import java.io.FileOutputStream;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Reads the pages of an existing PDF file, adds pagenumbers and a watermark.
 */
public class AddWatermark {

	/**
	 * @param intput
	 *            bytearray of input pdf document
	 * @param image
	 *            byte array of input image
	 * @param text
	 *            additional text to be attached with image.
	 * @return new pdf document
	 */
	public byte[] watermark(byte[] input, byte[] image, String text) {
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			// we create a reader for a certain document
			PdfReader reader = new PdfReader(input);
			int n = reader.getNumberOfPages();
			// we create a stamper that will copy the document to a new file
			PdfStamper stamp = new PdfStamper(reader, output);
			// adding some metadata
			HashMap moreInfo = new HashMap();
			moreInfo.put("Author", "Xiaoming Liu");
			stamp.setMoreInfo(moreInfo);
			// adding content to each page
			int i = 0;
			PdfContentByte under;
			PdfContentByte over;
			Image img = Image.getInstance(image);
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.WINANSI, BaseFont.EMBEDDED);
			img.setAbsolutePosition(200, 400);
			while (i < n) {
				i++;
				// watermark under the existing page
				under = stamp.getUnderContent(i);
				under.addImage(img);

				// text over the existing page

				over = stamp.getOverContent(i);
				over.beginText();
				over.setFontAndSize(bf, 18);
				over.setTextMatrix(30, 30);
				over.showText("page " + i);
				over.setFontAndSize(bf, 32);
				over.showTextAligned(Element.ALIGN_LEFT, text, 230, 430, 45);
				over.endText();

			}
			// adding an extra page
			// closing PdfStamper will generate the new PDF file
			stamp.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return output.toByteArray();
	}

	/**
	 * Reads the pages of an existing PDF file, adds pagenumbers and a
	 * watermark.
	 * 
	 * @param args
	 *            inputpdf, image, and output pdf file
	 */
	public static void main(String[] args) throws Exception {
		AddWatermark render = new AddWatermark();
		FileInputStream fi = new FileInputStream(args[0]);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024 * 100];
		int size = 0;
		while ((size = fi.read(buffer)) != -1) {
			baos.write(buffer, 0, size);
		}

		FileInputStream imagefi = new FileInputStream(args[1]);
		ByteArrayOutputStream imagebaos = new ByteArrayOutputStream(1024);
		byte[] imagebuffer = new byte[1024 * 100];
		int imagesize = 0;
		while ((imagesize = imagefi.read(imagebuffer)) != -1) {
			imagebaos.write(imagebuffer, 0, imagesize);
		}

		FileOutputStream fo = new FileOutputStream(args[2]);
		fo.write(render.watermark(baos.toByteArray(), imagebaos.toByteArray(),
				"duplicate"));
		fo.close();
	}
}
