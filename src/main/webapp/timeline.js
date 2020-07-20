function addNode() {
    var timeline = document.getElementById("timeline");
    console.log(timeline.innerHTML)
    timeline.innerHTML = timeline.innerHTML.concat("<span class=\"timeline-node\"><>--</span>");
}

function loadResults() {
  fetch('/timeline_data').then(response => response.json()).then((tricks) => {
    const trickNode = document.getElementById('timeline');
    tricks.forEach((trick) => {
      trickNode.appendChild(createTrickElement(trick));
    })
  });
}

function createTrickElement(trick) {
  const trickElement = document.createElement('div')


  const trickNameElement = document.createElement('span');
  trickNameElement.innerText = trick.trick_name;

  const dateElement = document.createElement('span');
  dateElement.innerText = trick.date;

  const linkElement = document.createElement('span');
  linkElement.innerText = trick.link;

  const notesElement = document.createElement('span');
  notesElement.innerText = trick.notes;

  var trickTitle = document.createTextNode("Trick: ");
  var dateTitle = document.createTextNode("Date: ");
  var linkTitle = document.createTextNode("Link: ");
  var notesTitle = document.createTextNode("Notes: ");
  var br1 = document.createElement("br");
  var br2 = document.createElement("br");
  var br3 = document.createElement("br");
  var br4 = document.createElement("br");
  var br5 = document.createElement("br");

  trickElement.appendChild(trickTitle);
  trickElement.appendChild(trickNameElement);
  trickElement.appendChild(br1);
  trickElement.appendChild(dateTitle);
  trickElement.appendChild(dateElement);
  trickElement.appendChild(br2);
  trickElement.appendChild(linkTitle);
  trickElement.appendChild(linkElement);
  trickElement.appendChild(br3);
  trickElement.appendChild(notesTitle);
  trickElement.appendChild(notesElement);
  trickElement.appendChild(br4);
  trickElement.appendChild(br5);
  return trickElement;
}
