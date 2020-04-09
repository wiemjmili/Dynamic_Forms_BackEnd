package com.app.service;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.app.model.Group;
import com.app.model.UserTask;
import com.app.model.Workflow;
import com.app.repository.UserTaskRepository;
import com.app.repository.WorkflowRepository;

@Service
public class WorkflowService {

	@Autowired
	private WorkflowRepository workflowrepository;
    
	@Autowired
	private UserTaskRepository userTaskrepository;
	
    @Autowired
    private UserTaskService UserTaskService;

	
	public List <Workflow> getAllWorkflow(){
		return workflowrepository.findAll();
	}
	
	public Optional<Workflow> getWorkflow(String id){
		return workflowrepository.findById(id);
	}
	
	
	@PostMapping("/addWF")
	public String addWF(@RequestBody Workflow WF) {
		  //save in mongo db 
		  workflowrepository.save(WF);
	      //save in file Process.bpmn20.xml
	      String name=saveProcess(WF);
	      String xml=addFlowable();
	      List <Group>  listGP =new ArrayList();
	      WF.setName(name);
	      WF.setWFXML(xml);
		  workflowrepository.save(WF);
	      // add UserTasks  
			List <UserTask> list=UserTaskService.getAllTasks();
			int i=0;
			  while(i<list.size()) 
			  {
				  list.get(i).setWorkFlow(WF);
				  list.get(i).setGroup(listGP);
				  userTaskrepository.save(list.get(i));
				  i++;
			  }  
		return "Workflow added"+WF.getName();
	}
	
	public String saveProcess(Workflow wf){

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
			        // write the content into xml file
			        TransformerFactory transformerFactory = TransformerFactory.newInstance();
			        Transformer transformer = transformerFactory.newTransformer();
			        DOMSource source = new DOMSource(doc);
			        StreamResult result = new StreamResult(new File("C:\\Users\\Famille\\git\\Dynamic_Forms_BackEnd\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml"));
			        transformer.transform(source, result);
			        
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        } 
		     return Name;   
		        
	}
		
		
		public String addFlowable() {
			String xmlString="";
			 try {   
				  File file = new File("C:\\Users\\Famille\\git\\Dynamic_Forms_BackEnd\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml");  
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
				  StreamResult result = new StreamResult(new File("C:\\Users\\Famille\\git\\Dynamic_Forms_BackEnd\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml"));
				  transformer.transform(source, result);	
			      StringWriter writer = new StringWriter();
			      //transform document to string 
			      transformer.transform(new DOMSource(doc), new StreamResult(writer));
			      xmlString = writer.getBuffer().toString();   
				  }
				  catch (Exception e) {  
				  e.printStackTrace();  
				  }
			 return xmlString;
		}

}