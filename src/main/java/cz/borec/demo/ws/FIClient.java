package cz.borec.demo.ws;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.XMLUtils;
import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.borec.demo.AppProperties;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.mfcr.fs.eet.schema.v2.BkpDigestType;
import cz.mfcr.fs.eet.schema.v2.BkpElementType;
import cz.mfcr.fs.eet.schema.v2.BkpEncodingType;
import cz.mfcr.fs.eet.schema.v2.ObjectFactory;
import cz.mfcr.fs.eet.schema.v2.OdpovedType;
import cz.mfcr.fs.eet.schema.v2.PkpCipherType;
import cz.mfcr.fs.eet.schema.v2.PkpDigestType;
import cz.mfcr.fs.eet.schema.v2.PkpElementType;
import cz.mfcr.fs.eet.schema.v2.PkpEncodingType;
import cz.mfcr.fs.eet.schema.v2.TrzbaDataType;
import cz.mfcr.fs.eet.schema.v2.TrzbaHlavickaType;
import cz.mfcr.fs.eet.schema.v2.TrzbaKontrolniKodyType;
import cz.mfcr.fs.eet.schema.v2.TrzbaType;



//@Service
public class FIClient {

	// private static final String URI = "http://localhost:8088/mockEETSOAP";
	private static final String URI = "https://pg.eet.cz:443/eet/services/EETServiceSOAP/v2";

	private static FIClient instance;
	// @Autowired
	private WebServiceTemplate webServiceTemplate;
	private ObjectFactory factory;
	private String cashId;
	private int restaurantId;
	private String customerDIC;

	private KeyStoreSigner keyStoreSigner;

	public boolean callFIPublic(OrderDTO orderDTO, boolean storno) {
		OdpovedType result = null;
		try {
			result = callFI(orderDTO, storno);
		} catch (Exception e) {
			e.printStackTrace();
		}

		orderDTO.setFirstFICall(false);
		if (result != null) {
			if (result.getChyba() == null) {
				if(storno) {
					orderDTO.setFIKStorno(result.getPotvrzeni().getFik());
				}
				else {
					orderDTO.setFIK(result.getPotvrzeni().getFik());
				}
				orderDTO.setStorno(storno);
				return true;
			} else {
				System.out.println("------------> Error calling FI: " + result.getChyba().getContent());
			}
		}
		return false;
	}

	private OdpovedType callFI(OrderDTO order, boolean storno) throws JAXBException, DatatypeConfigurationException, SignatureException, UnsupportedEncodingException, NoSuchAlgorithmException {

		TrzbaType request = new TrzbaType();

		TrzbaHlavickaType hlavicka = new TrzbaHlavickaType();

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar xMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		xMLGregorianCalendar.setFractionalSecond(BigDecimal.ZERO);
		hlavicka.setDatOdesl(xMLGregorianCalendar);
		hlavicka.setUuidZpravy("b3a09b52-7c87-4014-a496-4c7a53cf9125");

		// hlavicka.setPrvniZaslani(!order.hasBeenSentToFI());
		hlavicka.setPrvniZaslani(order.isFirstFICall());

		TrzbaDataType data = new TrzbaDataType();
		data.setDicPopl(customerDIC);
		data.setIdProvoz(restaurantId);
		data.setIdPokl(cashId);
		String orderId = String.valueOf(order.getId());
		data.setPoradCis(orderId);
		data.setCelkTrzba(storno ? order.getSumAfterDiscount().negate() : order.getSumAfterDiscount());
		c.setTime(order.getDate());
		xMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		xMLGregorianCalendar.setFractionalSecond(BigDecimal.ZERO);
		data.setDatTrzby(xMLGregorianCalendar);

		TrzbaKontrolniKodyType kody = new TrzbaKontrolniKodyType();

		PkpElementType pkp = new PkpElementType();
		pkp.setDigest(PkpDigestType.SHA_256);
		pkp.setCipher(PkpCipherType.RSA_2048);
		pkp.setEncoding(PkpEncodingType.BASE_64);

		String plainText = customerDIC + "|" + restaurantId + "|" + cashId + "|" + orderId + "|" + xMLGregorianCalendar + "|" + order.getSum();
		byte[] pkpValue = keyStoreSigner.sign(plainText);
		pkp.setValue(pkpValue);

		BkpElementType bkp = new BkpElementType();
		bkp.setDigest(BkpDigestType.SHA_1);
		bkp.setEncoding(BkpEncodingType.BASE_16);
		bkp.setValue(createBkp(pkpValue)); // "03ec1d0e-6d9f77fb-1d798ccb-f4739666-a4069bc3");

		kody.setBkp(bkp);
		kody.setPkp(pkp);

		request.setHlavicka(hlavicka);
		request.setData(data);
		request.setKontrolniKody(kody);

		JAXBElement<TrzbaType> trzbaDataType = factory.createTrzba(request);

		@SuppressWarnings("unchecked")
		JAXBElement<OdpovedType> response = (JAXBElement<OdpovedType>) webServiceTemplate.marshalSendAndReceive(trzbaDataType, new WebServiceMessageCallback() {

			public void doWithMessage(WebServiceMessage message) throws TransformerException, IOException {

				try {
					SoapMessage soapMessage = (SoapMessage) message;
					SoapHeader header = soapMessage.getSoapHeader();
					SoapBody body = soapMessage.getSoapBody();

					// Inicializace knihovny
					org.apache.xml.security.Init.init();

					// Nahrání zprávy SOAP s požadavkem
					javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
					dbf.setNamespaceAware(true);
					dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
					javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
					db.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());

					org.w3c.dom.Document doc = soapMessage.getDocument(); // transform(soapMessage); //.parse(new java.io.FileInputStream(new File("Request.xml")));

					// Vyhledání hlavicky SOAP
					Element headerElement = null;
					NodeList nodes = doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Header");
					if (nodes.getLength() == 0) {
						// Pridání elementu hlavicky SOAP
						headerElement = doc.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "Header");
						nodes = doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Envelope");
						if (nodes != null) {
							Element envelopeElement = (Element) nodes.item(0);
							headerElement.setPrefix(envelopeElement.getPrefix());
							envelopeElement.appendChild(headerElement);
						}
					} else {
						// Nalezeny elementy hlavicek SOAP
						headerElement = (Element) nodes.item(0);
					}
					
					final String XMLNS_WSU = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
					final String XSD_WSSE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
					
					final javax.xml.soap.SOAPFactory sf = javax.xml.soap.SOAPFactory.newInstance();
					Element security = doc.createElementNS(XSD_WSSE, "wsee:Security");
					security.setAttribute("xmlns:wsu", XMLNS_WSU);
					security.setAttribute("xmlns:wsee", XSD_WSSE);
					security.setAttribute("SOAP-ENV:mustUnderstand", "1");
					final SOAPElement securityElement = sf.createElement(security);
					//final Name securityElement = sf.createName("Security", "wsse", XSD_WSSE);
					//header.addHeaderElement(securityElement);
					headerElement.appendChild(securityElement);
					
					
					NodeList nodeList = doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Body");
					Element bodyElement = (Element) nodeList.item(0);
					bodyElement.setAttribute("xmlns:wsu", XMLNS_WSU);
					bodyElement.setAttribute("wsu:Id", "ID_BODY");
					bodyElement.setAttribute("ID", "ID_BODY");

					
					
					
					final javax.xml.soap.SOAPElement authElement = sf.createElement("BinarySecurityToken", "wsse", XSD_WSSE);
					authElement.setAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
					authElement.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
					authElement.setAttribute("wsu:Id", "SecurityToken");
					authElement.addAttribute(new QName("xmlns:wsu"), XMLNS_WSU);
					
					// Pridání certifikátu a informací o verejném klíci pro verifikátor
					KeyStore keystore = keyStoreSigner.getKeystore();
					X509Certificate cert = (X509Certificate) keystore.getCertificate(keyStoreSigner.getAlias());
					
					authElement.addTextNode(new String(Base64.getEncoder().encode(cert.getEncoded()))); //StringUtils.replace("abcd", "\n", ""));
					
					securityElement.addChildElement(authElement);
					

/*					Element security = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
					Element binarySecurityToken = doc.createElementNS( "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken");
					security.appendChild(binarySecurityToken);
*/		            							
					// Vytvorení instance trídy XMLSignature
					XMLSignature sig = new XMLSignature(doc, "", XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256); // .ALGO_ID_SIGNATURE_DSA);
					//binarySecurityToken.appendChild(sig.getElement());
					Element sigElement = sig.getElement();
					securityElement.appendChild(sigElement);

					
					// Specifikace transformace
					Transforms transforms = new Transforms(doc);
					//transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
					transforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS); //.TRANSFORM_C14N_WITH_COMMENTS);
					sig.addDocument("#ID_BODY", transforms, "http://www.w3.org/2001/04/xmlenc#sha256"); //org.apache.xml.security.utils.Constants.ALGO_ID_DIGEST_SHA1);

					sig.addKeyInfo(cert);
					sig.addKeyInfo(cert.getPublicKey());
					// Podepsání
					sig.sign(keyStoreSigner.getPrivateKey());
					
					NodeList keyInfos = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
					Node a = keyInfos.item(0);
					Element securityTokenReference = doc.createElementNS(XSD_WSSE, "wsee:SecurityTokenReference");
					securityTokenReference.setAttribute("xmlns:wsu", XMLNS_WSU);
					securityTokenReference.setAttribute("xmlns:wsee", XSD_WSSE);
					securityTokenReference.setAttribute("wsu:Id", "1");
					Element reference = doc.createElementNS(XSD_WSSE, "wsee:Reference");
					reference.setAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
					reference.setAttribute("URI", "#SecurityToken");
					securityTokenReference.appendChild(reference);
					a.appendChild(securityTokenReference);
					
					// Uložení podepsané zprávy do souboru
					FileOutputStream f = new FileOutputStream(new File("SignedRequest.xml"));
					//FileOutputStream f2 = new FileOutputStream(new File("SignedRequest2.xml"));
					XMLUtils.outputDOMc14nWithComments(doc, f);
					//XMLUtils.outputDOMc14nWithComments(transforms, f);
					f.close();


					

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				/*
				 * StringSource headerSource = new StringSource("<credentials xmlns=\"http://example.com/auth\">\n " +
				 * "<username>" + "user" + "</username>\n  " +
				 * "<password>" + "pass" + "</password>\n  " +
				 * "</credentials>");
				 * Transformer transformer = TransformerFactory.newInstance().newTransformer();
				 * transformer.transform(headerSource, header.getResult());
				 * 
				 * //--- just print:
				 * ByteArrayOutputStream out = new ByteArrayOutputStream();
				 * soapMessage.writeTo(out);
				 * String strMsg = new String(out.toByteArray());
				 * System.out.println(strMsg);
				 * System.out.println();
				 */ }
		});

		if(response.getValue().getChyba() != null) {
			System.out.println(response.getValue().getChyba().getContent());
		}
		else {
			System.out.println("FIK: " + response.getValue().getPotvrzeni().getFik());
		}

		return response.getValue();
	}

	private String createBkp(byte[] pkpValue) throws NoSuchAlgorithmException {
		MessageDigest cript = MessageDigest.getInstance("SHA-1");
		cript.reset();
		cript.update(pkpValue);
		byte[] bkp = cript.digest();
		String s = javax.xml.bind.DatatypeConverter.printHexBinary(bkp);
		
		String ss = String.format("%02X%02X%02X%02X-%02X%02X%02X%02X-%02X%02X%02X%02X-%02X%02X%02X%02X-%02X%02X%02X%02X", bkp[0], bkp[1], bkp[2], bkp[3], bkp[4], bkp[5], bkp[6], bkp[7], bkp[8], bkp[9], bkp[10], bkp[11], bkp[12], bkp[13], bkp[14], bkp[15], bkp[16], bkp[17], bkp[18], bkp[19]);
		
		return ss;
	}

	/*
	 * public WebServiceTemplate getWebServiceTemplate() {
	 * return webServiceTemplate;
	 * }
	 * 
	 * public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
	 * this.webServiceTemplate = webServiceTemplate;
	 * }
	 */
	public FIClient() throws JAXBException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		super();

		webServiceTemplate = new org.springframework.ws.client.core.WebServiceTemplate();
		factory = new ObjectFactory();
		keyStoreSigner = new KeyStoreSigner();

		JAXBContext c = JAXBContext.newInstance(cz.mfcr.fs.eet.schema.v2.ObjectFactory.class.getPackage().getName(),
				cz.mfcr.fs.eet.schema.v2.ObjectFactory.class.getClassLoader());
		final Marshaller m = c.createMarshaller();
		webServiceTemplate.setMarshaller(new org.springframework.oxm.Marshaller() {

			@Override
			public boolean supports(Class<?> arg0) {

				return true;
			}

			@Override
			public void marshal(Object arg0, Result arg1) throws IOException, XmlMappingException {
				try {
					m.marshal(arg0, arg1);

					StringWriter writer = new StringWriter();
					Transformer transformer = TransformerFactory.newInstance().newTransformer();
					transformer.transform(new DOMSource(((DOMResult) arg1).getNode()), new StreamResult(writer));
					String xml = writer.toString();
					System.out.println(xml);
					System.out.println(arg1.toString());
				} catch (Exception e) {

					e.printStackTrace();
					throw new RuntimeException(e);
				}

			}
		});

		final Unmarshaller un = c.createUnmarshaller();
		webServiceTemplate.setUnmarshaller(new org.springframework.oxm.Unmarshaller() {

			@Override
			public Object unmarshal(Source arg0) throws IOException, XmlMappingException {

				try {
					return un.unmarshal(arg0);
				} catch (JAXBException e) {

					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}

			@Override
			public boolean supports(Class<?> arg0) {

				return true;
			}
		});

		webServiceTemplate.setDefaultUri(URI);

		cashId = AppProperties.getProperties().getCashId();
		restaurantId = AppProperties.getProperties().getRestaurantId();
		customerDIC = AppProperties.getProperties().getCustomerICO();

	}

	public static void main(String[] args) throws JAXBException, DatatypeConfigurationException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, SignatureException, CertificateException, FileNotFoundException, IOException {
		FIClient client = new FIClient();
		OrderDTO order = new OrderDTO();
		OrderItemDTO orderItem = new OrderItemDTO();
		SalesProductEntity product = new SalesProductEntity();
		orderItem.setProduct(product);
		orderItem.setAmount(1);
		orderItem.setPrice(BigDecimal.TEN);
		order.setItems(Collections.singletonList(orderItem));
		order.setDate(new Date());
		OdpovedType a = client.callFI(order, false);
		//System.out.println(a.getChyba().getKod());
		System.out.println(Long.MAX_VALUE);
	}

	public static FIClient getInstance() throws JAXBException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		if (instance == null) {
			instance = new FIClient();
		}
		return instance;
	}

}
