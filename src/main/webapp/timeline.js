function addNode() {
    var timeline = document.getElementById("timeline");
    console.log(timeline.innerHTML)
    timeline.innerHTML = timeline.innerHTML.concat("<span class=\"timeline-node\"><>--</span>");
}

function results() {
    var skateStyle = document.getElementById('skate-style').value;
    var trickName = document.getElementById('trick-name').value;
    var date = document.getElementById('date').value;
    var media = document.getElementById('media').value;
    var notes = document.getElementById('notes').value;

    document.write("<h3>Skate Style: </h3>" + skateStyle + "<br/>")
    document.write("<h3>Trick: </h3>" + trickName + "<br/>")
    document.write("<h3>Date: </h3>" + date + "<br/>")
    document.write("<h3>Media link: </h3>" + media + "<br/>")
    document.write("<h3>Notes: </h3>" + notes + "<br/>")
}