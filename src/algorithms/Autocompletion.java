package algorithms;

import java.util.*;
import java.util.stream.Collectors;

/**
 * {@link Autocompletion} for {@link String}.
 */
public final class Autocompletion {

    private final TrieNode rootNode = new TrieNode('$', null);

    public Autocompletion() { }

    public final void insertWord(final String word) {
        rootNode.insertWord(word.trim().toLowerCase());
    }

    public final List<String> predictWords(final String prefix) {
        return rootNode.findCompletions(prefix.trim().toLowerCase())
                .stream()
                .map(string -> String.format("%s%s", prefix.trim().toLowerCase(), string))
                .collect(Collectors.toList());
    }

    /**
     * {@link TrieNode} describes a node in a trie and is used for {@link Autocompletion}
     */
    private static final class TrieNode {
        private final char value;
        private final TrieNode parentNode;
        private final List<TrieNode> childNodes;

        private TrieNode(char value, TrieNode parentNode) {
            this.value = value;
            this.parentNode = parentNode;
            this.childNodes = new LinkedList<>();
        }

        private char getValue() {
            return value;
        }

        private TrieNode getParentNode() {
            return parentNode;
        }

        private List<TrieNode> getChildNodes() {
            return childNodes;
        }

        private boolean isLastNode() {
            return childNodes.isEmpty();
        }

        private void insertWord(final String word) {
            if (word.length() != 0) {
                final TrieNode node = childNodes.stream()
                        .filter(filterNode -> filterNode.getValue() == word.charAt(0))
                        .findFirst()
                        .orElse(new TrieNode(word.charAt(0), this));
                node.insertWord(word.substring(1));
                childNodes.add(node);
            }
        }

        @Override
        public final String toString() {
            return "TrieNode{" +
                    "value=" + value +
                    ", childNodes=" + childNodes +
                    '}';
        }

        private Set<String> findCompletions(final String prefix) {
            if (prefix.length() != 0) {
                final Optional<TrieNode> optionalNode = childNodes.stream().filter(trieNode -> trieNode.getValue() == prefix.charAt(0)).findFirst();
                if (optionalNode.isPresent()) {
                    return optionalNode.get().findCompletions(prefix.substring(1));
                } else {
                    return Collections.emptySet();
                }
            } else {
                if (isLastNode()) {
                    return Set.of("");
                } else {
                    var strings = new HashSet<String>();
                    childNodes.forEach(node -> node.findCompletions(prefix).stream().map(string -> String.format("%s%s", String.valueOf(node.getValue()), string)).forEach(strings::add));
                    return strings;
                }
            }
        }
    }
}