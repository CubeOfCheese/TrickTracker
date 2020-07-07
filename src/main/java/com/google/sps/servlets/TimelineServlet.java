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

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/** Servlet that handles Timeline node data */
@WebServlet("/timeline_data")
public class TimelineServlet extends HttpServlet {

  public TimelineServlet() {}

    class TrickNode {
      public String trick_name;
      public String date;
      public String link;
      public String notes;

      public TrickNode(String trick_name, String date, String link, String notes) {
          this.trick_name = trick_name;
          this.date = date;
          this.link = link;
          this.notes = notes;
      }
  }

  private String toGson(ArrayList<TrickNode> tricks) {
    Gson gson = new Gson();
    String json = gson.toJson(tricks);
    return json;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // a function that will handle retrieving a user's tricks
    // for now we only send dummy data
    response.setContentType("application/json;");
    ArrayList<TrickNode> tricks = new ArrayList<TrickNode>();

    tricks.add(new TrickNode("Shuvit", "2018-10-25T03:24:00", "https://www.youtube.com/watch?v=5gJl69sBRnw", "Didn't take long!"));
    tricks.add(new TrickNode("Ollie", "2018-10-27T06:10:59", "https://www.youtube.com/watch?v=851cTIcNuDU", "Easier said than done"));
    tricks.add(new TrickNode("Nollie", "2018-10-30T11:42:22", "https://www.youtube.com/watch?v=Vj9OIDO3uuU", "*shivers*"));
    tricks.add(new TrickNode("Pop Shuvit", "2018-11-10T10:01:43", "https://www.youtube.com/watch?v=XmuBAdiSvbE", "I'm flying!"));
    tricks.add(new TrickNode("KickFlip", "2019-02-23T03:45:02", "https://www.youtube.com/watch?v=OsXOGs17PAs", "Thanks Bobby for the moral support."));

    response.getWriter().println(toGson(tricks));
  }
}
