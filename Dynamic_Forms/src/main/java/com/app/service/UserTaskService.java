
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.app.model.UserTask;
import com.app.model.Workflow;
import com.app.repository.UserTaskRepository;
import com.app.repository.WorkflowRepository;;

@Service
public class UserTaskService {

	@Autowired
	private UserTaskRepository usertaskRepository;
	@Autowired
	private WorkflowRepository workflowRepository;

	@Value("${urlFile}")
	private String urlFile;

	public List<UserTask> getTasks() {
		return usertaskRepository.findAll();
	}

	// get list user_tasks for workflow by name
	public List<UserTask> getTasksforWF(String idWF) {

		List<UserTask> listUT = usertaskRepository.findAll();
		List<UserTask> listUTWF = new ArrayList<UserTask>();

		for (int i = 0; i < listUT.size(); i++) {
			if (listUT.get(i).getWorkFlow().getId().equals(idWF)) {

				listUTWF.add(listUT.get(i));
			}
		}

		return listUTWF;
	}

	public List<UserTask> getTasksBynameWF(String nameWF) {

		List<UserTask> listUT = usertaskRepository.findAll();
		List<UserTask> listUTWF = new ArrayList<UserTask>();

		for (int i = 0; i < listUT.size(); i++) {
			if (listUT.get(i).getWorkFlow().getName().equals(nameWF)) {

				listUTWF.add(listUT.get(i));
			}
		}

		return listUTWF;
	}

	// get the id of User task by nameWF and nameUT ( because we can have two
	// user_tasks with the same name in different workflow)
	public String getUserTask(String nameUT, String nameWF) {

		List<UserTask> listUT = usertaskRepository.findAll();
		UserTask ut = new UserTask();

		boolean find = false;
		int j = 0;
		while (find == false && listUT.size() > j) {
			if (listUT.get(j).getName().equals(nameUT) && listUT.get(j).getWorkFlow().getName().equals(nameWF)) {
				find = true;
				ut = listUT.get(j);
			} else {
				j++;
			}
		}
		return ut.getId();
	}

	public UserTask getUTById(String idUT) {

		List<UserTask> listUT = usertaskRepository.findAll();
		UserTask ut = new UserTask();

		boolean find = false;
		int j = 0;
		while (find == false && listUT.size() > j) {
			if (listUT.get(j).getId().equals(idUT)) {
				find = true;
				ut = listUT.get(j);
			} else {
				j++;
			}
		}
		return ut;
	}

	public String addGroup(UserTask ut) {

		UserTask UserT = new UserTask();
		List<UserTask> listUT = usertaskRepository.findAll();
		boolean find = false;
		int j = 0;

		while (find == false && j < listUT.size()) {

			if (listUT.get(j).getId().equals(ut.getId())) {
				UserT = listUT.get(j);
				find = true;
			} else {
				j++;
			}

		}

		UserT.setGroup(ut.getGroup());
		usertaskRepository.save(UserT);
		// add candidate groups to xml file
		// addGptoUT(UserT.getWorkFlow());
		return "Candidate group successful added to usertask";

	}

	public List<UserTask> getAllTasks() {

		List<UserTask> listUserTask = new ArrayList<UserTask>();
		try {
			File file = new File(urlFile);
			// NOSONAR
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("bpmn2:userTask");
			for (int itr = 0; itr < nodeList.getLength(); itr++) {
				Node node = nodeList.item(itr);
				String name = node.getAttributes().getNamedItem("name").getNodeValue();
				UserTask task = new UserTask();
				task.setName(name);
				// task= UserTaskrepository.save(task);
				listUserTask.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listUserTask;
	}

	// Add Group To User Task dans l'xml
	public void addGptoUT(Workflow wf) {

		List<UserTask> listUT = usertaskRepository.findAll();
		String xmlWF = wf.getXml();
		String xmlString = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();

			Document doc = builder.parse(new InputSource(new StringReader(xmlWF)));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("bpmn2:userTask");
			String name = "flowable:assignee";

			for (int itr = 0; itr < nodeList.getLength(); itr++) {
				String value = "";
				Node node = nodeList.item(itr);
				String nameWF = node.getAttributes().getNamedItem("name").getNodeValue();
				for (int j = 0; j < listUT.size(); j++) {
					if (listUT.get(j).getWorkFlow().getId().equals(wf.getId()) && nameWF.equals(listUT.get(j).getName())
							&& listUT.get(j).getGroup() != null) {

						value = listUT.get(j).getGroup().get(0).getName();

						Element element = (Element) nodeList.item(itr);
						element.setAttribute(name, value);
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(new File(urlFile));
						transformer.transform(source, result);

						StringWriter writer = new StringWriter();
						// transform document to string
						transformer.transform(new DOMSource(doc), new StreamResult(writer));
						xmlString = writer.getBuffer().toString();
					}
				}
			}
			wf.setXml(xmlString);
			workflowRepository.save(wf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}