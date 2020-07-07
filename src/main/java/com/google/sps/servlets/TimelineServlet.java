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

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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

  class TimelineNode {
      public String trickName;
      public String date;
      public String mediaLink;
      public String notes

      public TimelineNode(String trickName, String date, String mediaLink, String notes) {
          this.trickName = trickName;
          this.date = date;
          this.mediaLink = mediaLink;
          this.notes = notes;
      }
  }

  private String toGson(ArrayList<TimelineNode> tricks) {
    Gson gson = new Gson();
    String json = gson.toJson(tricks);
    return json;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // a function that will handle retrieving a user's tricks
    // for now we only send dummy data
    response.setContentType("application/json;");
    ArrayList<TimelineNode> timelineNodes = new ArrayList<TimelineNode>();

    timelineNodes.add(new timelineNode("Shuvit", "2018-10-25T03:24:00", "https://www.youtube.com/watch?v=5gJl69sBRnw", "Didn't take long!"));
    timelineNodes.add(new timelineNode("Ollie", "2018-10-27T06:10:59", "https://www.youtube.com/watch?v=851cTIcNuDU", "Easier said than done"));
    timelineNodes.add(new timelineNode("Nollie", "2018-10-30T11:42:22", "https://www.youtube.com/watch?v=Vj9OIDO3uuU", "*shivers*"));
    timelineNodes.add(new timelineNode("Pop Shuvit", "2018-11-10T10:01:43", "https://www.youtube.com/watch?v=XmuBAdiSvbE", "I'm flying!"));
    timelineNodes.add(new timelineNode("KickFlip", "2019-02-23T03:45:02", "https://www.youtube.com/watch?v=OsXOGs17PAs", "Thanks Bobby for the moral support."));

    response.getWriter().println(toGson(timelineNodes));
  }
}
