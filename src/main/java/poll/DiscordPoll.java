package poll;

import java.util.*;

public class DiscordPoll {
    private String name;
    private List<DiscordPoll.Option> options;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DiscordPoll.Option> getOptions() {
        return this.options != null
                ? Collections.unmodifiableList(this.options)
                : Collections.emptyList();
    }

    public void setOptions(List<String> options) {
        if (options != null) {
            final List<DiscordPoll.Option> list = new ArrayList<>();
            for (String optionText : options) {
                final DiscordPoll.Option option = new DiscordPoll.Option();
                option.setText(optionText);
                list.add(option);
            }
            this.options = Collections.unmodifiableList(list);
        }
        else {
            this.options = null;
        }
    }

    public void setVote(long user, int vote) {
        this.removeVote(user);
        this.options.get(vote).Vote(user);
    }

    public void removeVote(long user) {
        for (DiscordPoll.Option option : this.options) {
            option.removeVote(user);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static class Option {
        private String text;
        private Set<Long> votes = new HashSet<>();

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getVotes() {
            return this.votes.size();
        }

        private void Vote(long user) {
            this.votes.add(user);
        }

        private void removeVote(long user) {
            this.votes.remove(user);
        }
    }
}
