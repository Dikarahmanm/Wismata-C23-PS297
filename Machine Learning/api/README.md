Cloud build & Deploy 


<pre><code> gcloud builds submit --tag gcr.io/phrasal-faculty-387812/get_recommendation_rating </code> </pre>
<pre><code> gcloud run deploy --image gcr.io/phrasal-faculty-387812/get_recommendation_rating --platform managed </code> </pre>