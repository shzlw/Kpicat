package com.kpicat.kpicat.screen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kpicat.kpicat.model.Page;
import com.kpicat.kpicat.R;

import java.util.List;

public class PageAdapter extends ArrayAdapter<Page> {

    private String textColor;

    public PageAdapter(Context context, int resource, List<Page> objects, String textColor) {
        super(context, resource, objects);
        this.textColor = textColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Page page = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_page, parent, false);
        }
        // Lookup view for data population
        TextView pageName = (TextView) convertView.findViewById(R.id.pageName);
        pageName.setTextColor(Color.parseColor(textColor));

        TextView pageId = (TextView) convertView.findViewById(R.id.hiddenPageId);

        pageName.setText(page.getName());
        pageId.setText(page.getPageId());

        // Return the completed view to render on screen
        return convertView;
    }
}
