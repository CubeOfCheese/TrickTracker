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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
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
import java.util.Map;

@WebServlet("/bio-get")
public class BioGetServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("application/json");
      
      UserService userService = UserServiceFactory.getUserService();
      if (!userService.isUserLoggedIn()) {
          response.sendRedirect("/dropin.html");
          return; 
      }
        
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
      Query query =
          new Query("UserBiography")
              .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, userService.getCurrentUser().getUserId()));
      PreparedQuery results = datastore.prepare(query);
      Entity entity = results.asSingleEntity(); 

      Map<String, String> values = new HashMap<String, String>();

      if (entity != null) {
        values.put("image", (String) entity.getProperty("image"));
        values.put("name", (String) entity.getProperty("name"));
        values.put("age", (String) entity.getProperty("age"));
        values.put("gender", (String) entity.getProperty("gender"));
        values.put("aboutme", (String) entity.getProperty("aboutme"));
      } else {
        values.put("image", "images/default.jpg");
        values.put("name", "");
        values.put("age", "");
        values.put("gender", "");
        values.put("aboutme", "");
      }

      Gson gson = new Gson(); 
      String json = gson.toJson(values);

      response.getWriter().println(json);
  }

}