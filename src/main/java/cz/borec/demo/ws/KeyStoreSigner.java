package cz.borec.demo.ws;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import cz.borec.demo.Constants;
import cz.borec.demo.gui.controls.AppPropertiesProxy;

public class KeyStoreSigner {

	KeyStore keystore; // ÃºloÅ¾iÅ¡tÄ› klÃ­Ä�Å¯ obsahujÃ­cÃ­ certifikÃ¡t pro
						// podpis
	String alias = null; // alias certifikÃ¡tu v ÃºloÅ¾iÅ¡ti klÃ­Ä�Å¯
	String password = "eet"; // heslo k privÃ¡tnÃ­mu klÃ­Ä�i certifikÃ¡tu
	private Signature signature;

	public KeyStoreSigner() throws InvalidKeyException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		super();
		try {
			alias = AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_ICO); // "cz1212121218"
			keystore = KeyStore.getInstance("pkcs12");
			String path = AppPropertiesProxy.get(Constants.CONFIG_CERTIFICATE_PATH);
			InputStream a = (path == null) ? getClass().getClassLoader().getResourceAsStream("keystore/01000003.p12")
					: new FileInputStream(path);
			keystore.load(a, password.toCharArray());

			signature = Signature.getInstance("SHA256withRSA");
			PrivateKey key = getPrivateKey();
			if (key == null) {
				throw new RuntimeException("Nejsp\u00ED\u0161e \u0161patn\u011B nastaven\u00E9 I\u010CO.");
			}
			signature.initSign(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public PrivateKey getPrivateKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (PrivateKey) keystore.getKey(alias, password.toCharArray());
	}

	public String getAlias() {
		return alias;
	}

	public byte[] sign(String message) throws SignatureException, UnsupportedEncodingException {

		signature.update(message.getBytes("UTF-8"));
		byte[] rsa_text = signature.sign();
		System.out.println("rsa_text length: " + rsa_text.length);
		System.out.println("rsa_text:" + rsa_text);
		/*
		 * byte[] retValue = Base64.getEncoder().encode(rsa_text);
		 * System.out.println("retValue length: " + retValue.length);
		 * System.out.println("retValue:" + new String(retValue));
		 */ return rsa_text;
		// return
		// "Ca8sTbURReQjjgcy/znXBKjPOnZof3AxWK5WySpyMrUXF0o7cz1BP6adQzktODKh2d8soAhn1R/S07lVDTa/6r9xTuI3NBH/+7YfYz/t92eb5Y6aNvLm6tXfOdE3C94EQmT0SEEz9rInGXXP1whIKYX7K0HgVrxjdxCFkZF8Lt12XbahhAzJ47LcPxuBZZp6U6wJ2sWI5os3KY9u/ZChzAUaCec7H56QwkMnu3U3Ftwi/YrxSzQZTmPTpFYKXnYanrFaLDJm+1/yg+VQntoByBM+HeDXigBK+SHaxx+Nd0sSmm1Im4v685BRVdUId+4CobcnSQ3CBsjAhqmIrtWTGQ==".getBytes();

	}

	public KeyStore getKeystore() {
		return keystore;
	}

	public String getPassword() {
		return password;
	}

}
