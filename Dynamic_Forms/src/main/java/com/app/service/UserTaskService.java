package com.app.service;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.app.model.Group;
import com.app.model.UserTask;
import com.app.model.Workflow;
import com.app.repository.GroupRepository;
import com.app.repository.UserTaskRepository;
import com.app.repository.WorkflowRepository;;


@Service
public class UserTaskService {
	
	@Autowired
	private UserTaskRepository UserTaskrepository;
	@Autowired
	private WorkflowRepository Workflowrepository;
	@Autowired
	private GroupRepository Grouprepository;
	
	
	  public List <UserTask> getTasks(){
		return UserTaskrepository.findAll();
		}
	
	
	  public List <UserTask> getTasksforWF(String nameWF){
		  
		  List <UserTask> listUT =UserTaskrepository.findAll();
		  List <UserTask>  listUTWF =new ArrayList();
		  
		  for (int i = 0; i < listUT.size(); i++)   
		  {   
			  if(listUT.get(i).getWorkFlow().getName().equals(nameWF)) {
				
				  listUTWF.add(listUT.get(i));
			  }
		  }
		
		  return listUTWF;
	  }
	
		public String getUserTask(String nameUT,String nameWF) {
			
			List <UserTask> listUT=UserTaskrepository.findAll();
			UserTask UT=new UserTask();
			
			boolean find=false;
			int j=0;
			while(find==false && listUT.size()>j) {
				if(listUT.get(j).getName().equals(nameUT) && listUT.get(j).getWorkFlow().getName().equals(nameWF)) {
					find=true;
					UT=listUT.get(j);
				}else {
					j++;
				}	
			}
			return UT.getId();
			}
	
		public String addGroup(@RequestBody UserTask UT) {
			
			if(!UT.getName().equals("") || !UT.getGroupN().equals("")) {
			UserTask UserT=new UserTask();
		    List <Group>  listGPUT =new ArrayList();
			List <UserTask> listUT=UserTaskrepository.findAll();
			List <Group> listGp=Grouprepository.findAll();
			
			  for (int i = 0; i < listUT.size(); i++)   
			  {
				  if(listUT.get(i).getName().equals(UT.getName())) {
					        UserT=listUT.get(i);
				  }
			  }
			  for (int i = 0; i < listGp.size(); i++)   
			  {
				  if(listGp.get(i).getName_GP().equals(UT.getGroupN())) {
					  listGPUT.add(listGp.get(i));
				  }
			  }
			  UserT.setGroupN(UT.getGroupN());
			  UserT.setGroup(listGPUT);
		
			  UserTaskrepository.save(UserT);
			  addGptoUT(UserT.getWorkFlow());
			}
			return UT.getName();
		}
	
		public List <UserTask> getAllTasks() {
		  
		  List <UserTask>  listUserTask =new ArrayList();
		  try { 
		  File file = new File("C:\\Users\\Famille\\git\\Dynamic_Forms_BackEnd\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml");  
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		  DocumentBuilder db = dbf.newDocumentBuilder();  
		  Document doc = db.parse(file);  
		  doc.getDocumentElement().normalize();
		  NodeList nodeList = doc.getElementsByTagName("bpmn2:userTask");  
		  for (int itr = 0; itr < nodeList.getLength(); itr++)   
		  {  
		  Node node = nodeList.item(itr); 
		  String Name= node.getAttributes().getNamedItem("name").getNodeValue();
		  UserTask task= new UserTask();
		  task.setName(Name);
		  listUserTask.add(task);
	      } 
		  }
		  catch (Exception e) {  
		  e.printStackTrace();  
		  } 
		  return listUserTask;
	  } 
  


  
  
  public  void  addGptoUT(Workflow wf){

	  List <UserTask> listUT =UserTaskrepository.findAll();
	  String xmlWF=wf.getWFXML();
	  String xmlString="";
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
      DocumentBuilder builder;  
      try  
      {  
          builder = factory.newDocumentBuilder(); 
         // System.out.println(xmlWF); 
          Document doc = builder.parse( new InputSource( new StringReader( xmlWF ) ) ); 
	      doc.getDocumentElement().normalize();
	      NodeList nodeList = doc.getElementsByTagName("bpmn2:userTask");  
	      String name="flowable:candidateGroups";
	  
	      for (int itr = 0; itr < nodeList.getLength(); itr++){
	    	  String value="";
	    	  Node node = nodeList.item(itr); 
	    	  String Name= node.getAttributes().getNamedItem("name").getNodeValue();
	    	  	for(int j=0;j<listUT.size();j++) {
	    		  if(listUT.get(j).getWorkFlow().getId().equals(wf.getId())) {
		  			if (Name.equals(listUT.get(j).getName())) {
		  				if(listUT.get(j).getGroup()!=null) {
		  					value=listUT.get(j).getGroup().get(0).getName_GP();
		  			    }
		  		      	 Element element=(Element) nodeList.item(itr);
		  	      		 element.setAttribute(name, value);
		  		         TransformerFactory transformerFactory = TransformerFactory.newInstance();
		  		         Transformer transformer = transformerFactory.newTransformer();
		  		         DOMSource source = new DOMSource(doc);
		  		         StreamResult result = new StreamResult(new File("C:\\Users\\Famille\\git\\Dynamic_Forms_BackEnd\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml"));
		  		         transformer.transform(source, result);
		  		         
		  		         StringWriter writer = new StringWriter();
		  		         //transform document to string 
		  		         transformer.transform(new DOMSource(doc), new StreamResult(writer));
		  		         xmlString = writer.getBuffer().toString();  
		  		} }
			}}
	         wf.setWFXML(xmlString);
	         Workflowrepository.save(wf);
	  }
	  catch (Exception e) {  
	  e.printStackTrace();  
	  }
  }
  
}
