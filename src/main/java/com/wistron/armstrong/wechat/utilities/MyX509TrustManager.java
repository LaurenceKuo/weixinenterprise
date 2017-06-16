package com.wistron.armstrong.wechat.utilities;


import java.security.KeyStore;
import java.security.cert.*;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


class MyX509TrustManager implements X509TrustManager {
	 /*
	  * The default X509TrustManager returned by IbmX509. We'll delegate
	  * decisions to it, and fall back to the logic in this class if the
	  * default X509TrustManager doesn't trust it.
	  */
	 X509TrustManager pkixTrustManager;
	 MyX509TrustManager() throws Exception {
	  // create a "default" JSSE X509TrustManager.
	  KeyStore ks = KeyStore.getInstance("JKS");
	  //CommonUtil common = new CommonUtil();
	  //String path =  common.getWebInfPath(MyX509TrustManager.class.getResource("").getPath());
	  //ks.load(new FileInputStream(path+"wiweixin.keystore"), "laurencekuo".toCharArray());
	  //ks.load(new FileInputStream("..\\..\\..\\..\\..\\..\\..\\webapp\\WEB-INF\\wiweixin.keystore"), "laurencekuo".toCharArray());
	  ks.load(null, null);
	  TrustManagerFactory tmf =
	   TrustManagerFactory.getInstance("SunX509", "SunJSSE");
	   tmf.init(ks);

	   TrustManager tms [] = tmf.getTrustManagers();

	   /*
	    * Iterate over the returned trustmanagers, look
	    * for an instance of X509TrustManager. If found,
	    * use that as our "default" trust manager.
	    */
	   for (int i = 0; i < tms.length; i++) {
	    if (tms[i] instanceof X509TrustManager) {
	     pkixTrustManager = (X509TrustManager) tms[i];
	     return;
	    }
	   }

	   /*
	    * Find some other way to initialize, or else we have to fail the
	    * constructor.
	    */
	   throw new Exception("Couldn't initialize");
	  }

	  /*
	   * Delegate to the default trust manager.
	   */
	  public void checkClientTrusted(X509Certificate[] chain, String authType)
	   throws CertificateException {
	    try {
	     pkixTrustManager.checkClientTrusted(chain, authType);
	    } catch (CertificateException excep) {
	     // do any special handling here, or rethrow exception.
	    }
	   }

	   /*
	    * Delegate to the default trust manager.
	    */
	   public void checkServerTrusted(X509Certificate[] chain, String authType)
	    throws CertificateException {
	     try {
	      pkixTrustManager.checkServerTrusted(chain, authType);
	     } catch (CertificateException excep) {
	      /*
	       * Possibly pop up a dialog box asking whether to trust the
	       * cert chain.
	       */
	     }
	    }

	    /*
	     * Merely pass this through.
	     */
	    public X509Certificate[] getAcceptedIssuers() {
	     return pkixTrustManager.getAcceptedIssuers();
	    }
	   }
