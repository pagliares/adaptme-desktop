/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */

package adaptme;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import adaptme.ui.window.AdaptMeUI;

/**
 * 
 * @author Rodrigo Martins Pagliares
 */
public class Main {
    public static void main(String[] args) {
	try {
		Process p = Runtime.getRuntime().exec("cp skeleton/DynamicExperimentationProgramProxy.java src/main/java/adaptme");
		
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (Exception e) {
	    try {
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}

	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new AdaptMeUI();
	    }
	});

    }
}
