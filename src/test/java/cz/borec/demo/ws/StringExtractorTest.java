package cz.borec.demo.ws;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringExtractorTest {

	@Test
	public void test() {

		// String to be scanned to find the pattern.
		
		String line2 = "<SignatureValue>JJLz5DoLmWy0Vu2yel0zzc/qjyvNRMk3QZWMJcglRKNuwINvKp7SEfanmo8YFtjtWf0w/o58kG/opq+felGW8/SWTuM6swbaMhVAnmJaGglHE9TQacRJjfPZfZlGEZSRx4eAe+06/7Fi1cYE54M+C0X1+9oI9DuYeNnIVq8trqatpi4dkIhCP92VA+YB8SFOVcNX/oKv/aX8l4vp666JYASr1nxXvK3MSkYRgt1GoIv23dYutx6s5dccZMqzXbtZOcc1j29PDPm7u4w7IKkT++3avj6eZ4Ol9GKVyBcGuToiXzxp6cTuUii/Hc3dymKUNvH0SJLnD3ctaHCWnr739w==</SignatureValue><KeyInfo><wsse:SecurityTokenReference xmlns=\"\"><wsse:Reference URI=\"#SecurityToken-e57c38f6-6910-4d9c-a77c-dc54d87d56aa\" ValueType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3\"/></wsse:SecurityTokenReference></KeyInfo></Signature></wsse:Security></soapenv:Header><soapenv:Body wsu:Id=\"Body-c83da201-7680-4810-b3b5-6a33cba463f5\"><eet:Odpoved><eet:Hlavicka uuid_zpravy=\"22b6ef8e-7d9a-451c-9c54-a114080a88c7\" bkp=\"163BAA94-45A2BCB1-FDBA9FBD-859010B0-29E6043B\" dat_prij=\"2016-09-04T17:20:45+02:00\"/><eet:Potvrzeni fik=\"46f15824-8e93-4d78-9344-be7345b38b1a-ff\" test=\"true\"/></eet:Odpoved></soapenv:Body></soapenv:Envelope>";

		String fik = StringExtractor.extractFIK(line2);
		assertEquals("46f15824-8e93-4d78-9344-be7345b38b1a-ff", fik);
		
		String chyba = StringExtractor.extractChyba(line2);
		assertEquals(null, chyba);
		
		String odp = "<soapenv:Envelope xmlns:eet=\"http://fs.mfcr.cz/eet/schema/v3\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><eet:Odpoved><eet:Hlavicka dat_odmit=\"2016-09-05T07:31:22+02:00\"/><eet:Chyba kod=\"3\" test=\"true\">XML zprava nevyhovela kontrole XML schematu</eet:Chyba></eet:Odpoved></soapenv:Body></soapenv:Envelope>";
		chyba = StringExtractor.extractChyba(odp);
		assertEquals("XML zprava nevyhovela kontrole XML schematu", chyba);
	}
}
