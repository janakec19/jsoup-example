package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static String div="<div class=\"sticky\" >#DATA#</div>";
    static String li="<li id='#DATA#'>"+div+"</li>";
    public static void main(String[] args) throws IOException, URISyntaxException {
        java.net.URL url = Main.class.getResource("/tree.html");
        File file = new File(url.toURI());
        Document document = Jsoup.parse(file, "UTF-8");
        //System.out.println(document.body()); ;
        Map<String, List<String>> map=new HashMap<>();
        List<String> list=new ArrayList<>();
        list.add("appxGrid.metadata");
        list.add("appxGrid.metadata2");
        list.add("appxGrid.metadata3");
        List<String> list2=new ArrayList<>();
        list2.add("helper.save");
        map.put("appxGrid.loadGrid",list);
        map.put("appxGrid.metadata2",list2);
        AtomicInteger count=new AtomicInteger(0);
        map.entrySet().forEach(e->{
            if(count.get()==0){
               String headerdiv= div.replace("#DATA#",e.getKey());
                Element element=document.select("#header-id").first();
                element.append(headerdiv);
                element.attr("id",e.getKey());
            }
            //System.out.println(document.html());
            Element element= document.select("[id="+e.getKey()+"]").first();
            count.incrementAndGet();
            StringBuilder builder=new StringBuilder();
            e.getValue().forEach(data->{
                builder.append(li.replace("#DATA#",data));
            });
           // System.out.println("e.append");
            if(element!=null)
            element.append("<ul>"+builder.toString()+"</ul>");

        });
        System.out.println(document.outerHtml());


    }
    // Extract the title from the HTML content
    public static String extractTitle(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        return doc.title();
    }

    // Extract the username from the HTML content
    public static String extractUserName(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Elements elements = doc.getElementsByClass("main-nav__username");

        if (!elements.isEmpty()) {
            return elements.first().text();
        }
        return "";
    }
}
