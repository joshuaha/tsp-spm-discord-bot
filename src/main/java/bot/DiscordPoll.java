package bot;

import java.util.*;

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
        this.votes = new ArrayList<>();
        for (String option : this.options) {
            this.votes.add(new HashSet<>());
        }
    }

    public void setVote(long user, int vote) {
        this.removeVote(user);
        this.votes.get(vote).add(user);
    }

    public void removeVote(long user) {
        for (Set<Long> vote : this.votes) {
            vote.remove(user);
        }
    }

    public List<Integer> getResults() {
        final List<Integer> results = new ArrayList<>();
        for (int i = 0; i < this.options.size(); i++) {
            results.add(this.votes.get(i).size());
        }
        return Collections.unmodifiableList(results);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
