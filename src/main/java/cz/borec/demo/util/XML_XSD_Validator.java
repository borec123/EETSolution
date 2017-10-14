package cz.borec.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XML_XSD_Validator {
	
	public static void main(String[] args) throws IOException, SAXException {
		
		//File file = new File("c:/temp/request.xml");
		File file = new File("c:/temp/sampleFromDocumentation.xml");
		FileInputStream fis = null;
		fis = new FileInputStream(file);

	/*	URL schemaFile = new URL("http://schemas.xmlsoap.org/soap/envelope/");
		SchemaFactory schemaFactory = SchemaFactory
			    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		  validator.validate(new StreamSource(file));
		  
		  
		String a = XMLConstants.W3C_XML_SCHEMA_NS_URI;*/

		File file2 = new File("C:/development/javaFXStoreH2/src/main/resources/xsd/EETXMLSchema.xsd");
		FileInputStream fis2 = null;
		fis2 = new FileInputStream(file2);

		System.out.println(validateAgainstXSD(fis, fis2));
		
		//validateAgainstXSD2("c:/temp/sampleFromDocumentation.xml", "C:/development/javaFXStoreH2/src/main/resources/xsd/EETXMLSchema.xsd");
		
		
		fis.close();
		fis2.close();

	}
	
	static boolean validateAgainstXSD(InputStream xml, InputStream xsd)
	{
	    try
	    {
	        SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.XMLNS_ATTRIBUTE_NS_URI); //.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = factory.newSchema(new StreamSource(xsd));
	        Validator validator = schema.newValidator();
	        validator.validate(new StreamSource(xml));
	        return true;
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	        return false;
	    }
	}
	
/*	static void validateAgainstXSD2(String xml, String xsd)
	{
	       try {
	            DOMParser parser = new DOMParser();
	            parser.setFeature("http://xml.org/sax/features/validation", true);

	            parser.setProperty("http://apache.org/xml/properties/schema/external", xsd);
	            
	            parser.setErrorHandler(new ErrorHandler() {
					
					@Override
					public void warning(SAXParseException exception) throws SAXException {
						System.out.println("warning" + exception);
					}
					
					@Override
					public void fatalError(SAXParseException exception) throws SAXException {
						System.out.println("fatalError" + exception);
					}
					
					@Override
					public void error(SAXParseException exception) throws SAXException {
						System.out.println("error" + exception);
					}
				});

				parser.parse(xml);
	        } catch (Exception e) {
	            System.out.print("Problem parsing the file.");
	            e.printStackTrace();
	        }

	}*/
}
