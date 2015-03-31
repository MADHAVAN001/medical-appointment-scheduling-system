package com.example.keith.rgms1;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Keith on 17/3/2015.
 */
public class NewsMgr {
    String news = "News";
    public NewsMgr(){}
    public interface Callback<T>{
        void done(T result);
    }

    public void getAllNews(final Callback<List<HashMap<String,String>>> callback){
//        return news;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(news);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                    for(int i=0;i< object.size();i++) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("txt", object.get(i).getString("subject"));
                        hm.put("cur", object.get(i).getString("description"));
                        hm.put("flag", Integer.toString(object.get(i).getInt("image")));
                        aList.add(hm);
                    }
                    callback.done(aList);
                } else {

                }
            }
        });
    }
}

