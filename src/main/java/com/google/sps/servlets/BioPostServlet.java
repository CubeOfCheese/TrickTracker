// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson; 
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/bio-post")
public class BioPostServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      UserService userService = UserServiceFactory.getUserService();
      if (!userService.isUserLoggedIn()) {
          response.sendRedirect("/dropin.html");
          return; 
      }  

      String id = userService.getCurrentUser().getUserId(); 
      String image = getUploadedFileUrl(request, "image");
      String name = request.getParameter("name");
      String age = request.getParameter("age");
      String pronouns = request.getParameter("pronouns");
      String aboutme = request.getParameter("aboutme");

      if (image == null) {
          image = "images/default.jpg";
      }

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
      Entity entity = new Entity("UserBiography", id);
      entity.setProperty("id", id); 
      entity.setProperty("image", image);
      entity.setProperty("name", name);
      entity.setProperty("age", age);
      entity.setProperty("pronouns", pronouns); 
      entity.setProperty("aboutme", aboutme);

      datastore.put(entity);

      response.sendRedirect("/timeline.html");
  }

  private String getUploadedFileUrl(HttpServletRequest request, String formInputElementName) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("image");

    if (blobKeys == null || blobKeys.isEmpty()) {
      return null;
    }

    int index = blobKeys.size() - 1; 
    BlobKey blobKey = blobKeys.get(index); /* Grab the most recent uploaded image */

    BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
    if (blobInfo.getSize() == 0) {
      blobstoreService.delete(blobKey);
      return null;
    }

    ImagesService imagesService = ImagesServiceFactory.getImagesService();
    ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
    String url = imagesService.getServingUrl(options);

    if (url.startsWith("http://localhost:8080/")) {
      url = url.replace("http://localhost:8080/", "/");
    }
    return url;
  }

}