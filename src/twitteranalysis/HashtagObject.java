package twitteranalysis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author u124320
 */
public class HashtagObject {
    public String topic;
    public List<String> hastagsNames = new ArrayList<String>();

    public List<String> getHastagsNames() {
        return hastagsNames;
    }

    public void setHastagsNames(List<String> hastagsNames) {
        this.hastagsNames = hastagsNames;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
