package com.codepath.gridimagesearch;

import java.util.ArrayList;
import java.util.List;

public class ImageSearchOptions {

	private List<String> imageSizeList;

	private List<String> colorFilterList;
	
	private List<String> imageTypeList;
	private List<Integer> imageTypeIconList;

	public List<Integer> getImageTypeIconList() {
		return imageTypeIconList;
	}

	public void setImageTypeIconList(List<Integer> imageTypeIconList) {
		this.imageTypeIconList = imageTypeIconList;
	}

	public ImageSearchOptions(){
		imageSizeList = new ArrayList<String>();
		colorFilterList = new ArrayList<String>();
		imageTypeList = new ArrayList<String>();
		imageTypeIconList = new ArrayList<Integer>();
	}

	public List<String> getImageSizeList() {
		return imageSizeList;
	}

	public void setImageSizeList(List<String> imageSizeList) {
		this.imageSizeList = imageSizeList;
	}

	public List<String> getColorFilterList() {
		return colorFilterList;
	}

	public void setColorFilterList(List<String> colorFilterList) {
		this.colorFilterList = colorFilterList;
	}

	public List<String> getImageTypeList() {
		return imageTypeList;
	}

	public void setImageTypeList(List<String> imageTypeList) {
		this.imageTypeList = imageTypeList;
	}


	
	
}
