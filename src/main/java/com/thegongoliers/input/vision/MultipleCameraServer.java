package com.thegongoliers.input.vision;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.cameraserver.CameraServer;

import java.util.HashMap;
import java.util.Map;

/**
 * A camera server which allows for swapping between multiple cameras (only one streams at a time)
 */
public class MultipleCameraServer {

    private MjpegServer server;
    private Map<String, VideoCamera> cameras;
    private String currentCamera;

    /**
     * Default constructor
     * @param name the name of the camera server on SmartDashboard
     */
    public MultipleCameraServer(String name){
        server = CameraServer.getInstance().addSwitchedCamera(name);
        cameras = new HashMap<>();
        currentCamera = null;
    }

    /**
     * Add a camera to the server. If no camera is currently displayed, the passed in camera will be.
     * @param name the unique name of the camera
     * @param camera the camera to add
     */
    public void addCamera(String name, VideoCamera camera){
        if (name == null || camera == null){
            return;
        }
        cameras.put(name, camera);
        if (currentCamera == null){
            switchToCamera(name);
        }
    }

    /**
     * @return the name of the current camera, or null if no camera is set.
     */
    public String getCurrentCameraName(){
        return currentCamera;
    }

    /**
     * @return the current camera, of null if no camera is set.
     */
    public VideoCamera getCurrentCamera(){
        if (currentCamera == null){
            return null;
        }
        return cameras.get(currentCamera);
    }

    /**
     * Switch the display to a different camera
     * @param name the name of the camera to switch to
     */
    public void switchToCamera(String name){
        VideoCamera camera = cameras.get(name);
        if (camera == null){
            return;
        }
        currentCamera = name;
        server.setSource(camera);
    }



}
