Cloud build & Deploy 


<pre><code> gcloud builds submit --tag gcr.io/phrasal-faculty-387812/index </code> </pre>
<pre><code> gcloud run deploy --image gcr.io/phrasal-faculty-387812/index --platform managed </code> </pre>