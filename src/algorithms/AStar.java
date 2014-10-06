package algorithms;

import java.util.Collections;

import astarframework.AAStar;
import astarframework.IAreaOfApplication;
import astarframework.Node;


public class AStar extends AAStar
{
	
	public AStar(IAreaOfApplication application)
	{
		super(application);
	}
	
	
	@Override
	public void pushNodeToList(Node node)
	{
		open.add(node);
		Collections.sort(open);
	}
	
}
