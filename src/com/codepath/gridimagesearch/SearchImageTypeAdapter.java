package com.codepath.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchImageTypeAdapter extends ArrayAdapter<String> {

	List<String> items = null;
	List<Integer> icons = null;

	public SearchImageTypeAdapter(Context context, List<String> objects,
			List<Integer> imgList) {

		super(context, R.layout.img_type_item, objects);

		items = objects;

		// dialog list icons: some examples here
		icons = imgList;

	}

	ViewHolder holder;

	class ViewHolder {
		ImageView icon;
		TextView title;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.img_type_item, null);

			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.ivTypeItem);
			holder.title = (TextView) convertView.findViewById(R.id.tvTypeItem);
			convertView.setTag(holder);
		} else {
			// view already defined, retrieve view holder
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(items.get(position));

		holder.icon.setImageResource(icons.get(position));
		return convertView;
	}
}
