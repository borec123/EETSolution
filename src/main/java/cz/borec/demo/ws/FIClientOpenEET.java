package cz.borec.demo.ws;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import openeet.lite.EetRegisterRequest;
import openeet.lite.Main;

import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import cz.borec.demo.AppProperties;
import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.gui.Controller;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.mfcr.fs.eet.schema.v2.ObjectFactory;
import cz.mfcr.fs.eet.schema.v2.OdpovedChybaType;
import cz.mfcr.fs.eet.schema.v2.OdpovedPotvrzeniType;
import cz.mfcr.fs.eet.schema.v2.OdpovedType;

//@Service
public class FIClientOpenEET {

	private static final String URI_PLAYGROUD = "https://pg.eet.cz:443/eet/services/EETServiceSOAP/v3";
	private static final String URI_PRODUCTION = "https://prod.eet.cz:443/eet/services/EETServiceSOAP/v3";

	private final String uri;

	// private static final String URI = "http://localhost:8088/mockEETSOAP";
	// private static final String URI =
	// "https://pg.eet.cz:443/eet/services/EETServiceSOAP/v2";

	private static FIClientOpenEET instance;
	// @Autowired
	private WebServiceTemplate webServiceTemplate;
	private String cashId;
	private String restaurantId;
	private String customerDIC;

	private KeyStoreSigner keyStoreSigner;

	private Unmarshaller un;

	private Controller controller;

	private String path = null;

	public boolean callFIPublic(OrderDTO orderDTO, boolean storno) {
		OdpovedType result = null;
		try {
			result = callFI(orderDTO, storno);
			controller.setError(0);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			controller.setError(1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			controller.setError(2);
		} catch (Exception e) {
			e.printStackTrace();
			controller.setError(3);
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
		path = AppPropertiesProxy.get(Constants.CONFIG_CERTIFICATE_PATH);
		EetRegisterRequest request = EetRegisterRequest.builder()
				.dic_popl(this.customerDIC)
				.id_provoz(AppProperties.getProperties().getRestaurantIdString())
				.id_pokl(this.cashId)
				.porad_cis(orderId)
				.dat_trzby(EetRegisterRequest.formatDate(order.getDate()))
				.celk_trzba(storno ? order.getSumAfterDiscount().negate().doubleValue()
						: order.getSumAfterDiscount().doubleValue())
				.dan1(storno ? order.getVatAmount().negate().doubleValue()
						: order.getVatAmount().doubleValue())
				.rezim(Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_FI_MODE)) ? 1 : 0)
				// .pkcs12(EetRegisterRequest.loadStream(Main.class.getResourceAsStream("/openeet/lite/01000003.p12")))
				.pkcs12(EetRegisterRequest.loadStream(path != null ? new FileInputStream(path)
						: Main.class.getResourceAsStream("/openeet/lite/01000003.p12")))
				.pkcs12password(keyStoreSigner.getPassword()).build();

		// try send
		String requestBody = request.generateSoapRequest();
		System.out.printf("===== BEGIN EET REQUEST =====\n%s\n===== END EET REQUEST =====\n", requestBody);

		String responseString = request.sendRequest(requestBody, new URL(uri));
		System.out.printf("===== BEGIN EET RESPONSE =====\n%s\n===== END EET RESPONSE =====\n", responseString);

		// JAXBElement<OdpovedType> response = (JAXBElement<OdpovedType>)
		// un.unmarshal(new
		// ByteArrayInputStream(responseString.getBytes("UTF-8")));

		OdpovedType odpoved = new OdpovedType();

		String fik = StringExtractor.extractFIK(responseString);
		if (fik != null) {
			OdpovedPotvrzeniType value = new OdpovedPotvrzeniType();
			value.setFik(fik);
			odpoved.setPotvrzeni(value);
		} else {
			String chyba = StringExtractor.extractChyba(responseString);
			OdpovedChybaType value = new OdpovedChybaType();
			value.setContent(chyba);
			odpoved.setChyba(value);
		}

		return odpoved;
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

		uri = AppProperties.getProperties().get(Constants.CONFIG_URL).toString();
		webServiceTemplate = new org.springframework.ws.client.core.WebServiceTemplate();
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

		
		
		cashId = AppPropertiesProxy.get(Constants.CONFIG_CASH_ID);
		restaurantId = AppPropertiesProxy.get(Constants.CONFIG_RESTAURANT_NAME);
		customerDIC = AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_ICO);

	}

	/*
	 * public static void main(String[] args) throws Exception { FIClientOpenEET
	 * client = new FIClientOpenEET(null); OrderDTO order = new OrderDTO();
	 * OrderItemDTO orderItem = new OrderItemDTO(); SalesProductEntity product =
	 * new SalesProductEntity(); orderItem.setProduct(product);
	 * orderItem.setAmount(1); orderItem.setPrice(BigDecimal.TEN);
	 * order.setItems(Collections.singletonList(orderItem)); order.setDate(new
	 * Date()); OdpovedType a = client.callFI(order, false); //
	 * System.out.println(a.getChyba().getKod());
	 * System.out.println(Long.MAX_VALUE); }
	 */

	public void setController(Controller controller) {
		this.controller = controller;

	}

	public static FIClientOpenEET getInstance() throws JAXBException, InvalidKeyException, UnrecoverableKeyException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		if (instance == null) {
			instance = new FIClientOpenEET();
		}
		return instance;
	}

}
