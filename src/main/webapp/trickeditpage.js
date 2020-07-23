async function loadTrickForm() {
  const blobURLResponse = await fetch('/blobstore-upload-trick-url'); 
  const blobURL = await blobURLResponse.text();
  document.getElementById("trick-form").action = blobURL;
}