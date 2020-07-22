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

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.TrickNode;
import com.google.appengine.api.users.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;  
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/** Servlet that adds tricks to a user's timeline / datastore */
@WebServlet("/add-trick")
public class AddTrickServlet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/dropin.html");
      return; 
    }
    User user = userService.getCurrentUser();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
    Date date;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); 
    ParsePosition ps = new ParsePosition(0); 
    String formDate = request.getParameter("date");
    try {
      date = formatter.parse(formDate, ps);
    }
    catch (Exception e) {
      System.out.println("Date parsing failed when adding a trick to store; auto creating new date");
      System.out.println(formDate);
      date = new Date();
    }

    // create entity from request form
    Entity newTrick = new Entity("Trick");
    newTrick.setProperty("trick-name", request.getParameter("trick-name"));
    newTrick.setProperty("link", request.getParameter("link"));
    newTrick.setProperty("notes", request.getParameter("notes"));
    newTrick.setProperty("date", date.getTime());
    newTrick.setProperty("id", user.getUserId());
    datastore.put(newTrick);

    response.sendRedirect("/timeline.html");
  }
}