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

package gov.lanl.disseminator.matchmaker;

import gov.lanl.disseminator.model.ContextObjectContainer;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

public class MatchMakerCo {
	public static RuleBase ruleBase = null;

	private static final String RULE_FILE = "/etc/rule/dlapps.java.drl";
	private static final String RULE_FILE_S = "/etc/rule/perm.drl";
	private static final String DSL_FILE = "/etc/rule/co.java.dsl";

	// Resource r;
	List l = new ArrayList();
	MultiMap mhm = new MultiHashMap();

	/*
	 * public JCRMatchMaker() throws Exception { URL url =
	 * JCRMatchMaker.class.getResource("/Users/ludab/wk/RABBIT/jcr.java.drl");
	 * System.out.println(url.toString()); this(url); }
	 */

	// public MatchMakerCo() throws Exception {
	// this(MatchMakerCo.class.getResource(RULE_FILE),MatchMakerCo.class.getResource(DSL_FILE));
	// }
	static {
		try {
			/*
			 * InputStream is = Thread.currentThread().getContextClassLoader()
			 * .getResourceAsStream(RULE_FILE);
			 * 
			 * if (is == null) { System.out.println("input stream rule is
			 * null"); } InputStream isl =
			 * Thread.currentThread().getContextClassLoader()
			 * .getResourceAsStream(DSL_FILE);
			 * 
			 * if (isl == null) { System.out.println("input stream dsl is
			 * null"); } // PackageBuilder builder = new PackageBuilder(); //
			 * builder.addPackageFromDrl( new InputStreamReader( //
			 * JCRMatchMaker.class.getResourceAsStream( RULE_FILE ) ) );
			 * 
			 * PackageBuilder builder = new PackageBuilder(); // Reader source =
			 * new // InputStreamReader(MatchMakerCo.class.getResourceAsStream( //
			 * RULE_FILE )); // Reader dsl = new InputStreamReader( //
			 * MatchMakerCo.class.getResourceAsStream( DSL_FILE ));
			 * 
			 * Reader rulesource = new InputStreamReader(is); Reader dslsource =
			 * new InputStreamReader(isl); System.out.println("readers ok");
			 * builder.addPackageFromDrl(rulesource, dslsource);
			 * System.out.println("builder ok"); ruleBase =
			 * RuleBaseFactory.newRuleBase();
			 * ruleBase.addPackage(builder.getPackage());
			 */
			ruleBase = readRule();
			System.out.println("it is compilation problem");

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public MatchMakerCo() {

	}

	public void defList(List l) {
		this.l = l;
	}

	public void defMap(MultiMap m) {
		this.mhm = m;
	}

	/**
	 * public MatchMakerCo() throws Exception{ try { InputStream is =
	 * Thread.currentThread().getContextClassLoader().getResourceAsStream(
	 * RULE_FILE);
	 * 
	 * if (is==null) { System.out.println("input stream rule is null"); }
	 * InputStream isl =
	 * Thread.currentThread().getContextClassLoader().getResourceAsStream(
	 * DSL_FILE);
	 * 
	 * if (isl==null) { System.out.println("input stream dsl is null"); }
	 * //PackageBuilder builder = new PackageBuilder();
	 * //builder.addPackageFromDrl( new InputStreamReader(
	 * JCRMatchMaker.class.getResourceAsStream( RULE_FILE ) ) );
	 * 
	 * PackageBuilder builder = new PackageBuilder(); //Reader source = new
	 * InputStreamReader(MatchMakerCo.class.getResourceAsStream( RULE_FILE ));
	 * //Reader dsl = new InputStreamReader(
	 * MatchMakerCo.class.getResourceAsStream( DSL_FILE ));
	 * 
	 * Reader rulesource = new InputStreamReader(is); Reader dslsource = new
	 * InputStreamReader(isl); System.out.println("readers ok");
	 * builder.addPackageFromDrl(rulesource,dslsource);
	 * System.out.println("builder ok"); ruleBase =
	 * RuleBaseFactory.newRuleBase(); ruleBase.addPackage(
	 * builder.getPackage());
	 * 
	 * 
	 * 
	 * //URL url = JCRMatchMaker.class.getResource("jcr.java.drl"); //URL url =
	 * new URL("file:///Users/ludab/wk/RABBIT/jcr.java.drl");
	 * //System.out.println(url.toString()); //System.out.println("here");
	 * //RuleSetLoader ruleSetLoader = new RuleSetLoader();
	 * //ruleSetLoader.addFromUrl(url); //dumpGeneratedSourceToDisk(
	 * ruleSetLoader );
	 * 
	 * //RuleBaseLoader ruleBaseLoader = new RuleBaseLoader();
	 * //ruleBaseLoader.addFromRuleSetLoader(ruleSetLoader); //ruleBase =
	 * ruleBaseLoader.buildRuleBase(); } catch (Exception ex){ throw new
	 * Exception(ex); } }
	 */
	public List getResource() {
		return l;
	}

	public MultiMap getMap() {
		return mhm;
	}

	public void matchservice(ContextObjectContainer obj) throws Exception {
		try {
			// WorkingMemory workingMemory = ruleBase.newStatefulSession();
			StatefulSession session = ruleBase.newStatefulSession();
			// WorkingMemory workingMemory = (WorkingMemory)
			// ruleBase.newStatelessSession();

			// StatelessSession session = ruleBase.newStatelessSession();
			// session.setGlobal("l", l);
			// session.setGlobal("mhm", mhm);
			session.setGlobal("l", l);
			session.setGlobal("mhm", mhm);
			// session.setGlobal("mhm", mhm);
			session.insert(obj);
			// session.execute(obj);
			// System.out.println("after node");

			session.fireAllRules();
			session.dispose();

		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	public void match(ContextObjectContainer obj) throws Exception {
		// recursive checking of each node
		try {
			// StatelessSession session = ruleBase.newStatelessSession();
			// WorkingMemory workingMemory = ruleBase.newStatefulSession();
			StatefulSession session = ruleBase.newStatefulSession();
			// StatelessSession session = ruleBase.newStatelessSession();
			// WorkingMemory workingMemory = (WorkingMemory)
			// ruleBase.newStatelessSession();

			// session.setGlobal("l", l);
			// session.setGlobal("mhm", mhm);
			// session.execute(obj);
			session.setGlobal("l", l);
			session.setGlobal("mhm", mhm);

			session.insert(obj);
			//ÊSystem.out.println("after node");
			session.fireAllRules();
			session.dispose();

			while (obj.hasNext()) {
				obj.nextReferent();
				match(obj);
			}

		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	public RuleBase getRuleBase() {
		return ruleBase;
	}

	private static RuleBase readRule() throws Exception {
		// PackageBuilderConfiguration pkgBuilderCfg = new
		// PackageBuilderConfiguration();
		// pkgBuilderCfg.setCompiler(PackageBuilderConfiguration.JANINO);

		PackageBuilder builder = new PackageBuilder();

		Reader reader = new InputStreamReader(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(RULE_FILE));
		builder.addPackageFromDrl(reader);
		reader = new InputStreamReader(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(RULE_FILE_S));
		builder.addPackageFromDrl(reader);

		// JavaDialectConfiguration javaConf = (JavaDialectConfiguration)
		// pkgBuilderCfg.getDialectConfiguration( "java" );

		Package pkg = builder.getPackage();
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(pkg);
		return ruleBase;
	}

}
