package co.crescendo.exam.service;

import com.google.api.services.vision.v1.model.FaceAnnotation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface DetectFacesGcsService {

    List<FaceAnnotation> detectFaces(String path, int maxResults) throws IOException;

}
