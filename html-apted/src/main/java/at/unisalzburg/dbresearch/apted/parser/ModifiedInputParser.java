/* MIT License
 *
 * Copyright (c) 2017 Mateusz Pawlik, Nikolaus Augsten
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package at.unisalzburg.dbresearch.apted.parser;

import java.util.List;
import java.util.ArrayList;
import at.unisalzburg.dbresearch.apted.node.Node;
import at.unisalzburg.dbresearch.apted.node.ModifiedNodeData;

/**
 * Parser for the input trees in the bracket notation with a single string-value
 * label of type {@link ModifiedNodeData}.
 *
 * <p>Bracket notation encodes the trees with nested parentheses, for example,
 * in tree {A{B{X}{Y}{F}}{C}} the root node has label A and two children with
 * labels B and C. Node with label B has three children with labels X, Y, F.
 *
 * @see Node
 * @see ModifiedNodeData
 */
public class ModifiedInputParser implements InputParser<ModifiedNodeData>
{

    private static int matchingBracket(String s, int pos)
    {
        if(s == null || pos > s.length() - 1)
            return -1;
        char open = s.charAt(pos);
        char close;
        switch(open) {
            case 123: // '{'
                close = '}';
                break;

            case 40: // '('
                close = ')';
                break;

            case 91: // '['
                close = ']';
                break;

            case 60: // '<'
                close = '>';
                break;

            default:
                return -1;
        }
        pos++;

        boolean inDoubleQuotes = false; //inside string content of a TextNode
        int count;
        for(count = 1; count != 0 && pos < s.length(); pos++) {
            if(s.charAt(pos) == '"' && s.charAt(pos-1) != '\\')
                inDoubleQuotes = !inDoubleQuotes;
            else if(!inDoubleQuotes && s.charAt(pos) == open)
                count++;
            else if(!inDoubleQuotes && s.charAt(pos) == close)
                count--;
        }

        if(count != 0)
            return -1;
        else
            return pos - 1;
    }

    private static List<String> getChildren(String s)
    {
        if(s != null && s.length() > 2 && s.startsWith("{") && s.endsWith("}"))
        {
            List<String> children = new ArrayList<>();
            int end = s.indexOf('{', 1);
            if(end == -1)
                return children;
            String rest = s.substring(end, s.length() - 1);
            for(int match = 0; rest.length() > 0 && (match = matchingBracket(rest, 0)) != -1;)
            {
                children.add(rest.substring(0, match + 1));
                if(match + 1 < rest.length())
                    rest = rest.substring(match + 1);
                else
                    rest = "";
            }

            return children;
        } else
        {
            return null;
        }
    }

    private static ModifiedNodeData getRoot(String s)
    {
        if (s != null && s.length() > 2 && s.startsWith("{") && s.endsWith("}"))
        {
            s = s.substring(1, s.length()-1); // remove the opening and closing brackets
            ModifiedNodeData parsed;
            // check if this is a TextNode, the string content might contains other delimiters
            if (s.startsWith("#text"))
            {
                int end = s.indexOf(':', 1);
                parsed = new ModifiedNodeData(s.substring(0, end));
                parsed.setContent(s.substring(end+1)); // including ':'
            }
            else // it is an Element node, should only contain node name & children.
            {
                int end = s.indexOf('{', 1);
                if (end != -1) // it has children
                    s = s.substring(0, end);
                parsed = new ModifiedNodeData(s);
            }
            return parsed;
        }
        else
        {
            return new ModifiedNodeData(null);
        }
    }

    /**
     * Parses the input tree as a string and converts it to our tree
     * representation using the {@link Node} class.
     *
     * @param s input tree as string in bracket notation+string contents.
     * @return tree representation of the bracket notation input.
     * @see Node
     */
    public Node<ModifiedNodeData> fromString(String s)
    {
        s = s.substring(s.indexOf("{"), s.lastIndexOf("}") + 1);
        Node<ModifiedNodeData> node = new Node<ModifiedNodeData>(getRoot(s));
        // look for children only if the node is an element,
        // otherwise the text content might have a '{' or '}' and parser considers them as separate nodes wrongly.
        if (!node.getNodeData().getName().startsWith("#text"))
        {
            // System.out.println(node.getNodeData().getName());
            List<String> c = getChildren(s);
            for(int i = 0; i < c.size(); i++){
                node.addChild(fromString(c.get(i)));
            }
        }

        return node;
    }
}
