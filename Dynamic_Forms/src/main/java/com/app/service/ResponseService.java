package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.enums.State;
import com.app.model.Process;
import com.app.model.Requests;
import com.app.model.Response;
import com.app.model.User;
import com.app.model.UserTask;
import com.app.repository.ProcessRepository;
import com.app.repository.RequestsRepository;
import com.app.repository.ResponseRepository;

@Service
public class ResponseService {

	@Autowired
	private ResponseRepository responserepository;

	@Autowired
	private RequestsRepository requestsrepository;
	@Autowired
	private ProcessRepository processRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RequestsService requestsService;

	@Autowired
	private UserTaskService userTaskService;

	@Autowired
	private Process_Service processSer;

	public List<Response> getAllresponsebyUser() {

		User user = userService.getCurrentUser();
		List<Response> listRes = responserepository.findAll();
		List<Response> listResponse = new ArrayList<>();

		for (int i = 0; i < listRes.size(); i++) {
			if (listRes.get(i).getUser().getId().equals(user.getId())) {
				listResponse.add(listRes.get(i));
			}
		}
		return listResponse;
	}

	public List<Response> getAllresponse() {

		List<Response> listRes = responserepository.findAll();

		return listRes;
	}

	public String addResponse(Response res) {

		String idreq = res.getIdReq();
		Requests req = requestsService.getRequestByid(idreq);
		String idProc = req.getIdProc();
		Process proc = processSer.getProcessById(idProc);
		String idproc = proc.getIdProcess();

		ProcessEngine processEngine = processSer.getEngine();
		String idDef = proc.getIdDefinition();
		List<ProcessInstance> processInstances = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionId(idDef).list();

		ProcessInstance procInstance = null;
		for (ProcessInstance processInstance : processInstances) {

			if (processInstance.getId().equals(idproc)) {
				procInstance = processInstance;
			}
		}

		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processInstanceId(idproc).singleResult();
		Task next_task = taskService.createTaskQuery().processInstanceId(idproc).singleResult();

		if (task != null) {
			taskService.complete(task.getId());

			if (next_task == null && procInstance != null) {
				processEngine.getRuntimeService().deleteProcessInstance(procInstance.getId(), "");
			}

		}

		String idUT = res.getForm().getIdUT();
		UserTask ut = userTaskService.getUTById(idUT);
		User user = userService.getCurrentUser();
		res.setUser(user);
		responserepository.save(res);

		if (ut.getIdNextUT() == null) {
			req.setValide(true);

		}
		req.setState(State.VALIDATED);
		requestsrepository.save(req);

		return "Response successfully added ";
	}

	public String rejectRequest(Requests Req) {

		Requests req = requestsService.getRequestByid(Req.getId());
		String idProc = req.getIdProc();
		Process proc = processSer.getProcessById(idProc);
		String idproc = proc.getIdProcess();
		ProcessEngine processEngine = processSer.getEngine();
		String idDef = proc.getIdDefinition();
		List<ProcessInstance> processInstances = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionId(idDef).list();
		ProcessInstance procInstance = null;

		for (ProcessInstance process_Instance : processInstances) {

			if (process_Instance.getId().equals(idproc)) {
				procInstance = process_Instance;
			}
		}
		if (procInstance != null) {
			processEngine.getRuntimeService().deleteProcessInstance(procInstance.getId(), "refused");
		}
		proc.setState(State.REFUSED);
		processRepository.save(proc);
		req.setValide(true);
		req.setState(State.REFUSED);
		requestsrepository.save(req);

		return "request rejected";
	}

}
