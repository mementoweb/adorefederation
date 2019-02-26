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

package gov.lanl.disseminator.service.toc;

public class MakeURI {

	/**
	 * @param args
	 */

	public static String makeProxy(boolean isOpenURL, String id, String baseurl) {
		String url;
		if (isOpenURL) {
			url = baseurl + "?url_ver=Z39.88-2004&svc_id="
					+ java.net.URLEncoder.encode("info:lanl-repo/svc/proxy")
					+ "&rft_id=" + java.net.URLEncoder.encode(id);
		} else {
			url = baseurl + "/proxy?what=" + java.net.URLEncoder.encode(id);
		}
		return url;
	}

	public static String makeURA(boolean isOpenURL, String id, String baseurl) {
		String url;
		if (isOpenURL) {
			url = baseurl
					+ "?url_ver=Z39.88-2004"
					+ "&svc_id="
					+ java.net.URLEncoder
							.encode("info:lanl-repo/svc/aggregation")
					+ "&rft_id=" + java.net.URLEncoder.encode(id);
		} else {
			url = baseurl + "/aggregation?what="
					+ java.net.URLEncoder.encode(id);
		}
		return url;
	}

	public static String makeUR(boolean isOpenURL, String id, String baseurl) {
		String url;
		if (isOpenURL) {
			url = baseurl + "?url_ver=Z39.88-2004" + "&svc_id="
					+ java.net.URLEncoder.encode("info:lanl-repo/svc/rem.atom")
					+ "&rft_id=" + java.net.URLEncoder.encode(id);
		} else {
			url = baseurl + "/rem.atom?what=" + java.net.URLEncoder.encode(id);
		}
		return url;
	}

	public static String makeAR(boolean isOpenURL, String id, String srv,
			String baseurl) {

		String url;
		if (isOpenURL) {
			url = baseurl + "?url_ver=Z39.88-2004&rft_id="
					+ java.net.URLEncoder.encode(id) + "&svc_id="
					+ java.net.URLEncoder.encode(srv);
		} else {
			String srvstr = srv.substring(srv.lastIndexOf("/") + 1);
			url = baseurl + "/" + srvstr + "?what="
					+ java.net.URLEncoder.encode(id);
		}
		return url;
	}

	public static String makeAR_forBrowser(boolean isOpenURL, String id,
			String srv, String baseurl) {

		String url;
		if (isOpenURL) {
			url = baseurl + "?url_ver=Z39.88-2004&rft_id=" + id + "&svc_id="
					+ srv;
		} else {
			String srvstr = srv.substring(srv.lastIndexOf("/") + 1);
			url = baseurl + "/" + srvstr + "?what=" + id;
		}
		return url;
	}
}
