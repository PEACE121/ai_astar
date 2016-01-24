package astarframework;

import java.util.ArrayList;
import java.util.List;


public class Node implements Comparable<Node>
{
	private IState					state;
	
	private int						g;
	private int						h;
	
	private Node					parent;
	private final List<Node>	kids	= new ArrayList<Node>();
	
	
	/**
	 * @param pos
	 */
	public Node(Node parent, IState state, int g, int h)
	{
		super();
		this.parent = parent;
		this.state = state;
		this.g = g;
		this.h = h;
	}
	
	
	/**
	 * @return the state
	 */
	public IState getState()
	{
		return state;
	}
	
	
	/**
	 * @param state the state to set
	 */
	public void setState(IState pos)
	{
		this.state = pos;
	}
	
	
	/**
	 * @return the parent
	 */
	public Node getParent()
	{
		return parent;
	}
	
	
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	
	/**
	 * @return the kids
	 */
	public List<Node> getKids()
	{
		return kids;
	}
	
	
	/**
	 * @return the kids
	 */
	public void addKid(Node kid)
	{
		kids.add(kid);
	}
	
	
	/**
	 * @return the g
	 */
	public int getG()
	{
		return g;
	}
	
	
	/**
	 * @param g the g to set
	 */
	public void setG(int g)
	{
		this.g = g;
	}
	
	
	/**
	 * @return the h
	 */
	public int getH()
	{
		return h;
	}
	
	
	/**
	 * @param h the h to set
	 */
	public void setH(int h)
	{
		this.h = h;
	}
	
	
	public int getF()
	{
		return getH() + getG();
	}
	
	
	@Override
	public int compareTo(Node o)
	{
		if (getF() < o.getF())
			return -1;
		else if (getF() > o.getF())
			return 1;
		else if (getH() < o.getH())
			return -1;
		else if (getH() > o.getH())
			return 1;
		return 0;// (int) Math.signum(Math.random() - 0.5);
	}
	
	
	public boolean compareWith(Node o)
	{
		if (o instanceof Node)
		{
			return state.isTheSame(o.getState());
		}
		System.out.println("Warning: Comparing Node with something else");
		return false;
		
	}
}
