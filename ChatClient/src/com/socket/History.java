package com.socket;

import java.io.File;

import javax.lang.model.element.Element;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import com.ui.ClientListFrame;


public class History {
	public String filePath;
	public History(String filePath){
		this.filePath = filePath;
	}
	public void addMessage(Message msg, String time){
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);
			
			Node data =doc.getFirstChild();
			
			org.w3c.dom.Element message =  doc.createElement("message");
			org.w3c.dom.Element _sender =  doc.createElement("sender");
			_sender.setTextContent(msg.sender);
			org.w3c.dom.Element _content = doc.createElement("content");
			_content.setTextContent(msg.content);
			org.w3c.dom.Element _recipient = doc.createElement("recipient");
			_recipient.setTextContent(msg.recipient);;
			org.w3c.dom.Element _time = doc.createElement("time");
			_time.setTextContent(time);
			
			message.appendChild(_sender);message.appendChild(_content); message.appendChild(_recipient); message.appendChild(_time);
			data.appendChild(message);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static String getTagValue(String sTag, org.w3c.dom.Element eElement){
		NodeList nlList =  eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node)nlList.item(0);
		return nValue.getNodeValue();
	}
	public void FillTable(ClientListFrame frame)
	{
		try {
			frame.chatArea.setText("");
			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("message");
			for(int temp = 0; temp <nList.getLength(); ++temp)
			{
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					org.w3c.dom.Element eElement = (org.w3c.dom.Element)nNode;
					String sender = getTagValue("sender", eElement);
					String content = getTagValue("content", eElement);
					String recipient = getTagValue("recipient",eElement);
					frame.chatArea.append("["+ sender +" > "+ recipient +"] : " + content + "\n");
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
