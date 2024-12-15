/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package independent.study.pkgfinal.exam;

/**
 *
 * @author Westley
 */
public class MazeBinaryTree 
{
    private static String[] tileNames = {"Dead End", "Exit", "Hallway", "Stairs", "Right Turn", "Left Turn"};
    private BinaryNode root;
    private BinaryNode current;
    

    public MazeBinaryTree() {}
    public MazeBinaryTree(Object rootObject)
    {
        root = new BinaryNode(rootObject);
        current = root;
    }

    public boolean isEmpty()
    {
        return root == null;
    }
    public void makeEmpty()
    {
        root = null;
        current = null;
    }

    /**
    * This "re-roots" the tree when called. Don't use unless there is no root, I.e. tree is empty. Or if you want to create a new tree and are fine with all of the data being replaced
    */
    public void addRoot(Object rootObject)
    {
        root = new BinaryNode(rootObject);
        current = root;
    }

    public void front()
    {
        current = root;
    }
    /**
    * This will adance the current position to the left node from the current position. This will never result in the current becoming null
    */
    public void advanceLeft()
    {
        BinaryNode leftNode = current.getLeft();
        if(leftNode != null)
        {
            current = leftNode;
        }
        current.setAmountOfUse(1);
        checkGrayColorAfter(current);
    }
    /**
    * This will adance the current position to the right node from the current position. This will never result in the current becoming null
    */
    public void advanceRight()
    {
        BinaryNode rightNode = current.getRight();
        if(rightNode != null)
        {
            current = rightNode;
        }
        current.setAmountOfUse(1);
        checkGrayColorAfter(current);
    }
    /**
    * This will remove node that is the left of the current position. However, this will also remove any children nodes under that node.
    */
    public void removeLeft()
    {
        current.setLeft(null);
    }
    
    /**
    * This will remove node that is the right of the current position. However, this will also remove any children nodes under that node.
    */
    public void removeRight()
    {
        current.setRight(null);
    }
    
    public Object getCurrent()
    {
        return current.getObject();
    }
    
    public boolean canGoLeft()
    {
        return current.getLeft() == null || ((String)current.getLeft().getObject()).equals("Dead End");
    }
    
    public boolean canGoRight()
    {
        return current.getRight() == null || ((String)current.getRight().getObject()).equals("Dead End");
    }
    
    public String getLeftName()
    {
        if(current.getLeft() == null)
        {
            return "Dead End";
        }
        return (String)current.getLeft().getObject();
    }
    
    public String getRightName()
    {
        if(current.getRight() == null)
        {
            return "Dead End";
        }
        return (String)current.getRight().getObject();
    }
    
    public int getLeftColor()
    {
        recursiveCheckColors(current.getLeft());
        return current.getLeftColor();
    }
    
    public int getRightColor()
    {
        recursiveCheckColors(current.getRight());
        return current.getRightColor();
    }
    
    private void recursiveCheckColors(BinaryNode node)
    {
        if(node == null)
        {
            return;
        }
        
        recursiveCheckColors(node.getLeft());
        recursiveCheckColors(node.getRight());
        
        checkGrayColor(node);
    }
    
    private void checkGrayColor(BinaryNode node)
    {
        if(node != null)
        {
            if(((node.getLeftColor() == 2 || ((String)(node.getLeft().getObject())).equals("Dead End")) && (node.getRightColor() == 2 || ((String)(node.getRight().getObject())).equals("Dead End"))))
            {
                if(node.getLeft() == null && node.getRight() == null)
                {
                    return;
                }
                node.setAmountOfUse(2);
            }
        }
    }
    
    private void checkGrayColorAfter(BinaryNode node)
    {
        if(node != null)
        {
            if(node.getLeft() == null && node.getRight() == null)
            {
                node.setAmountOfUse(2);
            }
            checkGrayColor(node);
        }
    }

    /**
    * Inserts an object to the left of the current position. This will replace the object in the left side if there is one.
    */
    public void insertLeft(Object leftObject)
    {
        current.setLeft(new BinaryNode(leftObject));
    }
    /**
    * Inserts an object to the right of the current position. This will replace the object in the right side if there is one.
    */
    public void insertRight(Object rightObject)
    {
        current.setRight(new BinaryNode(rightObject));
    }
    
    public void generateMap()
    {
        int mapSize = (int)(Math.random() * 5 + 3);
        root = new BinaryNode("Enterance");
        
        //Recursive generation:
        addNext(0, mapSize, root);
        
        //Now put the exit at the end:
        front();
        while(current.hasChildren())
        {
            int side = (int)(Math.random() * 2);
            if(side == 0)
            {
                if(current.getLeft() != null && !current.getLeft().getObject().equals(tileNames[0]))
                {
                    current = current.getLeft();
                }
                else if(current.getRight().getObject().equals(tileNames[0]))
                {
                    current = current.getRight();
                    break;
                }
                else
                {
                    break;
                }
            }
            else
            {
                if(current.getRight() != null && !current.getRight().getObject().equals(tileNames[0]))
                {
                    current = current.getRight();
                }
                else if(current.getLeft().getObject().equals(tileNames[0]))
                {
                    current = current.getLeft();
                    break;
                }
                else
                {
                    break;
                }
            }
        }
        //We Clearly ended out of the loop, so put the exit there:
        if(current.getRight() == null || current.getRight().getObject().equals(tileNames[0]))
        {
            current.setRight(new BinaryNode("Exit"));
        }
        else if(current.getLeft() == null || current.getLeft().getObject().equals(tileNames[0]))
        {
            current.setLeft(new BinaryNode("Exit"));
        }
        front();
        
        //Recursively check the map next to make sure there it is a winable map:
        if(!recursiveCheckMap(root))
        {
            generateMap();
        }
    }
    
    private void addNext(int current, int mapSize, BinaryNode parent)
    {
        if(current >= mapSize)
        {
            return;
        }
        
        //Start generating:
        //Left Child
        int randomNumberLeft = (int)(Math.random() * 6);
        if(randomNumberLeft == 1)
        {
            randomNumberLeft++;
        }
        parent.setLeft(new BinaryNode(tileNames[randomNumberLeft]));
        
        //Right Child
        int randomNumberRight = (int)(Math.random() * 6);
        if(randomNumberRight == 1)
        {
            randomNumberRight++;
        }
        parent.setRight(new BinaryNode(tileNames[randomNumberRight]));
        
        if(randomNumberLeft != 0)
        {
            addNext(current+1, mapSize, parent.getLeft());
        }
        
        if(randomNumberRight != 0)
        {
            addNext(current+1, mapSize, parent.getRight());
        }
    }
    private boolean recursiveCheckMap(BinaryNode node)
    {
        if(node == null)
        {
            return false;
        }
        if(((String)node.getObject()).equals("Exit"))
        {
            return true;
        }
        return recursiveCheckMap(node.getLeft()) || recursiveCheckMap(node.getRight());
    }
}
