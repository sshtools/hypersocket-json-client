/**
 * Copyright 2003-2016 SSHTOOLS Limited. All Rights Reserved.
 *
 * For product documentation visit https://www.sshtools.com/
 *
 * This file is part of Hypersocket JSON Client.
 *
 * Hypersocket JSON Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hypersocket JSON Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Hypersocket JSON Client.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.hypersocket.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandExecutor {

	static Logger log = LoggerFactory.getLogger(CommandExecutor.class);
	
	ProcessBuilder pb;
	List<String> args = new ArrayList<String>();
	StringBuffer buffer = new StringBuffer();
	File pwd = null;
	
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
		
		if(log.isInfoEnabled()) {
			StringBuilder builder = new StringBuilder();
			for(String s : args) {
				builder.append(s);
				builder.append(' ');
			}
			
			log.info("Executing command: " + builder.toString().trim());
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
		
		if (log.isDebugEnabled()) {
			log.debug("Command output: " + buffer.toString());
		}
		
		return exitCode;
	}
	
	public String getCommandOutput() {
		return buffer.toString();
	}
}
