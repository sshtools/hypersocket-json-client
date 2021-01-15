/*******************************************************************************
 * Copyright (c) 2013 LogonBox Limited.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.hypersocket.jsonclient.utils;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {
	static Logger log = System.getLogger(HypersocketUtils.class.getName());
	
	private ProcessBuilder pb;
	private List<String> args = new ArrayList<String>();
	private StringBuffer buffer = new StringBuffer();
	private File pwd = null;
	
	public CommandExecutor(String command) {
		args.add(command);
	}
	
	public CommandExecutor(String... cmdline){
		for(String arg : cmdline) {
			args.add(arg);
		}
	}
	
	public void addArg(String arg) {
		args.add(arg);
	}
	
	public void addArgs(String[] arguments) {
		for(String arg : arguments) {
			args.add(arg);
		}
	}
	
	public void setWorkingDirectory(File workingDirectory) {
		 pwd = workingDirectory;
	}
	
	public int execute() throws IOException {
		
		if(log.isLoggable(Level.INFO)) {
			StringBuilder builder = new StringBuilder();
			for(String s : args) {
				builder.append(s);
				builder.append(' ');
			}
			
			log.log(Level.INFO, "Executing command: " + builder.toString().trim());
		}
		pb = new ProcessBuilder(args);
		
		if(pwd!=null) {
			pb.directory(pwd.getCanonicalFile());
		}
		
		pb.redirectErrorStream(true);
		
		Process p = pb.start();
		
		int r;
		while((r = p.getInputStream().read()) > -1) {
			buffer.append((char)r);
		}
		
		int exitCode;
		
		try {
			exitCode = p.waitFor();
		} catch (InterruptedException e) {
			throw new IOException(e.getMessage(), e);
		}
		
		if (log.isLoggable(Level.DEBUG)) {
			log.log(Level.DEBUG, "Command output: " + buffer.toString());
		}
		
		return exitCode;
	}
	
	public String getCommandOutput() {
		return buffer.toString();
	}
}
