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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.app.model.Form;
import com.app.model.Group;
import com.app.model.User;
import com.app.model.UserTask;
import com.app.model.Workflow;
import com.app.repository.FormRepository;
import com.app.repository.UserTaskRepository;
import com.app.repository.WorkflowRepository;

@Service
public class WorkflowService {

	@Autowired
	private WorkflowRepository workflowRepository;

	@Autowired
	private UserTaskRepository usertaskRepository;

	@Autowired
	private FormRepository formRepository;

	@Autowired
	private UserTaskService usertaskService;

	@Autowired
	private UserService userService;

	@Value("${urlFile}")
	private String urlFile;

	public List<Workflow> getAllWorkflow() {
		return workflowRepository.findAll();
	}

	public Workflow getWorkflow(String id) {
		Workflow wf = new Workflow();
		List<Workflow> list = workflowRepository.findAll();
		boolean find = false;
		int i = 0;

		while (find == false && list.size() > i) {

			if (list.get(i).getId().equals(id)) {
				wf = list.get(i);
				find = true;
			} else {
				i++;
			}

		}
		return wf;

	}

	public Workflow getWF(String nameWF) {

		List<Workflow> listwf = workflowRepository.findAll();
		Workflow wf = new Workflow();
		boolean find = false;
		int j = 0;
		while (find == false && listwf.size() > j) {
			if (listwf.get(j).getName().equals(nameWF)) {
				find = true;
				wf = listwf.get(j);
			} else
				j++;
		}
		return wf;

	}

	public String addWF(Workflow wf) {

		List<Workflow> listwf = workflowRepository.findAll();
		// save in file Process.bpmn20.xml
		String name = saveProcess(wf);
		if (!name.equals("")) {
			wf.setName(name);

			boolean find = false;
			int j = 0;
			while (find == false && listwf.size() > j) {
				if (listwf.get(j).getName().equals(name)) {
					find = true;
				} else {
					j++;
				}

			}

			if (find == false) {
				List<UserTask> list = usertaskService.getAllTasks();
				// add UserTasks
				if (list.size() != 0) {
					// save in mongo db
					workflowRepository.save(wf);
					String xml = addFlowable();
					List<Group> listGP = new ArrayList<Group>();
					wf.setXml(xml);
					workflowRepository.save(wf);

					int i = 0;
					while (i < list.size()) {
						list.get(i).setWorkFlow(wf);
						list.get(i).setGroup(listGP);
						UserTask ut = usertaskRepository.save(list.get(i));
						list.get(i).setId(ut.getId());
						i++;
					}
					j = 0;
					while (j < list.size()) {
						list.get(j).setIdNextUT(list.get(j + 1).getId());
						usertaskRepository.save(list.get(j));
						j++;
					}
				}
				return "Workflow added" + wf.getName();

			} else {
				return "Existing Workflow with this name " + wf.getName();
			}
		}
		return "Name workflow empty";
	}

	public String updateWF(Workflow wf) {
		// save in file Process.bpmn20.xml
		String name = saveProcess(wf);
		if (!name.equals("")) {

			wf.setName(name);
			Workflow existWF = getWF(name);

			if (existWF.getId() != null) {

				List<UserTask> Tasks = usertaskService.getTasksforWF(existWF.getId());

				for (int i = 0; i < Tasks.size(); i++) {

					usertaskRepository.delete(Tasks.get(i));
				}

				List<UserTask> list = usertaskService.getAllTasks();

				// add UserTasks

				if (!list.isEmpty()) {

					// save in mongo db
					wf.setId(existWF.getId());
					workflowRepository.save(wf);
					String xml = addFlowable();
					List<Group> listGP = new ArrayList<Group>();
					wf.setXml(xml);
					workflowRepository.save(wf);

					int i = 0;
					while (i < list.size()) {
						list.get(i).setWorkFlow(wf);
						list.get(i).setGroup(listGP);
						UserTask ut = usertaskRepository.save(list.get(i));
						list.get(i).setId(ut.getId());
						i++;
					}

					int j = 0;
					while (j < list.size()) {
						list.get(j).setIdNextUT(list.get(j + 1).getId());
						usertaskRepository.save(list.get(j));
						j++;
					}

				}
				return "Workflow updated" + wf.getName();

			}

		}
		return "Name workflow empty";

	}

	public Workflow getWFbyid(String id) {

		List<Workflow> listwf = workflowRepository.findAll();
		Workflow wf = new Workflow();
		boolean find = false;
		int j = 0;
		while (find == false && listwf.size() > j) {
			if (listwf.get(j).getId().equals(id)) {
				find = true;
				wf = listwf.get(j);
			} else {
				j++;
			}
		}
		return wf;

	}

	public String deleteWF(String id) {

		if (!id.equals("")) {
			List<Form> listForm = formRepository.findAll();
			Workflow existWF = getWFbyid(id);
			List<UserTask> tasks = usertaskService.getTasksforWF(id);
			for (int i = 0; i < tasks.size(); i++) {
				usertaskRepository.delete(tasks.get(i));
			}
			int n = listForm.size();
			for (int i = 0; i < tasks.size(); i++) {
				boolean find = false;
				int j = 0;
				while (j < n && find == false) {
					if (tasks.get(i).getId().equals(listForm.get(j).getIdUT())) {
						formRepository.delete(listForm.get(j));
						find = true;
					} else {
						j++;
					}
				}
			}

			workflowRepository.delete(existWF);
		}
		return "ok";
	}

	public String saveProcess(Workflow wf) {

		String xmlWF = wf.getXml();
		String name = "";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlWF)));
			NodeList nodeList = doc.getElementsByTagName("bpmn2:process");
			name = nodeList.item(0).getAttributes().getNamedItem("name").getNodeValue();
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(urlFile));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;

	}

	public String addFlowable() {
		String xmlString = "";
		try {
			File file = new File(urlFile);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("bpmn2:definitions");
			Element element = (Element) nodeList.item(0);
			element.setAttribute("xmlns:flowable", "http://www.omg.org/spec/DD/20100524/DI");
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(urlFile));
			transformer.transform(source, result);
			StringWriter writer = new StringWriter();
			// transform document to string
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			xmlString = writer.getBuffer().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlString;
	}

	public List<Workflow> getListProcessbyUser() {

		User currentUser = userService.getCurrentUser();
		List<UserTask> listUT = usertaskRepository.findAll();
		List<Workflow> listAllWF = workflowRepository.findAll();

		List<Group> listGPofUser = currentUser.getGroups();

		List<UserTask> listUserTask = new ArrayList<UserTask>();
		List<UserTask> listUserTaskNext = new ArrayList<UserTask>();
		List<Workflow> listWF = new ArrayList<Workflow>();

		for (int j = 0; j < listAllWF.size(); j++) {

			boolean find = false;
			int i = 0;

			while (find == false && listUT.size() > i) {

				if (listUT.get(i).getWorkFlow().getId().equals(listAllWF.get(j).getId())) {
					find = true;
					listUserTaskNext.add(listUT.get(i));

				} else {
					i++;
				}
			}
		}

		for (int i = 0; i < listUserTaskNext.size(); i++) {

			List<Group> listGPofUT = listUserTaskNext.get(i).getGroup();

			for (int k = 0; k < listGPofUser.size(); k++) {

				for (int j = 0; j < listGPofUT.size(); j++) {

					if (listGPofUT.get(j).getName().equals(listGPofUser.get(k).getName())) {
						listUserTask.add(listUserTaskNext.get(i));
					}
				}
			}
		}
		for (int i = 0; i < listUserTask.size(); i++) {
			listWF.add(listUserTask.get(i).getWorkFlow());
		}

		return listWF;
	}

}