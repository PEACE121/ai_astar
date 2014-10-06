package astarframework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public abstract class AAStar
{
	
	private final List<Node>				closed		= new LinkedList<Node>();
	protected final List<Node>				open			= new LinkedList<Node>();
	
	private final IAreaOfApplication		application;
	
	private final List<IAStarObersvers>	observers	= new ArrayList<IAStarObersvers>();
	
	
	public AAStar(IAreaOfApplication application)
	{
		this.application = application;
	}
	
	
	public void run()
	{
		// add the root node to the open list
		IState startPos = application.getStart();
		Node start = new Node(null, startPos, 0, application.getHeuristic(startPos));
		open.add(start);
		
		while (true)
		{
			// all possible solution tried, no one worked
			if (open.isEmpty())
			{
				System.out.println("FAILED, no solution found.");
				return;
			}
			// pop the first node (lowest f value)
			Node x = open.get(0);
			open.remove(0);
			closed.add(x);
			inform(x, false);
			
			// check for solution
			if (application.isSolution(x.getState()))
			{
				inform(x, true);
				System.out.println("SUCCESS");
				System.out.println("Path length: " + pathLength(x));
				System.out.println("Closed (generated) nodes: " + closed.size());
				System.out.println("Open nodes: " + open.size());
				System.out.println("Open+Closed nodes: " + (closed.size() + open.size()));
				return;
			}
			
			// for all possible successors
			List<IState> succ = application.generateAllSuccessors(x.getState());
			for (IState s : succ)
			{
				// create a new node with an increased g and a (parent) connection to x
				Node sNode = new Node(x, s, x.getG() + application.cost(x.getState(), s), application.getHeuristic(s));
				x.addKid(sNode);
				
				Node sameNode = checkForAlreadyExistance(sNode);
				if (sameNode == null)
				{
					// abstract: only difference of A Star (sorted), Depth First (top), Breadth First (bottom)
					pushNodeToList(sNode);
				} else
				{
					// use existing node but adjust g if the new node is better
					sNode = sameNode;
					if (x.getG() + application.cost(x.getState(), sNode.getState()) < sNode.getG())
					{
						sNode.setParent(x);
						sNode.setG(x.getG() + application.cost(sNode.getState(), x.getState()));
						// modify also the children
						if (closed.contains(sNode))
						{
							propagatePathImprovements(sNode);
						}
					}
				}
			}
		}
	}
	
	
	private int pathLength(Node node)
	{
		int length = 1;
		while (node.getParent() != null)
		{
			node = node.getParent();
			length++;
		}
		return length;
	}
	
	
	public abstract void pushNodeToList(Node node);
	
	
	private void propagatePathImprovements(Node p)
	{
		for (Node c : p.getKids())
		{
			if (p.getG() + application.cost(c.getState(), p.getState()) < c.getG())
			{
				c.setParent(p);
				c.setG(p.getG() + application.cost(c.getState(), p.getState()));
				propagatePathImprovements(c);
			}
		}
		
	}
	
	
	private Node checkForAlreadyExistance(Node s)
	{
		Node same = null;
		for (Node n : open)
		{
			if (n.compareWith(s))
			{
				same = n;
			}
		}
		for (Node n : closed)
		{
			if (n.compareWith(s))
			{
				same = n;
			}
		}
		return same;
	}
	
	
	public void register(IAStarObersvers obs)
	{
		observers.add(obs);
	}
	
	
	public void inform(Node x, boolean force)
	{
		for (IAStarObersvers obs : observers)
		{
			obs.update(x, force);
		}
	}
}
