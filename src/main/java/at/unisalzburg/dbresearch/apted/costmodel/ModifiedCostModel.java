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

package at.unisalzburg.dbresearch.apted.costmodel;

import at.unisalzburg.dbresearch.apted.costmodel.CostModel;
import at.unisalzburg.dbresearch.apted.node.Node;
import at.unisalzburg.dbresearch.apted.node.ModifiedNodeData;

/**
 * This is a unit-cost model defined on string labels.
 *
 * @see CostModel
 * @see ModifiedNodeData
 */
public class ModifiedCostModel implements CostModel<ModifiedNodeData>
{

    /**
     * Calculates the cost of deleting a node.
     *
     * @param n a node considered to be deleted.
     * @return {@code 1} - a fixed cost of deleting a node.
     */
    public float del(Node<ModifiedNodeData> n)
    {
        return 1.0f;
    }

    /**
     * Calculates the cost of inserting a node.
     *
     * @param n a node considered to be inserted.
     * @return {@code 1} - a fixed cost of inserting a node.
     */
    public float ins(Node<ModifiedNodeData> n)
    {
        return 1.0f;
    }

    /**
     * Calculates the cost of renaming the label of the source node to the label
     * of the destination node.
     *
     * @param n1 a source node for rename.
     * @param n2 a destination node for rename.
     * @return {@code 0} or {@code 1} if names of renamed nodes are equal, and {@code Float.MAX_VALUE} otherwise.
     */
    public float ren(Node<ModifiedNodeData> n1, Node<ModifiedNodeData> n2)
    {
        if (n1.getNodeData().getName().equals(n2.getNodeData().getName()))
        {
            if (n1.getNodeData().getName().equals("text"))
            {
                return (n1.getNodeData().getContent().equals(n2.getNodeData().getContent())) ? 0.0f : 1.0f;
            }
            else
                return 0.0f; // TODO: check the attributes
        }
        else
            return Float.MAX_VALUE;
    }
}
