package com.BD.service;

import java.io.File;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.BD.model.Workflow;
import com.BD.repository.WorkflowRepository;

@Service
public class WorkflowService {

	@Autowired
	private WorkflowRepository repository;
	
		public String saveProcess(){

			List <Workflow> list=repository.findAll();
			Workflow wf=list.get(list.size()-1);
			String xmlWF=wf.getWFXML();
			String Name="";
			
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		        DocumentBuilder builder;  
		        try  
		        {  
		            builder = factory.newDocumentBuilder();  
		            Document doc = builder.parse( new InputSource( new StringReader( xmlWF ) ) ); 
		            NodeList nodeList = doc.getElementsByTagName("bpmn2:process");  
				    Name= nodeList.item(0).getAttributes().getNamedItem("name").getNodeValue();
				    System.out.println(Name);
			        // write the content into xml file
			        TransformerFactory transformerFactory = TransformerFactory.newInstance();
			        Transformer transformer = transformerFactory.newTransformer();
			        DOMSource source = new DOMSource(doc);
			        StreamResult result = new StreamResult(new File("C:\\Users\\Famille\\Documents\\workspace-sts\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml"));
			        transformer.transform(source, result);
			        
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        } 
		     return Name;   
		        
	}
		
		
		public void addFlowable() {
			 try {   
				 
				  File file = new File("C:\\Users\\Famille\\Documents\\workspace-sts\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml");  
				  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
				  DocumentBuilder db = dbf.newDocumentBuilder();  
				  Document doc = db.parse(file);  
				  doc.getDocumentElement().normalize();
				  NodeList nodeList = doc.getElementsByTagName("bpmn2:definitions");  
				  Element element=(Element) nodeList.item(0);
		      	  element.setAttribute("xmlns:flowable", "http://www.omg.org/spec/DD/20100524/DI" );
				  TransformerFactory transformerFactory = TransformerFactory.newInstance();
				  Transformer transformer = transformerFactory.newTransformer();
				  DOMSource source = new DOMSource(doc);
				  StreamResult result = new StreamResult(new File("C:\\Users\\Famille\\Documents\\workspace-sts\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml"));
				  transformer.transform(source, result);		
				  }
				  catch (Exception e) {  
				  e.printStackTrace();  
				  } 
		}

}
