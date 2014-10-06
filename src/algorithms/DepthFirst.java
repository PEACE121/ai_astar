package algorithms;

import astarframework.AAStar;
import astarframework.IAreaOfApplication;
import astarframework.Node;


public class DepthFirst extends AAStar
{
	
	public DepthFirst(IAreaOfApplication application)
	{
		super(application);
	}
	
	
	@Override
	public void pushNodeToList(Node node)
	{
		open.add(0, node);
	}
}
