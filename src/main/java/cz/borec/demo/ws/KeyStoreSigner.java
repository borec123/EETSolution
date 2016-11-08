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

public class KeyStoreSigner {
	

	KeyStore keystore; // úložiště klíčů obsahující certifikát pro podpis
	String alias = "cz1212121218"; // alias certifikátu v úložišti klíčů
	String password = "eet"; // heslo k privátnímu klíči certifikátu
	private Signature signature;
	
	
	public KeyStoreSigner() throws InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		super();
		keystore =  KeyStore.getInstance("pkcs12");
		InputStream a = getClass().getClassLoader().getResourceAsStream("keystore/01000003.p12");
		keystore.load(a, password.toCharArray());
	      

		signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(getPrivateKey());
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
/*		byte[] retValue = Base64.getEncoder().encode(rsa_text);
		System.out.println("retValue length: " + retValue.length);
		System.out.println("retValue:" + new String(retValue));
*/		return rsa_text;
		//return "Ca8sTbURReQjjgcy/znXBKjPOnZof3AxWK5WySpyMrUXF0o7cz1BP6adQzktODKh2d8soAhn1R/S07lVDTa/6r9xTuI3NBH/+7YfYz/t92eb5Y6aNvLm6tXfOdE3C94EQmT0SEEz9rInGXXP1whIKYX7K0HgVrxjdxCFkZF8Lt12XbahhAzJ47LcPxuBZZp6U6wJ2sWI5os3KY9u/ZChzAUaCec7H56QwkMnu3U3Ftwi/YrxSzQZTmPTpFYKXnYanrFaLDJm+1/yg+VQntoByBM+HeDXigBK+SHaxx+Nd0sSmm1Im4v685BRVdUId+4CobcnSQ3CBsjAhqmIrtWTGQ==".getBytes();
		
	}


	public KeyStore getKeystore() {
		return keystore;
	}
	
	public String getPassword() {
		return password;
	}

	


}
