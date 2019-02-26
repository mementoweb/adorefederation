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

package gov.lanl.federator.processor;

import gov.lanl.util.oai.oaiharvesterwrapper.Record;
import gov.lanl.util.xslt.XSLTPool;
import gov.lanl.util.xslt.XSLTTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

/**
 * Convert a list of DIDLs to other formats using stylesheet
 * 
 * This code is optimzed with multiple threads.
 * 
 */
public class DIDLProcessor {

    private Iterator recIter;

    private static int MAX_THREADS = 20;

    private HashMap<String, String> results = new HashMap<String, String>();

    private int numthreads;

    private ExecutorService executor;
    
    private String xsltFile;

    protected Logger log = Logger.getLogger(DIDLProcessor.class.getName());

    /**
     * Construtor without stylesheet file
     * 
     * @param oairecords
     *            list of OAI Record instances
     * @param numthreads
     *            number of threads used to process records
     * @throws Exception
     */
    public DIDLProcessor(ArrayList<Record> oairecords, int numthreads)
            throws Exception {
        this(oairecords, numthreads, null);
    }

    /**
     * Create a new DIDLProcessor instance
     * 
     * @param oairecords
     *            list of OAI Record instances
     * @param numthreads
     *            number of threads used to process records
     * @param xsltFile
     *            style sheet to be appied to all records
     * @throws Exception
     */
    public DIDLProcessor(ArrayList<Record> oairecords, int numthreads, String xsltFile) throws Exception {

        if ((numthreads < 1) || (numthreads > MAX_THREADS)) {
            log.error("not a valid number of threads, using default of 1; adore-federator.numberthreads range is 1-20");
            numthreads = 1;
        }
        recIter = oairecords.iterator();
        if (oairecords.size() < numthreads)
            this.numthreads = oairecords.size();
        else
            this.numthreads = numthreads;
        executor = Executors.newFixedThreadPool(this.numthreads);
        results = new HashMap<String, String>();
        this.xsltFile = xsltFile;
    }

    /**
     * Process the list of OAI Records
     * 
     * @return a hashmap containing identifier:record pairs
     * @throws DIDLProcessorException
     */
    public HashMap<String, String> process() throws DIDLProcessorException {
        ArrayList<FutureTask> tasks = new ArrayList<FutureTask>();
        while (recIter.hasNext()) {
            FutureTask<String> future = new FutureTask<String>(new DIDLThread((Record) recIter.next()), null);
            tasks.add(future);
            executor.execute(future);
        }
        try {
            executor.shutdown();
            while (!isDone(tasks)) {
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            log.error("An error occurred during thread interruption: " + e.getMessage());
        }

        log.debug("finish process");
        return results;
    }

    private static boolean isDone(ArrayList<FutureTask> list) {
        for (FutureTask ft : list) {
           if (!ft.isDone()) 
               return false;
        }
        return true;
    }

    /**
     * Adds the processed record to the results queue
     * 
     * @param r
     *            the processed OAI Record
     * @param didlrecord
     *            the stylesheet processed record
     */
    synchronized void finishRecord(Record r, String didlrecord) {
        results.put(r.getHeader().getIdentifier(), didlrecord);
    }

    /**
     * Handles the processing of OAI Records on multiple threads
     */
    class DIDLThread implements Runnable {
        boolean done = false;

        Record record;

        String errormsg = "";

        public DIDLThread(Record rec) {
            record = rec;
        }

        public void run() {
            String xmloutput = null;
            try {
                xmloutput = "";
                // for native processing
                if (xsltFile == null)
                    xmloutput = record.getMetadata();
                else {
                    log.debug("xsltFile=" + xsltFile);
                    log.debug(record.getMetadata());
                    XSLTTransformer xtran = XSLTPool
                            .borrowObject(xsltFile);
                    xmloutput = xtran.transform(record.getMetadata());
                    XSLTPool.returnObject(xsltFile, xtran);
                }

                // stripe XML processing instructions
                if (xmloutput.startsWith("<?xml"))
                    xmloutput = xmloutput
                            .substring(xmloutput.indexOf("?>") + 2);
                finishRecord(record, xmloutput);
            } catch (java.util.NoSuchElementException ex) {
                done = true;
            } catch (Exception ex) {
                log.warn(ex.toString());
                log.warn("xmloutput=" + xmloutput);
                ex.printStackTrace();
                done = false;
                errormsg = ex.toString();
            }
        }

        public boolean isDone() {
            return done;
        }

        public String getErrorMessage() {
            return errormsg;
        }
    }
}