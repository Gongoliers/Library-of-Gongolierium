package com.thegongoliers.input.vision;

import edu.wpi.cscore.VideoCamera;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MultipleCameraServerTest {

    @Test
    public void hasNoCameras(){
        MultipleCameraServer cameraServer = new MultipleCameraServer("server");
        assertNull(cameraServer.getCurrentCamera());
        assertNull(cameraServer.getCurrentCameraName());
    }

    @Test
    public void worksWithOneCamera(){
        MultipleCameraServer cameraServer = new MultipleCameraServer("server");
        VideoCamera camera = mock(VideoCamera.class);
        cameraServer.addCamera("camera1", camera);
        assertEquals(camera, cameraServer.getCurrentCamera());
        assertEquals("camera1", cameraServer.getCurrentCameraName());
    }

    @Test
    public void worksWithTwoCameras(){
        MultipleCameraServer cameraServer = new MultipleCameraServer("server");
        VideoCamera camera1 = mock(VideoCamera.class);
        VideoCamera camera2 = mock(VideoCamera.class);

        cameraServer.addCamera("camera1", camera1);
        cameraServer.addCamera("camera2", camera2);

        assertEquals(camera1, cameraServer.getCurrentCamera());
        assertEquals("camera1", cameraServer.getCurrentCameraName());
    }

    @Test
    public void switchesCameras(){
        MultipleCameraServer cameraServer = new MultipleCameraServer("server");
        VideoCamera camera1 = mock(VideoCamera.class);
        VideoCamera camera2 = mock(VideoCamera.class);

        cameraServer.addCamera("camera1", camera1);
        cameraServer.addCamera("camera2", camera2);

        cameraServer.switchToCamera("camera2");

        assertEquals(camera2, cameraServer.getCurrentCamera());
        assertEquals("camera2", cameraServer.getCurrentCameraName());
    }

}