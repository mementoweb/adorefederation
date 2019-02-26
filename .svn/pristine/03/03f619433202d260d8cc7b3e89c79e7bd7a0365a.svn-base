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

import java.io.FileInputStream;
import java.io.FileOutputStream;

import gov.lanl.disseminator.util.Stream2Array; 
import gov.lanl.disseminator.DmtAbstractTestCase;

public class PdfTest extends  DmtAbstractTestCase{
	String text;
	String inputfile;
	String outputfile;
	String imagefile;
	
	public PdfTest() throws Exception{
		super();
	}
	
	public void setUp() throws Exception{
		text="text";
		inputfile=exdir+"/xmltape.pdf";
		outputfile=exdir+"result.pdf";
		imagefile=exdir+"/elogo.gif";
		
	}
	
	public void testBrandTest() throws Exception{
		BrandText bt=new BrandText();
		FileInputStream fin=new FileInputStream(inputfile);
		byte[] result=bt.brand(Stream2Array.read(fin), text);
		fin.close();
		FileOutputStream fout=new FileOutputStream(outputfile);
		fout.write(result);
		fout.close();;
	}
	
	public void testWaterMarkTest() throws Exception{
		AddWatermark marker=new AddWatermark();
		FileInputStream fin=new FileInputStream(inputfile);
		FileInputStream imagein=new FileInputStream(imagefile);
		byte[] result=marker.watermark(Stream2Array.read(fin), Stream2Array.read(imagein), text);
		fin.close();
		FileOutputStream fout=new FileOutputStream(outputfile);
		fout.write(result);
		fout.close();;
	}
	
	
}
