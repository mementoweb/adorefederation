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

package gov.lanl.xmlsig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class XMLSigProperties {

    public static final String TAG_KEY_STORE_DIR = "xmlsig.keystore.dir";
    public static final String TAG_KEY_STORE_TYPE = "xmlsig.keystore.type";
    public static final String TAG_KEY_STORE_FILE = "xmlsig.keystore.file";
    public static final String TAG_KEY_STORE_PASS = "xmlsig.keystore.pass";
    public static final String TAG_PRIVATE_KEY_ALIAS = "xmlsig.privatekey.alias";
    public static final String TAG_PRIVATE_KEY_PASS = "xmlsig.privatekey.pass";
    public static final String TAG_CERT_ALIAS = "xmlsig.cert.alias";
    public static final String TAG_CERT_URL = "xmlsig.cert.url";
    
    private String keyStoreDir = null;
    private String keyStoreType = null;
    private String keyStoreFile = null;
    private String keyStorePass = null;
    private String privateKeyAlias = null;
    private String privateKeyPass = null;
    private String certificateAlias = null;
    private String certificateURL = null;
    
    public XMLSigProperties() {
        
    }
    
    public XMLSigProperties(String sigPropFile) {
        try {
            FileInputStream fis = new FileInputStream(sigPropFile);
            Properties props = new Properties();
            props.load(fis);
            this.load(props);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void load(Properties props) {
        keyStoreDir = props.getProperty(TAG_KEY_STORE_DIR, keyStoreDir);
        keyStoreType = props.getProperty(TAG_KEY_STORE_TYPE, keyStoreType);
        keyStoreFile = props.getProperty(TAG_KEY_STORE_FILE, keyStoreFile);
        keyStorePass = props.getProperty(TAG_KEY_STORE_PASS, keyStorePass);
        privateKeyAlias = props.getProperty(TAG_PRIVATE_KEY_ALIAS, privateKeyAlias);
        privateKeyPass = props.getProperty(TAG_PRIVATE_KEY_PASS, privateKeyPass);
        certificateAlias = props.getProperty(TAG_CERT_ALIAS, certificateAlias);
        certificateURL = props.getProperty(TAG_CERT_URL, certificateURL);
    }
    
    /**
     * @return Returns the certificateAlias.
     */
    public String getCertificateAlias() {
        return certificateAlias;
    }
    /**
     * @param certificateAlias The certificateAlias to set.
     */
    public void setCertificateAlias(String certificateAlias) {
        this.certificateAlias = certificateAlias;
    }
    /**
     * @return Returns the certificateURL.
     */
    public String getCertificateURL() {
        return certificateURL;
    }
    /**
     * @param certificateURL The certificateURL to set.
     */
    public void setCertificateURL(String certificateURL) {
        this.certificateURL = certificateURL;
    }
    /**
     * @return Returns the keyStoreDir.
     */
    public String getKeyStoreDir() {
        return keyStoreDir;
    }
    /**
     * @param keyStoreDir The keyStoreDir to set.
     */
    public void setKeyStoreDir(String keyStoreDir) {
        this.keyStoreDir = keyStoreDir;
    }
    /**
     * @return Returns the keyStoreFile.
     */
    public String getKeyStoreFile() {
        return keyStoreFile;
    }
    /**
     * @param keyStoreFile The keyStoreFile to set.
     */
    public void setKeyStoreFile(String keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }
    /**
     * @return Returns the keyStorePass.
     */
    public String getKeyStorePass() {
        return keyStorePass;
    }
    /**
     * @param keyStorePass The keyStorePass to set.
     */
    public void setKeyStorePass(String keyStorePass) {
        this.keyStorePass = keyStorePass;
    }
    /**
     * @return Returns the keyStoreType.
     */
    public String getKeyStoreType() {
        return keyStoreType;
    }
    /**
     * @param keyStoreType The keyStoreType to set.
     */
    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }
    /**
     * @return Returns the privateKeyAlias.
     */
    public String getPrivateKeyAlias() {
        return privateKeyAlias;
    }
    /**
     * @param privateKeyAlias The privateKeyAlias to set.
     */
    public void setPrivateKeyAlias(String privateKeyAlias) {
        this.privateKeyAlias = privateKeyAlias;
    }
    /**
     * @return Returns the privateKeyPass.
     */
    public String getPrivateKeyPass() {
        return privateKeyPass;
    }
    /**
     * @param privateKeyPass The privateKeyPass to set.
     */
    public void setPrivateKeyPass(String privateKeyPass) {
        this.privateKeyPass = privateKeyPass;
    }

    
    
}
