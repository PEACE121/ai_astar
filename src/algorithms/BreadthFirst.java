package algorithms;

import astarframework.AAStar;
import astarframework.IAreaOfApplication;
import astarframework.Node;


public class BreadthFirst extends AAStar
{
	
	public BreadthFirst(IAreaOfApplication application)
	{
		super(application);
	}
	
	
	@Override
	public void pushNodeToList(Node node)
	{
		open.add(node);
	}
}
