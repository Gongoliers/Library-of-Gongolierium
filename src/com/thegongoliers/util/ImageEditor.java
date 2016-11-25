package com.thegongoliers.util;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ImageEditor {
	public static void drawRectangleToMat(Mat src, Rect rect, Scalar color) {
		Imgproc.rectangle(src, rect.tl(), rect.br(), color);
	}
}
