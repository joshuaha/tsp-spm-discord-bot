package bot;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DiscordPoll {
    private String name;
    private List<String> options;
    private List<Set<Long>> votes;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOptions() {
        return this.options != null
                ? Collections.unmodifiableList(this.options)
                : Collections.emptyList();
    }

    public void setOptions(List<String> options) {
        this.options = Collections.unmodifiableList(options);
    }

    public void setVote(long user, int vote) {
        this.votes.get(vote).add(user);
    }

    public void removeVote(long user) {
        for (Set<Long> vote : votes) {
            vote.remove(user);
        }
    }
}
