function addNode() {
    var timeline = document.getElementById("timeline");
    console.log(timeline.innerHTML)
    timeline.innerHTML = timeline.innerHTML.concat("<span class=\"timeline-node\"><>--</span>");
}