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

  public TimelineServlet() {}

  private String toGson(ArrayList<TrickNode> tricks) {
    Gson gson = new Gson();
    String json = gson.toJson(tricks);
    return json;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // a function that will handle retrieving a user's tricks
    response.setContentType("application/json;");
    // note: assuming that a user will always be logged in
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    String userId = user.getUserId();

    Key userKey = KeyFactory.createKey("Trick", userId);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Trick").addSort("date", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    // retrieve user tricks 
    // prints empty json is user has no tricks
    ArrayList<TrickNode> tricks = new ArrayList<TrickNode>();
    for (Entity entity : results.asIterable()) {
      String trick_name = (String) entity.getProperty("trick_name");
      long date = (long) entity.getProperty("date");
      String link = (String) entity.getProperty("link");
      String notes = (String) entity.getProperty("notes");

      TrickNode trick = new TrickNode(trick_name, date, link, notes);
      tricks.add(trick);
    }
    response.getWriter().println(toGson(tricks));
    response.sendRedirect("/timeline.html");
  }
}
