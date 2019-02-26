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

package gov.lanl.ingest.oaitape;

import gov.lanl.ingest.IngestProperties;
import gov.lanl.ingest.IngestConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

/**
 * <p>DirIngester walks thru an xmltape directory structure and processes 
 * tape file resources into arc files, a csv file is generated for each tape.</p>
 * 
 *     public void beginDeref() { <br>
 *        DirIngester ingester = new DirIngester();<br>
 *        ingester.setProjectDir(projectDir);<br>
 *        ingester.setPlugin(ingestPlugin);<br>
 *        ingester.setProject(projectName);<br>
 *         ingester.setXmlTapesDir(tapeDir);<br>
 *        ingester.setLastIngest(lastIngest);<br>
 *        ingester.setMaxsize(maxsize);<br>
 *        ingester.runIt();<br>
 *        this.setLastIngestProperty(ingester.getLastIngest());<br>
 *    }<br>
 */

public class DirIngester implements java.util.Comparator {

    private SimpleDateFormat formatter;

    private String _project;

    private String _xmltapesdir;

    private String _lastingestpropfile;
    
    private String _lastingest;

    private String _plugin;

    private String _sidPrefix;

    private String _harvlog;

    private String _projectdir;

    private String _maxsize;

    private String _datastreamprefix = IngestProperties.getLocalDataStreamPrefix();
    
    private static String FILEDIV = File.separator;

    static Logger log = Logger.getLogger(DirIngester.class.getName());

    // remember: DEBUG < INFO < WARN < ERROR < FATAL
    {
    formatter = new SimpleDateFormat(IngestConstants.FORMAT_INGEST_DATE_TIME);
    TimeZone tz = TimeZone.getTimeZone(IngestConstants.FORMAT_TIME_ZONE);
    formatter.setTimeZone(tz);
    }

    public static void main(String[] args) {
        DirIngester ingester = new DirIngester();
        ingester.setUp(args[0]);
        ingester.runIt();
        ingester.setLastIngestPropFile();
    }

    private String date2UTC(Date date) {
        return formatter.format(date);
    }

    private Date UTC2date(String date) throws ParseException {
        return formatter.parse(date);
    }

    private static String stripExtention(String name) {
        int jj = name.lastIndexOf(".");
        name = name.substring(0, jj);
        return name;
    }

    /**
     * Set-up DirIngester using a properties file located on the file system
     * 
     * @param dir
     *            system directory of deref project
     */
    public void setUp(String dir) {
        try {
              Properties prop = new Properties();
              prop.load(new FileInputStream(new File(dir)));
              setUp(prop);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Set-up DirIngester using an existing Properties Object
     * 
     * @param prop
     *            Properties object containing necessary DirIngester props.
     */
    public void setUp(Properties prop) {
        try {
            _projectdir = prop.getProperty(IngestConstants.TAG_PROJECT_DIR);
            if (!_projectdir.endsWith("/"))
            	_projectdir = _projectdir + "/";
            _project = prop.getProperty(IngestConstants.TAG_PROJECT_NAME);
            _xmltapesdir = prop.getProperty(IngestConstants.TAG_XML_TAPE_DIR);
            if (!_xmltapesdir.endsWith("/"))
            	_xmltapesdir = _xmltapesdir + "/";
            _plugin = prop.getProperty(IngestConstants.TAG_INGEST_PLUGIN);
            _sidPrefix = prop.getProperty(IngestConstants.TAG_CLASSIFICATION);
            _maxsize = prop.getProperty(IngestConstants.TAG_MAX_ARC_SIZE, IngestConstants.DEFAULT_MAX_ARC_SIZE);
            _datastreamprefix = prop.getProperty(IngestConstants.TAG_LOCAL_DS);

            Properties ingestProp = new Properties();
            _harvlog = new File(_projectdir, _project.toLowerCase() + "_" + IngestConstants.PROP_FILE_LAST_INGEST).getAbsolutePath();
            ingestProp.load(new FileInputStream(new File(_harvlog)));
            _lastingest = ingestProp.getProperty(IngestConstants.TAG_LAST_INGEST);
            if (_project == null || _xmltapesdir == null || _plugin == null
                    || _sidPrefix == null || _maxsize == null || _datastreamprefix == null) {
            	System.out.println(IngestConstants.TAG_PROJECT_NAME + ": " + _project);
            	System.out.println(IngestConstants.TAG_XML_TAPE_DIR + ": " + _xmltapesdir);
            	System.out.println(IngestConstants.TAG_INGEST_PLUGIN + ": " + _plugin);
            	System.out.println(IngestConstants.TAG_CLASSIFICATION + ": " + _sidPrefix);
            	System.out.println(IngestConstants.TAG_MAX_ARC_SIZE + ": " + _maxsize);
            	System.out.println(IngestConstants.TAG_LOCAL_DS + ": " + _datastreamprefix);
                throw new RuntimeException("required config parameter missing");
            }
        } catch (Exception e) {
          throw new RuntimeException(e);
       } 
    }

    /**
     * Walks thru xmltape directories and process those directories
     * which are later then the last ingest date.  
     * 
     * See class description for usage.
     */

    public void runIt() {
        try {

            SingleTapeIngester driver = new SingleTapeIngester();
            String[] dir = new java.io.File(_xmltapesdir).list();

            ArrayList<String> nodots = new ArrayList<String>();

            for (int k = 0; k < dir.length; k++) {

                if (dir[k].indexOf(".") < 0) {

                    nodots.add(dir[k]);
                }
            }

            Date[] dirdates = new Date[nodots.size()];

            for (int k = 0; k < nodots.size(); k++) {
                dirdates[k] = UTC2date((String) nodots.get(k));
            }

            Arrays.sort(dirdates, this);
            String appendername = null;
            for (int i = 0; i < dirdates.length; i++) {
                if (dirdates[i].after(UTC2date(_lastingest))) {

                    String dirstr = date2UTC(dirdates[i]);

                    String[] tapes = new java.io.File(_xmltapesdir + FILEDIV + dirstr).list();

                    for (int j = 0; j < tapes.length; j++) {

                        String arcdir = _projectdir + "data" + FILEDIV + dirstr + FILEDIV
                                + stripExtention(tapes[j]) + FILEDIV;
                        String logdir = _projectdir + "log" + FILEDIV + dirstr + FILEDIV
                                + stripExtention(tapes[j]) + FILEDIV;

                        new File(arcdir).mkdirs();
                        new File(logdir).mkdirs();

                        Logger root = Logger.getRootLogger();

                        if (appendername != null) {
                            root.removeAppender(appendername);
                        }

                        FileAppender FileAppender = createAppender(logdir);
                        appendername = FileAppender.getName();
                        root.addAppender(FileAppender);

                        log.info("project:" + _project);
                        log.info("plugin:" + _plugin);
                        log.info("lastingest:" + _lastingest);
                        log.info("xmltapesdir:" + _xmltapesdir);
                        log.info("start ingest tape:" + tapes[j]);

                        String arcprefix = _project + "_";
                        String xmltape = _xmltapesdir + dirstr + FILEDIV + tapes[j];
                        driver.ingestTape(xmltape, arcdir, arcprefix, _sidPrefix,
                                _plugin, _maxsize, _datastreamprefix);
                        log.info("finish tape:" + tapes[j]);
                        _lastingest = date2UTC(dirdates[i]);
                    }
                    log.info("finish directory:" + date2UTC(dirdates[i]));

                }
            }

        } catch (Exception e) {
            log.error("DirIngester process error:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    /**
     * Create RollingFileAppender for log4j
     * 
     * @param logdir
     *            directory where to spool log4j
     * @return RollingFileAppender
     * @throws IOException
     */

    public RollingFileAppender createRollingAppender(String logdir)
            throws IOException {
        PatternLayout myLayout = new PatternLayout(
                "%d{dd MMM yyyy HH:mm:ss,SSS}%n %-5p %C{1}:%L - %m%n");
        RollingFileAppender rollingFileAppender = new RollingFileAppender(
                myLayout, logdir + IngestConstants.LOG_FILE_INGEST, true);
        rollingFileAppender.setMaxFileSize("10MB");
        rollingFileAppender.setMaxBackupIndex(10000);
        return rollingFileAppender;
    }

    /**
     * Create FileAppender for log4j
     * 
     * @param logdir
     *            directory where to spool log4j
     */

    public FileAppender createAppender(String logdir) throws IOException {
        PatternLayout myLayout = new PatternLayout(
                "%d{dd MMM yyyy HH:mm:ss,SSS}%n %-5p %C{1}:%L - %m%n");
        FileAppender FileAppender = new FileAppender(myLayout, logdir
                + IngestConstants.LOG_FILE_INGEST, true);

        return FileAppender;
    }

    /**
     * Writes time to lastIngest.txt file, uses current _lastingest value
     * @throws IOException
     */
    public void setLastIngestPropFile() {
        try {
            this.setLastIngestPropFile(_lastingest);
        } catch (IOException e) {
            log.error("Unable to save last ingest property file: \n" + e.getCause());
        }
    }
    
    /**
     * Writes time to lastIngest.txt file, prevents duplicate ingest.
     * @param time - Time at which the last ingest occured
     * @throws IOException
     */
    public void setLastIngestPropFile(String time) throws IOException {
        Properties fprop = new Properties();
        File f = new File(_harvlog);
        fprop.setProperty(IngestConstants.TAG_LAST_INGEST, time);
        fprop.store(new FileOutputStream(f), "Last Ingest Dir");
    }

    /**
     * @return Returns the datastreamprefix
     */
    public String getDataStreamPrefix() {
        return _datastreamprefix;
    }
    /**
     * @param arcidprefix The _datastreamprefix to set.
     */
    public void setDataStreamPrefix(String arcidprefix) {
        this._datastreamprefix = arcidprefix;
    }
    /**
     * @return Returns the _lastingestdir.
     */
    public String getLastIngest() {
        return _lastingest;
    }
    /**
     * @param lastingest The _lastingestdir to set.
     */
    public void setLastIngest(String lastingest) {
        this._lastingest = lastingest;
    }
    /**
     * @return Returns the _maxsize.
     */
    public String getMaxsize() {
        return _maxsize;
    }
    /**
     * @param maxsize The _maxsize to set.
     */
    public void setMaxsize(int maxsize) {
        _maxsize = String.valueOf(maxsize);
    }
    /**
     * @return Returns the _org.
     */
    public String getSidPrefix() {
        return _sidPrefix;
    }
    /**
     * @param org The _sidPrefix to set.
     */
    public void setSidPrefix(String org) {
        _sidPrefix = org;
    }
    /**
     * @return Returns the ingest API plugin.
     */
    public  String getPlugin() {
        return _plugin;
    }
    /**
     * @param plugin - Defines the ingest API plug-in class, ensure class is in path.
     */
    public void setPlugin(String plugin) {
        _plugin = plugin;
    }
    /**
     * @return Returns the project name.
     */
    public String getProject() {
        return _project;
    }
    /**
     * @param project - Defines the project name.
     */
    public void setProject(String project) {
        _project = project;
    }
    /**
     * @return Returns the project directory.
     */
    public String getProjectDir() {
        return _projectdir;
    }
    /**
     * @param projectdir - Define the project directory.
     */
    public void setProjectDir(String projectdir) {
        _projectdir = projectdir;
    }
    /**
     * @return Returns the xmltape directory.
     */
    public String getXmlTapesDir() {
        return _xmltapesdir;
    }
    /**
     * @param xmltapesdir - Defines the xmltape directory
     */
    public void setXmlTapesDir(String xmltapesdir) {
        _xmltapesdir = xmltapesdir;
    }
    /**
     * Compares date1 to date2
     * @param obj1 - Date 1
     * @param obj2 - Date 2
     * @return the value 0 if the argument is a Date equal to this Date; a value 
     * less than 0 if the argument is a Date after this Date; and a value greater 
     * than 0 if the argument is a Date before this Date.
     */
    public int compare(Object obj1,Object obj2) 
    { 
      Date date1 =(Date) obj1;
      Date date2 =(Date) obj2;
      return  date1.compareTo(date2); 
    } 
}