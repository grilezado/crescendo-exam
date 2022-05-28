package co.crescendo.exam.service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DetectFacesGcsServiceImpl implements DetectFacesGcsService {

  @Autowired
  private Vision vision;

  public List<FaceAnnotation> detectFaces(String path, int maxResults) throws IOException {

    if(path == null){
      return null;
    }

    AnnotateImageRequest request =
            new AnnotateImageRequest()
                    .setImage(new Image().setSource(new ImageSource().setImageUri(path)))
                    .setFeatures(
                            ImmutableList.of(
                                    new Feature().setType("FACE_DETECTION").setMaxResults(maxResults)));
    Vision.Images.Annotate annotate =
            vision
                    .images()
                    .annotate(new BatchAnnotateImagesRequest().setRequests(ImmutableList.of(request)));

    annotate.setDisableGZipContent(true);

    BatchAnnotateImagesResponse batchResponse = annotate.execute();
    assert batchResponse.getResponses().size() == 1;
    AnnotateImageResponse response = batchResponse.getResponses().get(0);
    if (response.getFaceAnnotations() == null) {
      return null;
    }

    return response.getFaceAnnotations();
  }

}
