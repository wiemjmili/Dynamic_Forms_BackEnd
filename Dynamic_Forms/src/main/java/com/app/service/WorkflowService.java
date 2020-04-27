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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.app.model.Group;
import com.app.model.User;
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
    
    @Autowired
    private UserService UserService;
	
	public List <Workflow> getAllWorkflow(){
		return workflowrepository.findAll();
	}
	
	public Optional<Workflow> getWorkflow(String id){
		return workflowrepository.findById(id);
	}
	
	
	@PostMapping("/addWF")
	public String addWF(Workflow WF) {
		
		List <Workflow>  listwf =workflowrepository.findAll();

		//save in file Process.bpmn20.xml
		String name=saveProcess(WF);
		WF.setName(name);
		
		boolean find=false;
		int j=0;
		while(find==false && listwf.size()>j) {
			if(listwf.get(j).getName().equals(name)) {
				find=true;
			}else j++;
		}

		if(find==false) {

			List <UserTask> list=UserTaskService.getAllTasks();
			// add UserTasks 
			
			if(list.size()!=0) {
				
				  //save in mongo db 
				  workflowrepository.save(WF);
			      String xml=addFlowable();
			      List<Group>  listGP =new ArrayList<Group>();
			      WF.setWFXML(xml);
				  workflowrepository.save(WF);
				  
			 int i=0;
			 while(i<list.size()) 
			  {
				  list.get(i).setWorkFlow(WF);
				  list.get(i).setGroup(listGP);
				  userTaskrepository.save(list.get(i));
				  i++;
			  } 
			 }
		return "Workflow added"+WF.getName();
		
		}else return "Existing Workflow with this name "+WF.getName();
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
		
		

		public List<Workflow> getListProcessbyUser() {
			
			User Current_User =UserService.getCurrentUser();
			List<UserTask> listUT =userTaskrepository.findAll();
			List<Workflow> listAllWF =workflowrepository.findAll();
			
			List<Group> listGPofUser =Current_User.getGroups();
			
			List<UserTask> listUserTask =new ArrayList<UserTask>();
			List<UserTask> listUserTask1 =new ArrayList<UserTask>();
			List<Workflow> listWF =new ArrayList<Workflow>();
			
			for(int j=0;j<listAllWF.size();j++) {
				
				boolean find=false;
				int i=0;
				
				while(find==false && listUT.size()>i) {
					
					if(listUT.get(i).getWorkFlow().getId().equals(listAllWF.get(j).getId())) {
						find=true;
						listUserTask1.add(listUT.get(i));
					
					}else {
						i++;
					}	
			}}
			
			for(int i=0;i<listUserTask1.size();i++) {
				
				List<Group> listGPofUT =listUserTask1.get(i).getGroup();
				
				for (int k=0; k< listGPofUser.size() ;k++) {
				
					for (int j=0; j< listGPofUT.size() ;j++) {
					
						if(listGPofUT.get(j).getName_GP().equals(listGPofUser.get(k).getName_GP())) {
				    	    listUserTask.add(listUserTask1.get(i));
						}
					}
				}
			}
			for(int i=0;i<listUserTask.size();i++) {
				listWF.add(listUserTask.get(i).getWorkFlow());	
			}

			return listWF;
		}
}
