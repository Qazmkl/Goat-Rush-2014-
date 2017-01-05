package com.goatrush.game;

public class Scores {
	
	// Constructor initializes head to be null
    public Scores(){
        head = null;
    }
    
    // Adds an element to the front
    public void addFront(int str){
        Node newNode = new Node(str);
        newNode.setNext(head);
        head = newNode;
    }
    
    // Removes the element by reworking the pointers to go around the element
    public void remove(int str){
        Node current = head;
        Node previous = head;
        
        if(current.getData() == str){
            head = current.getNext();
        }else{
            while(current.getNext() != null){
                previous = current;
                current = current.getNext();
                
                if(current.getData() == (str)){
                    previous.setNext(current.getNext());
                    break;
                }
            }
        }
    }
    
    // Returns the latest element
    public String latest(){
        String currentScore;
        currentScore = head.getData() + "";
        return currentScore;
    }
    
    // Checks if the boolean is empty
    public boolean isEmpty(){
    	if(head == null){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    // Goes through the whole list to find the highest element
    public int highest(){
    	Node current = head;
    	int high = 0;
    	
    	while(current != null){
    		if (current.getData() > high){
    			high = current.getData();
    		}
    		current = current.getNext();
    	}
    	
    	return high;
    	
    }
    
    // Node class
    private class Node{
    	// Constructor
        public Node(int newData){
            data = newData;
            next = null;
        }
        
        // Gets next element
        public Node getNext(){
            return(next);
        }
        
        // Sets next element
        public void setNext(Node newNode){
            next = newNode;
        }
        
        // Gets element's data
        public int getData(){
            return data;
        }
        
        // Variables declarations
        private int data;
        private Node next;
    }
    
    // Node object
    private Node head;
}
