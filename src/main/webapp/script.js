// Copyright 2019 Google LLC
//
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

async function loginStatus() {
    const response = await fetch('/landing');
    if (response.redirected) {
        document.getElementById("dropInLink").setAttribute("href", "/editbio.html");   
    } else {
        document.getElementById("dropInLink").setAttribute("href", "/timeline.html");   
    } 
}

async function loadBio() {
  const blobURLResponse = await fetch('/blobstore-upload-url'); 
  const bloblURL = await blobURLResponse.text();
  document.getElementById("bio-form").action = bloblURL;
  const bioInformationResponse = await fetch('/bio-get'); 
  const bioInformation = await bioInformationResponse.json();
  document.getElementById("profile").setAttribute("src", bioInformation.image); 
  document.getElementById("name").value = bioInformation.name;
  document.getElementById("age").value = bioInformation.age;
  document.getElementById("gender").value = bioInformation.gender;
  document.getElementById("aboutme").value = bioInformation.aboutme;
}

function uploadProfile() {
  document.getElementById("image").click();
}

async function loadTimelineBio() {
  const bioInformationResponse = await fetch('/bio-get'); 
  const bioInformation = await bioInformationResponse.json();
  
}