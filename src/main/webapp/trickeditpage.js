function fetchBlobstoreUrlAndShowForm() {
    console.log("fetch ran")
  fetch('/blobstore-upload-url')
      .then((response) => {
        console.log(response);
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('trick-form');
        messageForm.action = imageUploadUrl;
        messageForm.classList.remove('hidden');
      });
}