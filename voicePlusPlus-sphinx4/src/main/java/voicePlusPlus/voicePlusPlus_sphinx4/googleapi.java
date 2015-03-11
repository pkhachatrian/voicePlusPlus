package voicePlusPlus.voicePlusPlus_sphinx4;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import com.google.gson.Gson;

public class googleapi {

	 
//		public static void main(String[] args) throws IOException {
//	 
//			String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
//			String query = "what time is it";
//			String charset = "UTF-8";
//	 
//			URL url = new URL(address + URLEncoder.encode(query, charset));
//			Reader reader = new InputStreamReader(url.openStream(), charset);
//			GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
//	 
//			int total = results.getResponseData().getResults().size();
//			System.out.println("total: "+total);
//	 
//			// Show title and URL of each results
//			for(int i=0; i<=total-1; i++){
//				System.out.println("Title: " + results.getResponseData().getResults().get(i).getTitle());
//				System.out.println("URL: " + results.getResponseData().getResults().get(i).getUrl() + "\n");
//	 
//			}
//		}
	}
	 
	 
	class GoogleResults{
	 
	    private ResponseData responseData;
	    public ResponseData getResponseData() { return responseData; }
	    public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
	    public String toString() { return "ResponseData[" + responseData + "]"; }
	 
	    static class ResponseData {
	        private List<Result> results;
	        public List<Result> getResults() { return results; }
	        public void setResults(List<Result> results) { this.results = results; }
	        public String toString() { return "Results[" + results + "]"; }
	    }
	 
	    static class Result {
	        private String url;
	        private String title;
	        public String getUrl() { return url; }
	        public String getTitle() { return title; }
	        public void setUrl(String url) { this.url = url; }
	        public void setTitle(String title) { this.title = title; }
	        public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
	    }
	}
