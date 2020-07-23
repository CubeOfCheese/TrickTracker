var timelineTricks;

function loadResults() {
  fetch('/timeline_data').then(response => response.json()).then((tricks) => {
    timelineTricks = tricks;
    const timeline = document.getElementById('timeline');
    var counter = 0;
    tricks.forEach((trick) => {
      timeline.appendChild(createTrickElement(trick, counter));
      counter++;
    })
  });
  const bioInformationResponse = fetch('/bio-get'); 
  const bioInformation = bioInformationResponse.json();
  document.getElementById("profPicDefault").setAttribute("src", bioInformation.image); 
  document.getElementById("name").value = bioInformation.name;
  document.getElementById("bioInfo").value = bioInformation.age + ", " + bioInformation.pronouns;
  document.getElementById("bioAboutme").value = bioInformation.aboutme; 
}

function createTrickElement(trick, number) {
    const trickElement = document.createElement('span');
    trickElement.classList.add("timeline-node");
    trickElement.setAttribute('onclick', "displayTrick("+number+")");

    const nodeImage = document.createElement('img');
    nodeImage.classList.add("timeline-node");
    nodeImage.src = "images/timeline-node-and-connector.svg";

    trickElement.append(nodeImage);

    return trickElement;
}
function displayTrick(nodeId) {
    var trick = timelineTricks[nodeId]
    var date = new Date(trick.date);
    
    document.getElementById("skate-style").innerText = "Skate Style: " + trick.skate_style;
    document.getElementById("trick-name").innerText = "Trick: " + trick.trick_name;
    document.getElementById("date").innerText = "Date: " + date.toDateString();
    document.getElementById("link").innerText = "Link: " + trick.link;
    document.getElementById("notes").innerText = "Notes: " + trick.notes;
    document.getElementById("trick-media").setAttribute("src", trick.trick_media);
}
