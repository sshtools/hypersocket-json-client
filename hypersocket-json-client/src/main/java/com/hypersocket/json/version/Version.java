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
package com.hypersocket.json.version;


public class Version implements Comparable<Version> {
    private int[] elements;
    private String versionString;

    public Version(String versionString) {
        parseFromString(versionString);
    }

    public void parseFromString(String versionString) {
        this.versionString = versionString;
        String[] stringElements = versionString.split("[^a-zA-Z0-9]+");
        if (stringElements.length < 2 || stringElements.length > 4) {
            throw new IllegalArgumentException("Version number '" + versionString + "' incorrect. Must be in the format <major>.<minor>.<release>[?TAG]");
        } else if (stringElements.length == 3) {
            stringElements = new String[] { stringElements[0], stringElements[1], stringElements[2], "base" };
        } else if (stringElements.length == 2) {
            stringElements = new String[] { stringElements[0], stringElements[1], "0", "base" };
        }
        elements = new int[stringElements.length];
        int idx = 0;
        int element;
        for (String string : stringElements) {
            if (idx == 3) {
                if (string.equalsIgnoreCase("base")) {
                    element = 0;
                } else if (string.toLowerCase().startsWith("ga")) {
                    String substring = string.substring(2);
                    element = -19999 + ( substring.equals("") ? 0 : Integer.parseInt(substring) );
                } else if (string.toLowerCase().startsWith("rc")) {
                    String substring = string.substring(2);
                    element = -29999 + ( substring.equals("") ? 0 : Integer.parseInt(substring) );
                } else if (string.toLowerCase().startsWith("beta")) {
                    String substring = string.substring(4);
                    element = -39999 + ( substring.equals("") ? 0 : Integer.parseInt(substring) );
                } else if (string.toLowerCase().startsWith("alpha")) {
                    String substring = string.substring(5);
                    element = -49999 + ( substring.equals("") ? 0 : Integer.parseInt(substring) );
                } else if (string.toLowerCase().startsWith("snapshot")) {
                    String substring = string.substring(8);
                    element = -59999 + ( substring.equals("") ? 0 : Integer.parseInt(substring) );
                } else if (string.toLowerCase().startsWith("local")) {
                    element = Integer.MAX_VALUE;
                } else if (string.toLowerCase().startsWith("r")) {
                    String substring = string.substring(1);
                    element = ( substring.equals("") ? 0 : Integer.parseInt(substring) );
                } else {
                    element = Integer.parseInt(string);
                }
            } else {
                element = Integer.parseInt(string);
            }
            elements[idx] = element;
            idx++;
        }
    }

    public int hashCode() {
        return toString().hashCode();
    }
    
    public boolean equals(Object o) {
        return o != null  && o instanceof Version && ((Version)o).compareTo(this) == 0;
    }

    public int[] getVersionElements() {
        return elements;
    }

    public String toString() {
        return versionString;
    }

    public int compareTo(Version version) {
        if (version == null) {
            return 1;
        }
        int[] otherElements = version.getVersionElements();
        for(int i = 0 ; i < 4 ; i++) {
            if(elements[i] != otherElements[i]) {
                return elements[i] - otherElements[i];
            }
        }
        return 0;
    }
  
}
