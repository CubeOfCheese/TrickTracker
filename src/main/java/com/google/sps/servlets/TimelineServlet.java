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

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.TrickNode;
import com.google.appengine.api.users.*;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

/** Servlet that handles Timeline node data */
@WebServlet("/timeline_data")
public class TimelineServlet extends HttpServlet {

  private String toGson(ArrayList<TrickNode> tricks) {
    Gson gson = new Gson();
    String json = gson.toJson(tricks);
    return json;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {    
    // a function that will handle retrieving a user's tricks
    response.setContentType("application/json;");

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return; 
    }
    boolean hasBiography = hasBiography(userService.getCurrentUser().getUserId()); 
    if (!hasBiography) {
        System.out.println("No bio");
        response.sendRedirect("/editbio.html"); 
    }
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return; 
    }

    User user = userService.getCurrentUser();
    String userId = user.getUserId();
    // note: not very secure considering we do no encyrption here
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query =  new Query("Trick")
        .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, userId));
    PreparedQuery results = datastore.prepare(query);

    // retrieve user tricks 
    // prints empty json is user has no tricks
    ArrayList<TrickNode> tricks = new ArrayList<TrickNode>();
    for (Entity entity : results.asIterable()) {
      String skate_style = (String) entity.getProperty("skate-style");
      String trick_name = (String) entity.getProperty("trick-name");
      long date = (long) entity.getProperty("date");
      String link = (String) entity.getProperty("link");
      String notes = (String) entity.getProperty("notes");
      String trick_media = (String) entity.getProperty("trick-media");

      TrickNode trick = new TrickNode(trick_name, date, link, notes, skate_style, trick_media);
      tricks.add(trick);
    }
    String responseBody = toGson(tricks);
    response.getWriter().println(responseBody);
  }

  private boolean hasBiography(String id) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
    Query query =
        new Query("UserBiography")
            .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    if (entity == null) {
        return false; 
    }
    return true;       
  }
}
