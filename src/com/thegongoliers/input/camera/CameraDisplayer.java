package com.thegongoliers.input.camera;

import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraDisplayer {
	public void display(VideoSource source) {
		// CameraServer.getInstance().addCamera(source);
		CameraServer.getInstance().startAutomaticCapture(source);
	}
}
