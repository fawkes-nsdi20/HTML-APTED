/* MIT License
 *
 * Copyright (c) 2017 Mateusz Pawlik
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

package at.unisalzburg.dbresearch.apted.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node data that consists of a name(string),
 * attributes (array of pairs of strings), content (string).
 * Such node data belongs to a node.
 *
 * @see Node
 */
public class ModifiedNodeData
{

    /**
     * The name of a node.
     */
    private String name;

    /**
     * If node is an Element, the content is empty.
     */
    private String content;

    /**
     * If node is a TextNode, the list of attributes is empty.
     */
    private List<Attribute> attributes;

    /**
     * Constructs node data with a specified name, empty attributes and content.
     *
     * @param name string name of a node.
     * @param content string content of the node if it is a TextNode
     */
    public ModifiedNodeData(String name)
    {
        this.name = name;
        this.content = "";
        this.attributes = new ArrayList<Attribute>();
    }

    /**
     * Returns the name of a node.
     *
     * @return node name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the content of a TextNode.
     *
     * @param content string content of a node.
     */
    public void setContent(String content)
    {
        this.content = content;
    }

    /**
     * Returns the content of a node.
     *
     * @return node content.
     */
    public String getContent()
    {
        return content;
    }
}
