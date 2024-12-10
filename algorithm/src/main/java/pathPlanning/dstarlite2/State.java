package pathPlanning.dstarlite2;
/*
 * @author daniel beard
 * http://danielbeard.io
 * http://github.com/daniel-beard
 *
 * Copyright (C) 2012 Daniel Beard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

import java.net.URLEncoder;

public class State implements Comparable, java.io.Serializable
{
	public int x=0;
	public int y=0;

	public double g=0;
	public double rhs=0;
	public double cost=0;
	/**
	 * 探测优先级-总代价
	 * 值=min(g, rhs) + h(s, sstart) + km
	 * min(g, rhs): 取g值和rhs值中的较小值
	 * h(s, sstart): 从当前节点到起点的启发式估计值
	 * km 路径代价修正值  
	 */
	public double totalCost;
	/**
	 * 探测优先级-局部最小代价
	 * 值=min(g, rhs)
	 */
	public double localCost;

	//Default constructor
	public State()
	{

	}

	//Overloaded constructor
	public State(int x, int y, double totalCost,double localCost)
	{
		this.x = x;
		this.y = y;
		this.totalCost = totalCost;
		this.localCost = localCost;
	}

	//Overloaded constructor
	public State(State other)
	{
		this.x = other.x;
		this.y = other.y;
		this.totalCost = other.totalCost;
		this.localCost = other.localCost;
	}

	//Equals
	public boolean eq(final State s2)
	{
		return ((this.x == s2.x) && (this.y == s2.y));
	}

	//Not Equals
	public boolean neq(final State s2)
	{
		return ((this.x != s2.x) || (this.y != s2.y));
	}

	//Greater than
	public boolean gt(final State s2)
	{
		if (totalCost-0.00001 > s2.totalCost) return true;
		else if (totalCost < s2.totalCost-0.00001) return false;
		return localCost > s2.localCost;
	}

	//Less than or equal to
	public boolean lte(final State s2)
	{
		if (totalCost < s2.totalCost) return true;
		else if (totalCost > s2.totalCost) return false;
		return localCost < s2.localCost + 0.00001;
	}

	//Less than
	public boolean lt(final State s2)
	{
		if (totalCost + 0.000001 < s2.totalCost) return true;
		else if (totalCost - 0.000001 > s2.totalCost) return false;
		return localCost < s2.localCost;
	}

	//CompareTo Method. This is necessary when this class is used in a priority queue
	public int compareTo(Object that)
	{
		//This is a modified version of the gt method
		State other = (State)that;
		if (totalCost-0.00001 > other.totalCost) return 1;
		else if (totalCost < other.totalCost-0.00001) return -1;
		if (localCost > other.localCost) return 1;
		else if (localCost < other.localCost) return -1;
		return 0;
	}

	//Override the CompareTo function for the HashMap usage
	@Override
	public int hashCode()
	{
		return this.x + 34245*this.y;
	}

	@Override public boolean equals(Object aThat) {
		//check for self-comparison
		if ( this == aThat ) return true;

		//use instanceof instead of getClass here for two reasons
		//1. if need be, it can match any supertype, and not just one class;
		//2. it renders an explict check for "that == null" redundant, since
		//it does the check for null already - "null instanceof [type]" always
		//returns false. (See Effective Java by Joshua Bloch.)
		if ( !(aThat instanceof State) ) return false;
		//Alternative to the above line :
		//if ( aThat == null || aThat.getClass() != this.getClass() ) return false;

		//cast to native object is now safe
		State that = (State)aThat;

		//now a proper field-by-field evaluation can be made
		if (this.x == that.x && this.y == that.y) return true;
		return false;

	}

	public String getId(){
		URLEncoder.encode("");
		return x+"_"+y;
	}
}
