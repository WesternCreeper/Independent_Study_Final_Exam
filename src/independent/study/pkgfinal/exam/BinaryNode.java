/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package independent.study.pkgfinal.exam;

/**
 *
 * @author Westley
 */
public class BinaryNode 
{
    private Object object;
    private BinaryNode left;
    private BinaryNode right;
    private int amountOfUse = 0;
    public BinaryNode() 
    {
        object = null;
    }
    public BinaryNode(Object e)
    {
        object = e;
    }
    public BinaryNode(Object e, BinaryNode left, BinaryNode right)
    {
        object = e;
        this.left = left;
        this.right = right;
    }

    /**
    * Returns the current object
    */
    public Object getObject()
    {
        return object; 
    }

    /**
    * Sets the current object to e
    */
    public void setObject(Object e)
    {
        object = e; 
    }

    /**
    * Returns the left node
    */
    public BinaryNode getLeft()
    {
        return left; 
    }

    /**
    * Sets the left node to a new left node
    */
    public void setLeft(BinaryNode newLeft)
    {
        left = newLeft; 
    }

    /**
    * Returns the right node
    */
    public BinaryNode getRight()
    {
        return right; 
    }

    /**
    * Sets the right node to a new right node
    */
    public void setRight(BinaryNode newRight)
    {
        right = newRight; 
    }
   
    /**
    * Finds out if the binary node has any children (1 or 2)
    */
    public boolean hasChildren()
    {
        return right != null || left != null;
    }

    /**
    * Removes the child given, if the given node is not its child then nothing will happen
    */
    public void removeChild(BinaryNode child)
    {
        if(left.equals(child))
        {
           left = null;
        }
        else if(right.equals(child))
        {
           right = null;
        }
    }
    /**
    * This will ONLY return a non-null value when there is one child to this node
    */
    public BinaryNode getChild()
    {
        if(left == null && right != null)
        {
           return right;
        }
        else if(left != null && right == null)
        {
           return left;
        }
        else
        {
           return null;
        }
    }

    /**
    * Replaces the child given, with the second node given. If the first node given is not a child of this node, then nothing will happen
    */
    public void replaceChild(BinaryNode child, BinaryNode node)
    {
        if(left.equals(child))
        {
           left = node;
        }
        else if(right.equals(child))
        {
           right = node;
        }
    }

    public int getHeight()
    {
        return getHeight(this);
    }
    private int getHeight(BinaryNode node)
    {
        if(!node.hasChildren())
        {
           return 0;
        }
        int leftHeight = 0;
        if(node.getLeft() != null)
        {
           leftHeight = getHeight(node.getLeft());
        }
        int rightHeight = 0;
        if(node.getRight() != null)
        {
           rightHeight = getHeight(node.getRight());
        }

        if(rightHeight > leftHeight)
        {
           return rightHeight + 1;
        }
        else
        {
           return leftHeight + 1;
        }
    }

    public void setAmountOfUse(int amountOfUse) {
        this.amountOfUse = amountOfUse;
    }

    public int getAmountOfUse() {
        return amountOfUse;
    }
    
    public int getLeftColor()
    {
        if(getLeft() == null)
        {
            return 2;
        }
        return getLeft().getAmountOfUse();
    }
    
    public int getRightColor()
    {
        if(getRight() == null)
        {
            return 2;
        }
        return getRight().getAmountOfUse();
    }
}
