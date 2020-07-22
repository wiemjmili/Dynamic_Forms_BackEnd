package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Form;
import com.app.model.UserTask;
import com.app.repository.FormRepository;

@Service
public class FormsService {

	@Autowired
	private FormRepository formRepository;
	@Autowired
	private UserTaskService usertaskService;
	@Autowired
	private RequestsService requestsService;

	public List<Form> getListFormByNameWorkflow(String nameWF) {

		List<UserTask> listUT = usertaskService.getTasksBynameWF(nameWF);
		List<Form> allForms = formRepository.findAll();
		List<Form> listForm = new ArrayList<>();

		for (int i = 0; i < listUT.size(); i++) {
			for (int j = 0; j < allForms.size(); j++) {
				if (listUT.get(i).getId().equals(allForms.get(j).getIdUT())) {
					listForm.add(allForms.get(j));
				}

			}
		}

		return listForm;
	}

	public List<Form> getALLForms() {
		List<Form> allForms = formRepository.findAll();
		return allForms;
	}

	public List<Form> getFormsByUser(String idUT) {

		List<Form> allForms = formRepository.findAll();
		List<Form> forms = new ArrayList<>();
		UserTask UT = usertaskService.getUTById(idUT);
		List<UserTask> listTask = new ArrayList<>();
		List<UserTask> list = new ArrayList<>();

		listTask = requestsService.getUserTaskByUser(UT.getWorkFlow().getName());
		for (int j = 0; j < listTask.size(); j++) {
			list.add(listTask.get(j));
		}

		for (int i = 0; i < allForms.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (allForms.get(i).getIdUT().equals(list.get(j).getId())) {
					forms.add(allForms.get(i));
				}
			}
		}

		return forms;
	}

	public Form getFormbyUserTask(String idUT) {

		List<Form> allForms = formRepository.findAll();
		boolean find = false;
		int j = 0;
		Form form = new Form();
		while (find == false && allForms.size() > j) {
			if (allForms.get(j).getIdUT().equals(idUT)) {
				find = true;
				form = allForms.get(j);
			} else {
				j++;
			}
		}
		return form;
	}

	public String addForm(Form form) {

		List<Form> list = formRepository.findAll();
		boolean find = false;
		int j = 0;
		while (find == false && list.size() > j) {
			if (form.getIdUT().equals(list.get(j).getIdUT())) {
				find = true;

			} else {
				j++;
			}
		}
		if (find == false) {
			formRepository.save(form);

			return "Form successfully added to usertask";

		} else
			return "exist";

	}

	public List<Form> getFormbyProcess(String nameWF) {

		List<Form> listForm = new ArrayList<>();
		List<UserTask> listUT = usertaskService.getTasksBynameWF(nameWF);
		List<Form> allForms = formRepository.findAll();
		Form form = new Form();
		UserTask ut = listUT.get(0);

		boolean find = false;
		int j = 0;
		while (find == false && allForms.size() > j) {
			if (ut.getId().equals(allForms.get(j).getIdUT())) {
				find = true;
				form = allForms.get(j);
			} else {
				j++;
			}
		}
		if (find == true) {
			listForm.add(form);
		}

		return listForm;
	}

}
