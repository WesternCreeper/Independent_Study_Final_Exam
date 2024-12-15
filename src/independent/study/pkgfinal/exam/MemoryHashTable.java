/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package independent.study.pkgfinal.exam;

import dataStructures.HashTable;

/**
 *
 * @author Westley
 */
public class MemoryHashTable extends HashTable
{
    public MemoryHashTable(int size, int hashOption)
    {
        super(size, hashOption);
    }
    public MemoryHashTable(int size, int hashOption, int maximumStringLength)
    {
        super(size, hashOption, maximumStringLength);
    }
    public Object grabValue(int index)
    {
        if(index < 0 || index > allObjects.length)
        {
            return null;
        }
        else
        {
            return allObjects[index];
        }
    }
}
