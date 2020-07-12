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
  const text = await response.text();
  document.getElementById("button").setAttribute("href", text);    
}

async function loadBio() {
  const blobURLResponse = await fetch('/blobstore-upload-url'); 
  const bloblURL = await bloblURLResponse.text();
  document.getElementById("bio-form").action = bloblURL;
  const bioInformationResponse = await fetch('/bioGet'); 
  const bioInformation = await bioInformationResponse.json();
  document.getElementById("profile").setAttribute("src", bioInformation["images"]); 
  document.getElementById("name").value = bioInformation["name"];
  document.getElementById("age").value = bioInformation["age"];
  document.getElementById("gender").value = bioInformation["gender"];
  document.getElementById("aboutme").value = bioInformation["aboutme"];
}

$("#profile").click(function(e) {
    $("#image").click();
});