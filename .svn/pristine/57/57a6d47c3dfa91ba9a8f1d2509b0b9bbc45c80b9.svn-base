/*
 * Counter.java
 *
 * Created on February 7, 2006, 2:34 PM
 *
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */ 

package gov.lanl.didl.example.visitor;

import info.repo.didl.DIDLVisitorType;
import info.repo.didl.DIDLInfoType;
import info.repo.didl.StatementType;
import info.repo.didl.ComponentType;
import info.repo.didl.ResourceType;
import info.repo.didl.ItemType;
import info.repo.didl.DIDLType;
import info.repo.didl.DescriptorType;
import java.io.FileInputStream;
import java.io.PrintWriter;

/**
 * A sample counter demonstrates the usage of DIDL visitor pattern, the 
 * idea is borrowed from a similar example in xerces SAX Counter.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class Counter implements DIDLVisitorType{
    
    /** Default repetition (1). */
    protected static final int DEFAULT_REPETITION = 1;
    
    
    //number of DIDLInfo
    int nDIDLInfo;
    //number of Item
    int nItem;
    //number of Component
    int nComponent;
    //number of Resource
    int nResource;
    //number of Descriptors
    int nDescriptor;
    //number of Statemetns
    int nStatement;
    
    /** Creates a new instance of Counter */
    public Counter() {
    }
    
    
    /** Prints the results. */
    public void printResults(PrintWriter out, String filename, long time,
            long memory, int repetition) {
        
        // filename.xml: 631 ms (2 DIDLInfo, 4 item, 2 components ,  5 descriptors, 5 statements , 2 resources)
        out.print(filename);
        out.print(": ");
        if (repetition == 1) {
            out.print(time);
        } else {
            out.print(time);
            out.print('/');
            out.print(repetition);
            out.print('=');
            out.print(time/repetition);
        }
        out.print(" ms");
        if (memory != Long.MIN_VALUE) {
            out.print(", ");
            out.print(memory);
            out.print(" bytes");
        }
        
        out.print(" (");
        out.print(nDIDLInfo);
        out.print(" DIDLInfos, ");
        
        out.print(nItem);
        out.print(" items, ");
        
        out.print(nComponent);
        out.print(" components, ");
        
        out.print(nDescriptor);
        out.print(" descriptors, ");
        
        out.print(nStatement);
        out.print(" statements, ");
        
        out.print(nResource);
        out.print(" resources ) ");
        
        out.println();
        out.flush();
        
    }
    
    
    public void visitStart(final DIDLType didl) {
        nDIDLInfo=0;
        nItem=0;
        nComponent=0;
        nResource=0;
        nDescriptor=0;
        nStatement=0;
        
    }
    
    public void visitEnd(final DIDLType didl) {
    }
    
    
    public void visitStart(final ItemType item) {
        nItem++;
        
    }
    
    public void visitEnd(final ItemType item) {
        
    }
    
    public void visitStart(final DIDLInfoType didlInfo) {
        nDIDLInfo++;
    }
    
    public void visitEnd(final DIDLInfoType didlInfo) {
    }
    
    public void visitStart(final DescriptorType descriptor) {
        nDescriptor++;
    }
    
    public void visitEnd(final DescriptorType descriptor) {
    }
    
    public void visitStart(final ComponentType component) {
        nComponent++;
    }
    
    public void visitEnd(final ComponentType component) {
    }
    
    public void visitStart(final StatementType statement) {
        nStatement++;
    }
    
    public void visitEnd(final StatementType statement) {
    }
    
    public void visitStart(final ResourceType resource) {
        nResource++;
    }
    
    public void visitEnd(final ResourceType resource) {
    }
    
    
    public static void main(String[] argv){
        if (argv.length == 0){
            System.err.println("Usage: java gov.lanl.didl.example.visitor.Counter [-x number] file ...");
            System.err.println("    -x number Select number of repetitions.");
            System.exit(1);
        }
        
        Counter counter=new Counter();
        int repetition=DEFAULT_REPETITION;
        PrintWriter out=new PrintWriter(System.out);
        // process arguments
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];
            if (arg.startsWith("-")) {
                String option = arg.substring(1);
                if (option.equals("x")) {
                    if (++i == argv.length) {
                        System.err.println("error: Missing argument to -x option.");
                        continue;
                    }
                    String number = argv[i];
                    try {
                        int value = Integer.parseInt(number);
                        if (value < 1) {
                            System.err.println("error: Repetition must be at least 1.");
                            continue;
                        }
                        repetition = value;
                    } catch (NumberFormatException e) {
                        System.err.println("error: invalid number ("+number+").");
                    }
                    continue;
                }
                System.err.println("error: unknown option ("+option+").");
                continue;
            }
            
            
            
            info.repo.didl.impl.serialize.DIDLDeserializer
                    deserializer = new info.repo.didl.impl.serialize.DIDLDeserializer();
            
            try{
                long timeBefore = System.currentTimeMillis();
                long memoryBefore = Runtime.getRuntime().freeMemory();
                
                for (int j = 0; j < repetition; j++) {
                    FileInputStream fin=null;
                    fin=new FileInputStream(arg);
                    DIDLType didl=(DIDLType)(deserializer.read(fin));
                    didl.accept(counter);
                }
                
                long memoryAfter = Runtime.getRuntime().freeMemory();
                long timeAfter = System.currentTimeMillis();
                
                long time = timeAfter - timeBefore;
                long memory = memoryBefore - memoryAfter;
                
                counter.printResults(out, arg, time, memory, repetition);
                
            } catch (java.io.IOException ex){
                System.err.println(" error reading file");
                ex.printStackTrace();
            }
            catch (info.repo.didl.serialize.DIDLSerializationException ex){
                System.err.println(" DIDL dersialization exception");
                ex.printStackTrace();
                
            }
        }//end for
    }//end main()
}
