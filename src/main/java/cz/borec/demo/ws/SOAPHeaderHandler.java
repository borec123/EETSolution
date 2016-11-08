/*package cz.borec.demo.ws;

import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SOAPHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    private final String authenticatedToken;

    public SOAPHeaderHandler(String authenticatedToken) {
        this.authenticatedToken = authenticatedToken;
    }

    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outboundProperty =
                (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty.booleanValue()) {
            try {
                SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
                SOAPFactory factory = SOAPFactory.newInstance();
                String prefix = "urn";
                String uri = "urn:api.ecm.opentext.com";
                SOAPElement securityElem =
                        factory.createElement("OTAuthentication", prefix, uri);
                SOAPElement tokenElem =
                        factory.createElement("AuthenticationToken", prefix, uri);
                tokenElem.addTextNode(authenticatedToken);
                securityElem.addChildElement(tokenElem);
                SOAPHeader header = envelope.addHeader();
                header.addChildElement(securityElem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // inbound
        }
        return true;
    }

    public Set<QName> getHeaders() {
        return new TreeSet();
    }

    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    public void close(MessageContext context) {
        //
    }
    
    public static void main(String[] args) {
    	Authentication_Service authentication_Service = new Authentication_Service();



    	Authentication basicHttpBindingAuthentication = authentication_Service.getBasicHttpBindingAuthentication();
    	String authenticatedToken = "TOKEN";
    	((BindingProvider)basicHttpBindingAuthentication).getBinding().getHandlerChain().add(new SOAPHeaderHandler(authenticatedToken));
    }
    
}*/