package web;



import java.util.HashMap;
import twitteranalysis.HashtagObject;
import twitteranalysis.SentimentObject;
import twitteranalysis.TweetTreatment;


/**
 *
 * @author u124320
 */
public class SimpleHTMLExtractor {
        
    
    public static String extractContent(String tabName){
    
        String content = "";
        switch(tabName){
            case "Hashtags":
                content += extractHashtags();
                break;
            case "UsersMentioned":
                content += extractUsersMentioned();
                break;
            case "Date":
                content += extractDate();
                break;
            /*
            case "HeatMap":
                content += extractHeatMap();
                break;
            case "CircleMap":
                content += extractCircleMap();
                break;
            */
        }
        
        return content;
    }
    

    public static String extractHashtags(){
        String content = "";
        TweetTreatment tTreatment = new TweetTreatment();
        HashMap<String, HashtagObject> hashMap = tTreatment.getHashtagSentimentInfo();
        content += "<div class=\"contentTabulator\" id=\"Hashtags\" style=\"display:none;padding:2%;\">\n<center>";
            for(Object s: hashMap.keySet()){
                content += "<div id=\"chart_div" + s + "\" class=\"chart\" ></div>\n";
            }
        content += "</center>\n</div>\n\n";
        content += "<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>\n";

        for(Object s: hashMap.keySet()){
            HashtagObject v = (HashtagObject) hashMap.get(s);
            int positive = v.getSentiments().getPositive();
            int negative = v.getSentiments().getNegative();
            int neutral = v.getSentiments().getNeutral();
        
        
        content +=   "<script type='text/javascript'>\n" +
                    "\n" +
                    "      google.charts.load('current', {'packages':['corechart']});\n" +
                    "\n" +
                    "      google.charts.setOnLoadCallback(drawChart);\n" +
                    "\n" +
                    "      function drawChart() {\n" +
                    "\n" +
                    "        var data = new google.visualization.DataTable();\n" +
                    "        data.addColumn('string', 'Type');\n" +
                    "        data.addColumn('number', 'Quantity');\n" +
                    "        data.addRows([\n" +
                    "          ['Positive', "+ positive + "],\n" +
                    "          ['Negative', "+ negative + "],\n" +
                    "          ['Neutrals', "+ neutral + "],\n" +
                    "        ]);\n" +
                    "\n" +
                    "        // Set chart options\n" +
                    "        var options = {'title':'"+ s + "',\n" +
                    "                       'width':800,\n" +
                    "                       'height':600};\n" +
                    "\n" +
                    "        var chart = new google.visualization.PieChart(document.getElementById('chart_div"+s+"'));\n" +
                    "        chart.draw(data, options);\n" +
                    "      }\n" +
                    "    </script>\n";
        
        }
        return content;

    
    
    }
    
        
    public static String extractUsersMentioned(){
    
        String content = "";
        TweetTreatment tTreatment = new TweetTreatment();
        HashMap<String, SentimentObject> hashMapMentionedUsers = tTreatment.getHashtagMentionedUsersInfo();
        content += "<div class=\"contentTabulator\" id=\"UsersMentioned\" style=\"display:none;padding:2%;\">\n<center>";
            for(Object s: hashMapMentionedUsers.keySet()){
                if(!s.equals("")){
                    content += "<div id=\"chart_div" + s + "\" class=\"chart\" ></div>\n";
                }
            }
        content += "</center>\n</div>\n\n";

        for(Object s: hashMapMentionedUsers.keySet()){
            SentimentObject v = (SentimentObject) hashMapMentionedUsers.get(s);
            int positive = v.getPositive();
            int negative = v.getNegative();
            int neutral = v.getNeutral();
        
        
        content +=   "<script type='text/javascript'>\n" +
                    "\n" +
                    "      google.charts.load('current', {'packages':['corechart']});\n" +
                    "\n" +
                    "      google.charts.setOnLoadCallback(drawChart);\n" +
                    "\n" +
                    "      function drawChart() {\n" +
                    "\n" +
                    "        var data = new google.visualization.DataTable();\n" +
                    "        data.addColumn('string', 'Type');\n" +
                    "        data.addColumn('number', 'Quantity');\n" +
                    "        data.addRows([\n" +
                    "          ['Positive', "+ positive + "],\n" +
                    "          ['Negative', "+ negative + "],\n" +
                    "          ['Neutrals', "+ neutral + "],\n" +
                    "        ]);\n" +
                    "\n" +
                    "        // Set chart options\n" +
                    "        var options = {'title':'"+ s + "',\n" +
                    "                       'width':800,\n" +
                    "                       'height':600};\n" +
                    "\n" +
                    "        var chart = new google.visualization.PieChart(document.getElementById('chart_div"+s+"'));\n" +
                    "        chart.draw(data, options);\n" +
                    "      }\n" +
                    "    </script>\n";
        
        }
        return content;

    }
    
        
    public static String extractDate(){
    
        String content = "";
        TweetTreatment tTreatment = new TweetTreatment();
        HashMap<String, SentimentObject> hashMapDate = tTreatment.getHashtagDateInfo();
        content += "<div class=\"contentTabulator\" id=\"Date\" style=\"display:none;padding:2%;\">\n<center>";
            for(Object s: hashMapDate.keySet()){
                content += "<div id=\"chart_div" + s + "\" class=\"chart\" ></div>\n";
            }
        content += "</center>\n</div>\n\n";

        for(Object s: hashMapDate.keySet()){
            SentimentObject v = (SentimentObject) hashMapDate.get(s);
            int positive = v.getPositive();
            int negative = v.getNegative();
            int neutral = v.getNeutral();
        
        
        content +=   "<script type='text/javascript'>\n" +
                    "\n" +
                    "      google.charts.load('current', {'packages':['corechart']});\n" +
                    "\n" +
                    "      google.charts.setOnLoadCallback(drawChart);\n" +
                    "\n" +
                    "      function drawChart() {\n" +
                    "\n" +
                    "        var data = new google.visualization.DataTable();\n" +
                    "        data.addColumn('string', 'Type');\n" +
                    "        data.addColumn('number', 'Quantity');\n" +
                    "        data.addRows([\n" +
                    "          ['Positive', "+ positive + "],\n" +
                    "          ['Negative', "+ negative + "],\n" +
                    "          ['Neutrals', "+ neutral + "],\n" +
                    "        ]);\n" +
                    "\n" +
                    "        // Set chart options\n" +
                    "        var options = {'title':'"+ s + "',\n" +
                    "                       'width':800,\n" +
                    "                       'height':600};\n" +
                    "\n" +
                    "        var chart = new google.visualization.PieChart(document.getElementById('chart_div"+s+"'));\n" +
                    "        chart.draw(data, options);\n" +
                    "      }\n" +
                    "    </script>\n";
        
        }
        return content;

    }
    

}

    