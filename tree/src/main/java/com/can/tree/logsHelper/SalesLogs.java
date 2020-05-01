package com.can.tree.logsHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.PageRequest;

import com.can.tree.model.Logs;
import com.can.tree.service.LogsService;

public class SalesLogs implements LogsGenerate{
	public static String about = "SALES";
	private LogsService logsService;
	 public SalesLogs(LogsService logsService) {
		this.logsService= logsService;
	}


	public String infoAdd(String username,String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		Logs newLog = new Logs();
		newLog.setProcessType("EKLEME");
		newLog.setText(text);
		newLog.setType("INFO");
		newLog.setUsername(username);
		newLog.setAbout(about);
		newLog.setDateTime(formatter.format(currentDate));
		logsService.save(newLog);
		return newLog.toString();		
	}
	public String infoEdit(String username,String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		Logs newLog = new Logs();
		newLog.setProcessType("DUZENLEME");
		newLog.setText(text);
		newLog.setType("INFO");
		newLog.setUsername(username);
		newLog.setAbout(about);
		newLog.setDateTime(formatter.format(currentDate));
		logsService.save(newLog);
		return newLog.toString();		
	}
	public String infoDelete(String username,String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		Logs newLog = new Logs();
		newLog.setProcessType("SILME");
		newLog.setText(text);
		newLog.setType("INFO");
		newLog.setUsername(username);
		newLog.setAbout(about);
		newLog.setDateTime(formatter.format(currentDate));
		logsService.save(newLog);
		return newLog.toString();		
	}
	public String warningAdd(String username,String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		Logs newLog = new Logs();
		newLog.setProcessType("EKLEME");
		newLog.setText(text);
		newLog.setType("WARNING");
		newLog.setUsername(username);
		newLog.setAbout(about);
		newLog.setDateTime(formatter.format(currentDate));
		logsService.save(newLog);
		return newLog.toString();		
	}
	public String warningEdit(String username,String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		Logs newLog = new Logs();
		newLog.setProcessType("DUZENLEME");
		newLog.setText(text);
		newLog.setType("WARNING");
		newLog.setUsername(username);
		newLog.setAbout(about);
		newLog.setDateTime(formatter.format(currentDate));
		logsService.save(newLog);
		return newLog.toString();		
	}
	public String warningDelete(String username,String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		Logs newLog = new Logs();
		newLog.setProcessType("SILME");
		newLog.setText(text);
		newLog.setType("WARNING");
		newLog.setUsername(username);
		newLog.setAbout(about);
		newLog.setDateTime(formatter.format(currentDate));
		logsService.save(newLog);
		return newLog.toString();		
	}
	public String getLogs() {
		List<Logs> logs = logsService.findByAbout(about,  PageRequest.of(0, 1)).getContent();
		List<String> resp = new ArrayList<String>();
		for (Logs logs2 : logs) {
			resp.add(logs2.toString());
		}
		return resp.toString();
	}
}
