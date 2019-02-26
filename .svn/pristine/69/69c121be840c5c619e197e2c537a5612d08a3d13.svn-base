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

package gov.lanl.adore.diag;

import gov.lanl.adore.helper.IdNotFoundException;
import gov.lanl.util.StreamUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AdoreSysDiag {

    public static AdoreProcess getIdLocatorResponse(String baseUrl, String id) throws IdNotFoundException {
        AdoreProcess response = new AdoreProcess();
        try {
            URL url = new URL(baseUrl.toString() + "?url_ver=Z39.88-2004" + "&rft_id=" + URLEncoder.encode(id, "UTF-8"));
            response.setRequest(url.toString());
            HttpURLConnection huc = (HttpURLConnection) (url.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                response.setResponse(new String(StreamUtil.getByteArray(is)));
            } else if (code == 404) {
                throw new IdNotFoundException("Unable to locate specified id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return response;
    }    
   
    public static AdoreProcess getServiceRegistryResponse(String baseUrl, String id) {
        AdoreProcess response = new AdoreProcess();
        try {
            URL url = new URL(baseUrl.toString() + "?url_ver=Z39.88-2004" + "&rft_id=" + URLEncoder.encode(id, "UTF-8") + "&svc_val_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Adc&svc_id=info%3Alanl-repo%2Fsvc%2Fockham");
            response.setRequest(url.toString());
            HttpURLConnection huc = (HttpURLConnection) (url.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                response.setResponse(new String(StreamUtil.getByteArray(is)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return response;
    }

    public static AdoreProcess getRecord(String baseUrl, String id) {
        AdoreProcess response = new AdoreProcess();
        try {
            URL url = new URL(baseUrl.toString() + "?url_ver=Z39.88-2004" + "&rft_id=" + URLEncoder.encode(id, "UTF-8") + "&svc_id=info%3Alanl-repo%2Fsvc%2FgetDIDL");
            response.setRequest(url.toString());
            HttpURLConnection huc = (HttpURLConnection) (url.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                response.setResponse(new String(StreamUtil.getByteArray(is)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return response;
    }

    public static AdoreProcess getDatastream(String baseUrl, String id) {
        AdoreProcess response = new AdoreProcess();
        try {
            URL url = new URL(baseUrl.toString() + "?url_ver=Z39.88-2004" + "&rft_id=" + URLEncoder.encode(id, "UTF-8") + "&svc_id=info%3Alanl-repo%2Fsvc%2FgetDatastream");
            response.setRequest(url.toString());
            HttpURLConnection huc = (HttpURLConnection) (url.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                response.setResponseAsByteArray(StreamUtil.getByteArray(is));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return response;
    }
}
