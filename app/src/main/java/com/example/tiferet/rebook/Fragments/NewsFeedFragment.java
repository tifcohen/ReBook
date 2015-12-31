package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiferet.rebook.R;

/**
 * Created by TIFERET on 30-Dec-15.
 */
public class NewsFeedFragment extends Fragment {

    public interface NewsFeedFragmentDelegate{}

    NewsFeedFragmentDelegate delegate;
    public void setDelegate(NewsFeedFragmentDelegate delegate){
        this.delegate = delegate;
    }

    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_feed_fragment, container, false);

        list = (ListView) view.findViewById(R.id.newsFeedFragment);
        //data = StudentDB.getInstance().getAllStudents();


        return view;
    }


    /*class CustomAdapter extends BaseAdapter {

        public CustomAdapter() {
        }

        @Override
        public int getCount() { //returns the size of the list
            return data.size();
        }

        @Override
        public Object getItem(int position) { //returns the student
            return data.get(position);
        }

        @Override
        public long getItemId(int position) { //returns student id
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.single_student_row, null);
                CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Student st = data.get((Integer) v.getTag());
                        Log.d("TAG", "cb position " + (Integer) v.getTag());
                        st.setSelected(!st.isSelected());

                    }
                });
            }
            TextView name = (TextView) convertView.findViewById(R.id.stdName);
            TextView id = (TextView) convertView.findViewById(R.id.stdId);
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
            ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

            Student st = data.get(position);
            name.setText(st.getName());
            id.setText(st.getStId());
            cb.setChecked(st.isSelected());
            cb.setTag(new Integer(position));

            return convertView;
        }
    }*/
}
