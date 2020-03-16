package com.BD.service;
import java.io.File;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.BD.model.UserTask;
import com.BD.model.Workflow;
import com.BD.repository.UserTaskRepository;
import com.BD.repository.WorkflowRepository;;


@Service
public class UserTaskService {
	
	@Autowired
	private UserTaskRepository UserTaskrepository;
	@Autowired
	private WorkflowRepository Workflowrepository;
	
  public List <UserTask> getAllTasks() {
		  
		  List <UserTask>  listUserTask =new ArrayList();
		  try { 
		  File file = new File("C:\\Users\\Famille\\Documents\\workspace-sts\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml");  
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		  DocumentBuilder db = dbf.newDocumentBuilder();  
		  Document doc = db.parse(file);  
		  doc.getDocumentElement().normalize();
		  NodeList nodeList = doc.getElementsByTagName("bpmn2:userTask");  
		  for (int itr = 0; itr < nodeList.getLength(); itr++)   
		  {  
		  Node node = nodeList.item(itr); 
		  String Name= node.getAttributes().getNamedItem("name").getNodeValue();
		  System.out.println(Name); 
		  UserTask task= new UserTask();
		  task.setName(Name);
		  task.setGroup(null);
		  listUserTask.add(task);
	      } 
		  }
		  catch (Exception e) {  
		  e.printStackTrace();  
		  } 
		  return listUserTask;
	  } 
  
  
  public List <UserTask> getTasksforLastWF(){
	  
	  List <UserTask> listUT =UserTaskrepository.findAll();
	  List <Workflow> listWF =Workflowrepository.findAll();
	  List <UserTask>  listUTforWF =new ArrayList();
	  Workflow wf=listWF.get(listWF.size()-1);
	  
	  for (int i = 0; i < listUT.size(); i++)   
	  { 
		  if(listUT.get(i).getWorkFlow().getId().equals(wf.getId())) {
			  listUTforWF.add(listUT.get(i));
		  }
	  }
	  return listUTforWF;
  }
  
  public  void  addGptoUT(){
	  
	  List <UserTask>  listUserTask =UserTaskrepository.findAll();
	  try {   
	  File file = new File("C:\\Users\\Famille\\Documents\\workspace-sts\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml");  
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
	  DocumentBuilder db = dbf.newDocumentBuilder();  
	  Document doc = db.parse(file);  
	  doc.getDocumentElement().normalize();
	  NodeList nodeList = doc.getElementsByTagName("bpmn2:userTask");  
	  String name="flowable:candidateGroups";
	  
	  for (int itr = 0; itr < nodeList.getLength(); itr++){
		  String value="";
		  Node node = nodeList.item(itr); 
		  String Name= node.getAttributes().getNamedItem("name").getNodeValue();
		  		for (int i= 0; i < listUserTask.size(); i++){
		  			if (Name.equals(listUserTask.get(i).getName())) {
		  				if(listUserTask.get(i).getGroup()!=null) {
		  					value=listUserTask.get(i).getGroup().get(0).getName_GP();
		  			}
		  		} 
		  	}
	      	 Element element=(Element) nodeList.item(itr);
      		 element.setAttribute(name, value);
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File("C:\\Users\\Famille\\Documents\\workspace-sts\\Dynamic_Forms\\src\\main\\resources\\Process.bpmn20.xml"));
	         transformer.transform(source, result);
      		
      		
			}
	  }
	  catch (Exception e) {  
	  e.printStackTrace();  
	  } 
  }
  
}
