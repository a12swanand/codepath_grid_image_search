package com.codepath.gridimagesearch;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.net.Uri;

public class ImageRequest implements Serializable{


	private static final long serialVersionUID = 4113485420659124169L;

	private String imageSearchString;

	private String imageSize;

	private String imageType;
	
	private String colorFilter;

	private String siteFilter;

	private int startIndex;

	private int resultSetSize;

	private Map<String, String> mappings = new HashMap<String, String>();

	public ImageRequest() {
		this.setStartIndex(0);
		this.setResultSetSize(8);
		this.setImageSearchString("");
		this.setImageSize("");
		this.setImageType("");
		this.setColorFilter("");
		this.setSiteFilter("");
		
		generateMappings();
	}

	private void generateMappings() {
		mappings.put("imageSearchString", "&q=");
		mappings.put("imageSize", "&imgsz=");
		mappings.put("imageType", "&imgtype=");
		mappings.put("colorFilter", "&imgcolor=");
		mappings.put("siteFilter", "&as_sitesearch=");
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getResultSetSize() {
		return resultSetSize;
	}

	public void setResultSetSize(int resultSetSize) {
		this.resultSetSize = resultSetSize;
	}

	public String getImageSearchString() {
		return imageSearchString;
	}

	public void setImageSearchString(String imageSearchString) {
		this.imageSearchString = imageSearchString;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getSiteFilter() {
		return siteFilter;
	}

	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}

	public String getColorFilter() {
		return colorFilter;
	}

	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}

	public String generateSearchUrl() {
		
		 String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";

		 url = url + "&rsz=" + this.getResultSetSize();

		 url = url + "&start=" + this.getStartIndex();
		 
		 if(!imageSearchString.isEmpty()){
			 url = url + mappings.get("imageSearchString") + Uri.encode(imageSearchString);
		 }
		 
		 if(!imageSize.isEmpty()){
			 url = url + mappings.get("imageSize") + Uri.encode(imageSize);
		 }

		 if(!imageType.isEmpty()){
			 url = url + mappings.get("imageType") + Uri.encode(imageType);
		 }
		 
		 if(!colorFilter.isEmpty()){
			 url = url + mappings.get("colorFilter") + Uri.encode(colorFilter);
		 }
		 
		 if(!siteFilter.isEmpty()){
			 url = url + mappings.get("siteFilter") + Uri.encode(siteFilter);
		 }
	
		 return url;
	}
}
