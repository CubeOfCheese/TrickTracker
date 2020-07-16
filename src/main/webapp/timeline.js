function addNode() {
    var timeline = document.getElementById("timeline");
    console.log(timeline.innerHTML)
    timeline.innerHTML = timeline.innerHTML.concat("<span class=\"timeline-node\"><>--</span>");
}

function loadResults() {
  fetch('/timeline_data').then(response => response.json()).then((tricks) => {
    const trickNode = document.getElementById('trick-results');
    tricks.forEach((trick) => {
      trickNode.appendChild(createTrickElement(trick));
    })
  });
}

function createTrickElement(trick) {
  const trickElement = document.createElement('li');
  trickElement.TrickNode = 'trick-container';

  const trickNameElement = document.createElement('span');
  trickElement.innerText = trick.trick_name;

  const dateElement = document.createElement('span');
  trickElement.innerText = trick.date;

  const linkElement = document.createElement('span');
  trickElement.innerText = trick.link;

  const notesElement = document.createElement('span');
  trickElement.innerText = trick.notes;

  trickElement.appendChild(trickNameElement);
  trickElement.appendChild(dateElement);
  trickElement.appendChild(linkElement);
  trickElement.appendChild(notesElement);
  return trickElement;
}