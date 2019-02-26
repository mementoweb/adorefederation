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

package gov.lanl.disseminator;

import java.io.IOException;
import java.net.URL;
import java.net.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 8169261258465916584L;

	public void doGet(HttpServletRequest request, HttpServletResponse res)
			throws ServletException, IOException {

		String file = request.getRequestURI();
		URL reconstructedURL = new URL(request.getScheme(), request
				.getServerName(), request.getServerPort(), file);
		String url = reconstructedURL.toString();
		// System.out.println ("url"+ url);
		String user_agent = request.getHeader("user-agent");
		// System.out.println(user_agent);
		String frm = "info:ofi/fmt:kev:mtx:adore"; // urlencode it
		frm = java.net.URLEncoder.encode(frm, "UTF8");
		// String
		// fmtstr="&svc_val_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Aadore&rft_val_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Aadore";
		String fmtstr = "&svc_val_fmt=" + frm + "&rft_val_fmt=" + frm;
		// String query = request.getQueryString();
		String id = request.getParameter("rft_id");
		String service = request.getParameter("svc_id");

		String access = request.getParameter("svc.access");
		String permstr = "";

		if (access == null) {
			permstr = "&svc.access=LIBRARY";
		}

		if (service.contains("proxy")) {
			id = java.net.URLDecoder.decode(id);
			// System.out.println("from top servlet:" +id);
			res.reset();
			res.sendRedirect(id);
			return;
			// RequestDispatcher dispatcher =
			// getServletContext().getRequestDispatcher(id);

			// request.getRequestDispatcher("/template.jsp");
			// dispatcher.forward(request, res);
		}

		if (user_agent.indexOf("Mozilla") >= 0) {
			// query = query + "req_id=human";
			// System.out.println(query);
			// request.setAttribute("req_id","human");
			//System.out.println("detected mozilla");
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(
							"/myservice?req_id=human" + fmtstr + "&res_id="
									+ java.net.URLEncoder.encode(url, "UTF8")
									+ permstr);
			// request.getRequestDispatcher("/template.jsp");
			dispatcher.forward(request, res);
		} else {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(
							"/myservice?res_id="
									+ java.net.URLEncoder.encode(url, "UTF8")
									+ fmtstr + permstr);
			// request.getRequestDispatcher("/template.jsp");
			dispatcher.forward(request, res);
		}
		// res.sendRedirect("http://penguin.lanl.gov:8090/disseminator/service?"+query
		// + "&res_id="+URLEncoder.encode(url,"UTF8"));
	}
}
