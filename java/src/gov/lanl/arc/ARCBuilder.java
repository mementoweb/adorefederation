package gov.lanl.arc;

import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.util.MimeTypeMapper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ARCBuilder {
    public static ARCFileWriter writer;
    
    public ARCBuilder(String arcFileDir, String prefix) {
    	try {
			writer = new ARCFileWriter(arcFileDir, prefix);
		} catch (ARCException e) {
			e.printStackTrace();
		}
    }
    
    public void processResourceDir(File resDir, boolean recursive) throws ARCException {
    	for (File f : getFileList(resDir.getAbsolutePath(),new JP2FileFilter(),recursive)) {
    		setResource(f);
    	}
    }
    
    public ArrayList<String> close() throws IOException {
    	writer.close();
    	return writer.getArcFileNameList();
    }
    
    private void setResource(File resource) throws ARCException {
        FileInputStream fis;
        try {
            fis = new FileInputStream(resource);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(fis.available());
            byte[] buffer = new byte[1024];
            int count = 0;
            BufferedInputStream bis = new BufferedInputStream(fis);
            while ((count = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
            int recordLength = baos.size();
            int jjj = resource.getAbsolutePath().lastIndexOf(".");
            String ext = resource.getAbsolutePath().substring(jjj + 1, resource.getAbsolutePath().length());
            String mimeType = new MimeTypeMapper().getMimeType(ext);
            long timeStamp = System.currentTimeMillis();
            writer.write(writer.getUUIDResourceURI(), 
                    "0.0.0.0", 
                    mimeType, 
                    baos, 
                    timeStamp, 
                    recordLength);
            baos.close();
        } catch (FileNotFoundException e) {
            throw new ARCException(e.getCause());
        } catch (IOException e) {
            throw new ARCException(e.getCause());
        }
    }
    
    public static void main(String[] args) {
    	String arcDir= null;
    	String prefix = null;
    	File processDir = null;
    	if (args.length > 1) {
    		arcDir = new File(args[0]).getAbsolutePath();
    		processDir = new File(args[2]);
    		if (args.length > 2) 
    		    prefix = args[1];
        } else {
        	System.out.println("Usage: ARCBuilder arcFileDir");
        }
    	long a = System.currentTimeMillis();
    	ARCBuilder ab = new ARCBuilder(arcDir, prefix);
    	try {
			ab.processResourceDir(processDir, false);
			ab.close();
		} catch (ARCException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - a);
    }
    
    /**
     * Gets a ArrayList of File objects provided a dir or file path.
     * @param filePath
     *        Absolute path to file or directory
     * @param fileFilter
     *        Filter dir content by extention; allows "null"
     * @param recursive
     *        Recursively search for files
     * @return
     *        ArrayList of File objects matching specified criteria.
     */
    private static ArrayList<File> getFileList(String filePath, FileFilter fileFilter, boolean recursive) {
        ArrayList<File> files = new ArrayList<File>();
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            File[] fa = file.listFiles(fileFilter);
            for (int i = 0; i < fa.length; i++) {
                if (fa[i].isFile())
                  files.add(fa[i]);
                else if (recursive && fa[i].isDirectory())
                  files.addAll(getFileList(fa[i].getAbsolutePath(), fileFilter, recursive));
            }
        }
        else if (file.exists() && file.isFile()) {
            files.add(file);
        }
        
        return files;
    }
    
    private class JP2FileFilter implements java.io.FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) return true;
            String name = f.getName().toLowerCase();
            return name.endsWith("jp2") || name.endsWith("jpx") || name.endsWith("jpm");
        }
    }
}
