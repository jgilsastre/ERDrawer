package es.test.jgilsastre.view.menu;

import java.util.List;
import es.test.jgilsastre.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationAdapter extends BaseAdapter {
	
	private Activity activity;  
	private List<MenuItem> items;
	 
	/**
	 * @param activity
	 * @param items
	 */
	public NavigationAdapter(Activity activity, List<MenuItem> items) {
		super();
		this.activity = activity;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class Row{
		TextView textItem;
		ImageView iconItem;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Row view;
		LayoutInflater inflater = activity.getLayoutInflater();
		if(convertView==null){
			view = new Row();
			MenuItem item = items.get(position);
			convertView = inflater.inflate(R.layout.menu_item, null);
			view.textItem = (TextView) convertView.findViewById(R.id.title_item);
			view.textItem.setText(item.getText());
			view.iconItem = (ImageView) convertView.findViewById(R.id.icon);
			view.iconItem.setImageResource(item.getIcon());
			convertView.setTag(view);
		}else{
			view = (Row) convertView.getTag();
		}
		return convertView;
	}

}
