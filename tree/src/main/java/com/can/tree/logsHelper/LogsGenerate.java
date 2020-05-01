package com.can.tree.logsHelper;

public interface LogsGenerate {

	public String infoAdd(String username,String text) ;
	public String infoEdit(String username,String text) ;
	public String infoDelete(String username,String text) ;
	public String warningAdd(String username,String text) ;
	public String warningEdit(String username,String text) ;
	public String warningDelete(String username,String text) ;
	public String getLogs() ;
}