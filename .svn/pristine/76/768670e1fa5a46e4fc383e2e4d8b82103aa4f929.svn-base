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

package gov.lanl.locator;

import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class IdSearch implements Callable<Vector> {
	static Logger log = Logger.getLogger(IdSearch.class.getName());
	
	private PoolingDataSource ds;

	private byte[] digest;

	private int num;

	public IdSearch(PoolingDataSource ds, byte[] digest, int num) {
		this.ds = ds;
		this.digest = digest;
		this.num = num;
	}

	public Vector call() {
		long start1 = System.currentTimeMillis();
		Vector repo = new Vector();
		Throwable thrown = null;
		try {

			repo = getData(repo);
		} catch (SQLException e) {
			e.printStackTrace();
			log.debug("IdSearch second retry ");

			try {
				long end1 = System.currentTimeMillis();
				double elapsed1 = end1 - start1;
				log.debug("ms since first attemp elapsed1" + elapsed1);

				repo = getData(repo);
			} catch (SQLException e1) {
				e1.printStackTrace();
				thrown = e1;
			}

			if (thrown == null) {
				thrown = e;
			}
			throw new RuntimeException(thrown);
		}
		return repo;
	}

	public Vector getData(Vector repo) throws SQLException {
		long start1 = System.currentTimeMillis();
		Connection conn;
		conn = ds.getConnection();

		long end1 = System.currentTimeMillis();
		double elapsed1 = end1 - start1;
		log.debug("get connection ms:" + elapsed1);
		String sql = "select distinct repo_id from resolver_" + num
				+ " where digest=?";

		PreparedStatement pStmt = conn.prepareStatement(sql);
		pStmt.setBytes(1, digest);
		long start = System.currentTimeMillis();
		ResultSet rs = pStmt.executeQuery();
		long end = System.currentTimeMillis();
		double elapsed = end - start;
		log.debug("execute query ms:" + elapsed);
		while (rs.next()) {
			repo.add(rs.getInt(1));
		}
		pStmt.close();
		conn.close();
		return repo;
	}

}
