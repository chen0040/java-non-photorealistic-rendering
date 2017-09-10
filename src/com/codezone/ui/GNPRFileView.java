/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone.ui;

/**
 *
 * @author AB
 */
import javax.swing.*;
import javax.swing.filechooser.*;

import java.io.File;
import java.util.Hashtable;

public class GNPRFileView extends FileView {
    private Hashtable icons = new Hashtable(5);
    private Hashtable fileDescriptions = new Hashtable(5);
    private Hashtable typeDescriptions = new Hashtable(5);

    @Override public String getName(File f) {
	return null;
    }

    public void putDescription(File f, String fileDescription) {
	fileDescriptions.put(fileDescription, f);
    }

    @Override public String getDescription(File f) {
	return (String) fileDescriptions.get(f);
    };

    public void putTypeDescription(String extension, String typeDescription) {
	typeDescriptions.put(typeDescription, extension);
    }

    public void putTypeDescription(File f, String typeDescription) {
	putTypeDescription(getExtension(f), typeDescription);
    }

    @Override public String getTypeDescription(File f) {
	return (String) typeDescriptions.get(getExtension(f));
    }

    public String getExtension(File f) {
	String name = f.getName();
	if(name != null) {
	    int extensionIndex = name.lastIndexOf('.');
	    if(extensionIndex < 0) {
		return null;
	    }
	    return name.substring(extensionIndex+1).toLowerCase();
	}
	return null;
    }

    public void putIcon(String extension, Icon icon) {
	icons.put(extension, icon);
    }

    @Override public Icon getIcon(File f) {
	Icon icon = null;
	String extension = getExtension(f);
	if(extension != null) {
	    icon = (Icon) icons.get(extension);
	}
	return icon;
    }

    public Boolean isHidden(File f) {
	String name = f.getName();
	if(name != null && !name.equals("") && name.charAt(0) == '.') {
	    return Boolean.TRUE;
	} else {
	    return Boolean.FALSE;
	}
    };

    @Override public Boolean isTraversable(File f) {
	if(f.isDirectory()) {
	    return Boolean.TRUE;
	} else {
	    return Boolean.FALSE;
	}
    };

}


