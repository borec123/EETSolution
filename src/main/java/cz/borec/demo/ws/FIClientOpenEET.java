package cz.borec.demo.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
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
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.XMLUtils;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;
import org.w3c.dom.Document;
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
import cz.mfcr.fs.eet.schema.v2.OdpovedChybaType;
import cz.mfcr.fs.eet.schema.v2.OdpovedPotvrzeniType;
import cz.mfcr.fs.eet.schema.v2.OdpovedType;
import cz.mfcr.fs.eet.schema.v2.PkpCipherType;
import cz.mfcr.fs.eet.schema.v2.PkpDigestType;
import cz.mfcr.fs.eet.schema.v2.PkpElementType;
import cz.mfcr.fs.eet.schema.v2.PkpEncodingType;
import cz.mfcr.fs.eet.schema.v2.TrzbaDataType;
import cz.mfcr.fs.eet.schema.v2.TrzbaHlavickaType;
import cz.mfcr.fs.eet.schema.v2.TrzbaKontrolniKodyType;
import cz.mfcr.fs.eet.schema.v2.TrzbaType;
import openeet.lite.EetRegisterRequest;
import openeet.lite.Main;

import javax.xml.namespace.QName;

@Service
public class FIClientOpenEET {

	// private static final String URI = "http://localhost:8088/mockEETSOAP";
	private static final String URI = "https://pg.eet.cz:443/eet/services/EETServiceSOAP/v2";

	private static FIClientOpenEET instance;
	// @Autowired
	private WebServiceTemplate webServiceTemplate;
	private ObjectFactory factory;
	private String cashId;
	private int restaurantId;
	private String customerDIC;

	private KeyStoreSigner keyStoreSigner;

	private Unmarshaller un;

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
				if (storno) {
					orderDTO.setFIKStorno(result.getPotvrzeni().getFik());
				} else {
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

	private OdpovedType callFI(OrderDTO order, boolean storno) throws MalformedURLException, Exception {

		String orderId = String.valueOf(order.getId());
		EetRegisterRequest request = EetRegisterRequest.builder()
				.dic_popl(AppProperties.getProperties().getCustomerICO())
				.id_provoz(AppProperties.getProperties().getRestaurantIdString())
				.id_pokl(AppProperties.getProperties().getCashId())
				.porad_cis(orderId)
				.dat_trzby(EetRegisterRequest.formatDate(order.getDate()))
				.celk_trzba(storno ? order.getSumAfterDiscount().negate().doubleValue() : order.getSumAfterDiscount().doubleValue())
				.rezim(0)
				.pkcs12(EetRegisterRequest.loadStream(Main.class.getResourceAsStream("/openeet/lite/01000003.p12")))
				.pkcs12password(keyStoreSigner.getPassword()).build();

		// try send
		String requestBody = request.generateSoapRequest();
		System.out.printf("===== BEGIN EET REQUEST =====\n%s\n===== END EET REQUEST =====\n", requestBody);

		String responseString = request.sendRequest(requestBody,
				new URL("https://pg.eet.cz:443/eet/services/EETServiceSOAP/v3"));
		System.out.printf("===== BEGIN EET RESPONSE =====\n%s\n===== END EET RESPONSE =====\n", responseString);

		//JAXBElement<OdpovedType> response = (JAXBElement<OdpovedType>) un.unmarshal(new ByteArrayInputStream(responseString.getBytes("UTF-8")));

		OdpovedType odpoved = new OdpovedType();

		String fik = StringExtractor.extractFIK(responseString);
		if(fik != null) {
			OdpovedPotvrzeniType value = new OdpovedPotvrzeniType();
			value.setFik(fik);
			odpoved.setPotvrzeni(value );
		}
		else {
			String chyba = StringExtractor.extractChyba(responseString);
			OdpovedChybaType value = new OdpovedChybaType();
			value.setContent(chyba);
			odpoved.setChyba(value );
		}

		return odpoved;
	}

	private String createBkp(byte[] pkpValue) throws NoSuchAlgorithmException {
		MessageDigest cript = MessageDigest.getInstance("SHA-1");
		cript.reset();
		cript.update(pkpValue);
		byte[] bkp = cript.digest();
		String s = javax.xml.bind.DatatypeConverter.printHexBinary(bkp);

		String ss = String.format(
				"%02X%02X%02X%02X-%02X%02X%02X%02X-%02X%02X%02X%02X-%02X%02X%02X%02X-%02X%02X%02X%02X", bkp[0], bkp[1],
				bkp[2], bkp[3], bkp[4], bkp[5], bkp[6], bkp[7], bkp[8], bkp[9], bkp[10], bkp[11], bkp[12], bkp[13],
				bkp[14], bkp[15], bkp[16], bkp[17], bkp[18], bkp[19]);

		return ss;
	}

	/*
	 * public WebServiceTemplate getWebServiceTemplate() { return
	 * webServiceTemplate; }
	 * 
	 * public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate)
	 * { this.webServiceTemplate = webServiceTemplate; }
	 */
	public FIClientOpenEET() throws JAXBException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
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

		un = c.createUnmarshaller();
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

	public static void main(String[] args) throws Exception {
		FIClientOpenEET client = new FIClientOpenEET();
		OrderDTO order = new OrderDTO();
		OrderItemDTO orderItem = new OrderItemDTO();
		SalesProductEntity product = new SalesProductEntity();
		orderItem.setProduct(product);
		orderItem.setAmount(1);
		orderItem.setPrice(BigDecimal.TEN);
		order.setItems(Collections.singletonList(orderItem));
		order.setDate(new Date());
		OdpovedType a = client.callFI(order, false);
		// System.out.println(a.getChyba().getKod());
		System.out.println(Long.MAX_VALUE);
	}

	public static FIClientOpenEET getInstance() throws JAXBException, InvalidKeyException, UnrecoverableKeyException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		if (instance == null) {
			instance = new FIClientOpenEET();
		}
		return instance;
	}

}
